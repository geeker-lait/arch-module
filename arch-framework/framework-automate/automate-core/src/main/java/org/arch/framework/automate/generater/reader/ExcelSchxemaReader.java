package org.arch.framework.automate.generater.reader;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.arch.framework.automate.common.utils.ExcelUtils;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.properties.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:05 AM
 */
@Slf4j
@Service
public class ExcelSchxemaReader extends AbstractSchemaReader implements SchemaReadable<ExcelProperties> {

    @Override
    public SchemaType getTyp() {
        return SchemaType.EXCEL;
    }

    @Override
    public <T extends SchemaData> List<T> read(SchemaProperties schemaProperties) {
        Stream<String> stream = Arrays.stream(schemaProperties.getResources().split(","));
        /**
         * excel 文档只能匹配一个,不能同时匹配，即要么api 要么 mvc
         */
        if(schemaProperties.getPatterns().equalsIgnoreCase(SchemaPattern.MVC.name())){
            List<SchemaData>  databaseProperties = new ArrayList<>();
            stream.forEach(res->{
                databaseProperties.addAll(readMvc(res,schemaProperties.getConfiguration()));
            });
            return (List<T>) databaseProperties;

        } else if(schemaProperties.getPatterns().equalsIgnoreCase(SchemaPattern.API.name())){

        }
        return null;
    }


    private List<DatabaseProperties> readApi(String excel,Map<String,String> heads){

        return null;
    }

    private List<DatabaseProperties> readMvc(String excel,Map<String,String> heads) {
        Map<String, String> swapHeads = heads.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, e -> e.getKey()));
        List<DatabaseProperties> databasePropertiesList= new ArrayList<>();
        Map<String, String> tableMap = new HashMap<>();
        // 从类路劲加载
        if(-1 != excel.indexOf("classpath:")){
            excel = new ClassPathResource(excel.split(":")[1]).getAbsolutePath();
        }
        Workbook workbook = null;
        try {
            workbook = ExcelUtils.initWorkBook(excel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0, length = workbook.getNumberOfSheets(); i < length; ++i) {
            // 存放所有table信息
            List<TableProperties> tables = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(i);
            String dbName = sheet.getSheetName();
            // 处理sheet
            Row firstRow = sheet.getRow(sheet.getFirstRowNum());
            List<ColumnsProperties> columns = new ArrayList<>();
            for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                //存储每一行的信息
                Map<String, Object> map = new HashMap();
                String key;
                for (int k = firstRow.getFirstCellNum(); k < firstRow.getLastCellNum(); k++) {
                    if (row == null) continue;
                    TableProperties tableProperties;
                    if (ExcelUtils.isMergedRegion(sheet, j, k)) {
                        Object otable = ExcelUtils.getMergedRegionValue(sheet, j, k);
                        if (otable != null) {
                            String table = otable.toString();
                            // 防止重名
                            key = dbName + "." + table;
                            String val = tableMap.get(key);
                            if (!StringUtils.isNotBlank(val)) {
                                tableMap.put(key, table);
                                log.info("current table name is :{}",key);
                                String tc[] = table.split("/");
                                tableProperties = new TableProperties();
                                tableProperties.setName(tc[1]);
                                tableProperties.setComment(tc[0]);
                                columns = new ArrayList<>();
                                tableProperties.setColumns(columns);
                                tables.add(tableProperties);
                            }
                        }
                    } else {
                        // 获取首行标题，并翻译成字段；
                        Object cv = ExcelUtils.getCellValue(row.getCell(k));
                        String name = swapHeads.get(firstRow.getCell(k).getStringCellValue());
                        if (name != null && null != cv) {
                            map.put(name, cv);
                        }
                    }
                }
                // 对每一行信息转换为对象
                if (map.size() > 0) {
                    MvcSchema mvcSchema = BeanUtil.toBean(map, MvcSchema.class);
                    /*Class c = JdbcTypeUtils.getFieldType(tableSchema.getType());
                    if(c == null){
                        log.info("jdbc type convert to java type is error {}",tableSchema);
                        continue;
                    }*/
                    ColumnsProperties columnsProperties = new ColumnsProperties();
                    columnsProperties.setName(mvcSchema.getColumn());
                    columnsProperties.setComment(mvcSchema.getComment());
                    columnsProperties.setLength(mvcSchema.getLength());
                    columnsProperties.setTyp(mvcSchema.getType());
                    columns.add(columnsProperties);
                }
            }

            DatabaseProperties databaseProperties = new DatabaseProperties();
            databaseProperties.setName(dbName);
            databaseProperties.setTables(tables);
            databasePropertiesList.add(databaseProperties);
        }
        return databasePropertiesList;
    }
}
