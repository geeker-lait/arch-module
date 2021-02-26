package org.arch.framework.automate.generater.reader;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.arch.framework.automate.common.utils.ExcelUtils;
import org.arch.framework.automate.common.utils.JdbcTypeUtils;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.properties.ColumnsProperties;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.ExcelProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:05 AM
 */
@Slf4j
@Service
public class ExcelSchemaReader extends AbstractSchemaReader implements SchemaReadable<ExcelProperties> {

    @Override
    public SourceName getSource() {
        return SourceName.EXCEl_SOURCE;
    }

    @Override
    public List<DatabaseProperties> read(ExcelProperties source) throws Exception {
        return doRead(source.getFile(),source.getHeads());
    }

    public List<DatabaseProperties> doRead(String excel,Map<String,String> heads) throws Exception {
        Map<String, String> swapHeads = heads.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, e -> e.getKey()));
        List<DatabaseProperties> databasePropertiesList= new ArrayList<>();
        Map<String, String> tableMap = new HashMap<>();
        // 从类路劲加载
        if(-1 != excel.indexOf("classpath:")){
            excel = new ClassPathResource(excel.split(":")[1]).getAbsolutePath();
        }
        Workbook workbook = ExcelUtils.initWorkBook(excel);
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
                                log.info("current table name is {}" + key);
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
                    TableSchema tableSchema = BeanUtil.toBean(map, TableSchema.class);
                    /*Class c = JdbcTypeUtils.getFieldType(tableSchema.getType());
                    if(c == null){
                        log.info("jdbc type convert to java type is error {}",tableSchema);
                        continue;
                    }*/
                    ColumnsProperties columnsProperties = new ColumnsProperties();
                    columnsProperties.setName(tableSchema.getColumn());
                    columnsProperties.setComment(tableSchema.getComment());
                    columnsProperties.setLength(tableSchema.getLength());
                    columnsProperties.setTyp(tableSchema.getType());
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
