package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseService {


//    /**
//     * 根据数据库名称获取数 及 表相关属性数据
//     *
//     * @param source   数据源
//     * @param database 数据库名称，只支持单个库获取，多个库在调用外城循环
//     * @return
//     */
//    public List<TableProperties> getDatabaseTablesInfo(DatabaseProperties source, String database) {
//        return DDLOperate.selectDDLOperate(source).getDatabaseProperties(source, database);
//    }
//
//
//    public List<MethodProperties> getDatabaseApisInfo(DatabaseProperties databaseProperties, String res) {
//        return null;
//    }
}
