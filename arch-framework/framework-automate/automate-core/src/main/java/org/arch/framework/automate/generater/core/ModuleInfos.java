package org.arch.framework.automate.generater.core;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.arch.framework.automate.common.metadata.DatabaseInfo;
import org.arch.framework.automate.common.metadata.EntityInfo;
import org.arch.framework.automate.common.metadata.FieldInfo;
import org.arch.framework.automate.common.utils.ExcelUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:56 AM
 */
@Slf4j
public class ModuleInfos<T extends NameToField> {

    @Getter
    private List<DatabaseInfo> databaseInfos;

    public ModuleInfos(String filePath, InputStream inputStream, Class<T> t) throws Exception {
        Workbook workbook = ExcelUtils.initWorkBook(filePath, inputStream);
        init(workbook, t);
    }

    public void init(Workbook workbook, Class<T> t) {
        databaseInfos = new ArrayList<>();
        Map<String, String> tables = new HashMap<>();
        for (int i = 0, length = workbook.getNumberOfSheets(); i < length; ++i) {
            // 初始化ModuleInfo
            Sheet sheet = workbook.getSheetAt(i);
            DatabaseInfo databaseInfo = new DatabaseInfo();
            List<EntityInfo> entityInfos = new ArrayList<>();
            databaseInfo.setModuleName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sheet.getSheetName()));
            databaseInfo.setEntityInfos(entityInfos);
            databaseInfos.add(databaseInfo);
            // 处理sheet
            Row firstRow = sheet.getRow(sheet.getFirstRowNum());
            String moduleName = sheet.getSheetName();
            // 获取首行标题，并翻译成字段；
            String name = null;
            List<FieldInfo> fields = null;
            for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                //存储每一行的信息
                Map<String, Object> map = new HashMap();
                String key = null;
                for (int k = firstRow.getFirstCellNum(); k < firstRow.getLastCellNum(); k++) {
                    if (row == null) continue;

                    if (ExcelUtils.isMergedRegion(sheet, j, k)) {
                        Object otable = ExcelUtils.getMergedRegionValue(sheet, j, k);
                        if (otable != null) {
                            String table = otable.toString();
                            key = moduleName + "." + table;
                            String val = tables.get(key);
                            if (!StringUtils.isNotBlank(val)) {
                                tables.put(key, table);
                                log.info("========" + table);
                                String tc[] = table.split("/");
                                EntityInfo entityInfo = new EntityInfo();
                                entityInfo.setComment(tc[0]);
                                entityInfo.setTableName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tc[1]));
                                entityInfo.setModuleName(moduleName);
                                fields = new ArrayList<>();
                                entityInfo.setFields(fields);
                                entityInfos.add(entityInfo);
                            }
                        }
                    } else {
                        Cell cell = row.getCell(k);
                        name = TranslationField.getfieldMap().get(firstRow.getCell(k).getStringCellValue());
                        if (name != null && null != ExcelUtils.getCellValue(cell)) {
                            map.put(name, ExcelUtils.getCellValue(cell));
                        }
                    }
                }
                if(map.size()>0) {
                    //用来存储行列信息
                    T ta = BeanUtil.toBean(map, t);
                    ta.setRow(j + 1);
                    addFields(ta, fields);
                }
            }
        }

    }


    public void addFields(T t, List<FieldInfo> fields) {
        TableSchema tableSchema;
        if (t != null && fields != null) {
            tableSchema = (TableSchema) t;
            FieldInfo fieldInfo = new FieldInfo();
            if (tableSchema.getColumn() != null) {
                String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tableSchema.getColumn());
                fieldInfo.setName(column);
                fieldInfo.setColumnName(column);
            }
            fieldInfo.setComment(tableSchema.getComment());
            fieldInfo.setLength(tableSchema.getLength());
            fieldInfo.setType(tableSchema.getType());
            fields.add(fieldInfo);
        }
    }
}
