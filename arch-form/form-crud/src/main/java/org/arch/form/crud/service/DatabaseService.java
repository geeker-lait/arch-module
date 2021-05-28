package org.arch.form.crud.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.automate.common.api.Api;
import org.arch.automate.common.configuration.DatabaseConfiguration;
import org.arch.automate.common.database.Table;
import org.arch.form.crud.ddl.DDLOperate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseService {


    /**
     * 根据数据库名称获取数 及 表相关属性数据
     *
     * @param source   数据源
     * @param database 数据库名称，只支持单个库获取，多个库在调用外城循环
     * @return
     */
    public List<Table> getDatabaseTablesInfo(DatabaseConfiguration source, String database) {
        return DDLOperate.selectDDLOperate(source).getDatabaseProperties(source, database);
    }


    public List<Api> getDatabaseApisInfo(DatabaseConfiguration databaseProperties, String res) {
        return null;
    }
}
