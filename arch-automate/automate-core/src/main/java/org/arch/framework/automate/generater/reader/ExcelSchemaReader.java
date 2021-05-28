package org.arch.framework.automate.generater.reader;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.arch.automate.common.configuration.ExcelFiledConfiguration;
import org.arch.automate.common.database.Column;
import org.arch.automate.common.database.Database;
import org.arch.automate.common.database.Table;
import org.arch.automate.common.enums.SchemaPattern;
import org.arch.automate.common.enums.SchemaType;
import org.arch.automate.common.utils.ExcelUtils;
import org.arch.automate.common.utils.JdbcTypeUtils;
import org.arch.framework.automate.generater.core.DatabaseSchemaData;
import org.arch.framework.automate.generater.core.ReaderConfiguration;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class ExcelSchemaReader extends AbstractSchemaReader<ExcelFiledConfiguration> implements SchemaReadable {


    @Override
    public List<SchemaData> read(SchemaProperties schemaProperties) {
        // 如果有有特殊处理，再次处理，如果没有则调用父类通用处理
        return super.doRead(schemaProperties);
    }

    @Override
    public SchemaType getTyp() {
        return SchemaType.EXCEL;
    }

    @Override
    protected List<? extends SchemaData> readMvc(ReaderConfiguration<ExcelFiledConfiguration> readerConfiguration) {
        return getMvc(readerConfiguration);
    }

    @Override
    protected List<? extends SchemaData> readApi(ReaderConfiguration<ExcelFiledConfiguration> readerConfiguration) {
        return new ArrayList<>();
    }

    @Override
    protected ReaderConfiguration<ExcelFiledConfiguration> buildConvertConfiguration(String resName, SchemaProperties schemaProperties, SchemaPattern schemaPattern) {
        ReaderConfiguration<ExcelFiledConfiguration> readerConfiguration = new ReaderConfiguration<>();
        readerConfiguration.setConfiguration((ExcelFiledConfiguration) schemaProperties.getSchemaConfiguration());
        readerConfiguration.setResource(resName);
        readerConfiguration.setPattern(schemaPattern);
        return readerConfiguration;
    }


    protected List<? extends SchemaData> getMvc(ReaderConfiguration<ExcelFiledConfiguration> readerConfiguration) {
        String res = readerConfiguration.getResource();
        // 从类路劲加载
        if (-1 != res.indexOf("classpath:")) {
            res = new ClassPathResource(res.split(":")[1]).getAbsolutePath();
        }
        Workbook workbook = null;
        try {
            workbook = ExcelUtils.initWorkBook(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> swapHeads = readerConfiguration.getConfiguration().getHeader().entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, e -> e.getKey()));
        List<DatabaseSchemaData> databaseSchemaDatas = new ArrayList<>();
        for (int i = 0, length = workbook.getNumberOfSheets(); i < length; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            String dbName = sheet.getSheetName();
            // 处理sheet
            Row firstRow = sheet.getRow(sheet.getFirstRowNum());
            Database database = new Database();
            database.setName(dbName);
            Table table = null;
            Map<String, Table> tabMap = new HashMap<>();
            for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                //存储每一行的信息
                Map<String, Object> fieldMap = new HashMap();
                for (int k = firstRow.getFirstCellNum(); k < firstRow.getLastCellNum(); k++) {
                    if (row == null) {
                        continue;
                    }
                    if (ExcelUtils.isMergedRegion(sheet, j, k)) {
                        Object otable = ExcelUtils.getMergedRegionValue(sheet, j, k);
                        if (otable != null) {
                            String stable = otable.toString();
                            if (StringUtils.isNotBlank(stable)) {
                                log.info("current table name is :{}", stable);
                                String tc[] = stable.split("/");
                                table = tabMap.get(tc[1]);
                                if (table != null) {
                                    continue;
                                }
                                table = new Table();
                                table.setName(tc[1]);
                                table.setComment(tc[0]);
                                database.getTables().add(table);
                                tabMap.put(tc[1], table);
                            }
                        }
                    } else {
                        // 获取首行标题，并翻译成字段；
                        Object cv = ExcelUtils.getCellValue(row.getCell(k));
                        String name = swapHeads.get(firstRow.getCell(k).getStringCellValue());
                        if (name != null && null != cv) {
                            fieldMap.put(name, cv);
                        }
                    }
                }
                // 对每一行信息转换为对象
                if (fieldMap.size() > 0) {
                    ExcelFiledConfiguration excelProperties = BeanUtil.toBean(fieldMap, ExcelFiledConfiguration.class);
                    Class c = JdbcTypeUtils.getFieldType(excelProperties.getType());
                    if (c == null) {
                        log.info("jdbc type convert to java type is error {}", excelProperties);
                        continue;
                    }
                    Column column = new Column();
                    column.setName(excelProperties.getColumn());
                    column.setComment(excelProperties.getComment());
                    column.setLength(excelProperties.getLength());
                    column.setTyp(c.getSimpleName());
                    if (table != null) {
                        table.getColumns().add(column);
                    }
                }
            }
            DatabaseSchemaData databaseSchemaData = new DatabaseSchemaData();
            databaseSchemaData.setDatabase(database);
            databaseSchemaData.setSchemaPattern(SchemaPattern.MVC);
            databaseSchemaData.setIdentifier("" + readerConfiguration.hashCode());
            databaseSchemaDatas.add(databaseSchemaData);
        }
        return databaseSchemaDatas;
    }


}
