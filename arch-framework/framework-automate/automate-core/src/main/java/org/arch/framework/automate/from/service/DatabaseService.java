package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.ddl.DDLOperate;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
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
     * @param source 数据源
     * @param database 只支持单个数据，多个外层 for循环调用
     * @return
     */
    public List<TableProperties> getDatabaseTablesInfo(DatabaseProperties source, String database) {
        return DDLOperate.selectDDLOperate(source).getDatabaseProperties(source, database);
    }



}
