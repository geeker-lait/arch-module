//package org.arch.framework.automate.generater.reader.database;
//
//import lombok.AllArgsConstructor;
//import org.arch.form.crud.service.DatabaseService;
//import org.arch.framework.automate.generater.converter.AbstractSchemaService;
//import org.arch.framework.automate.generater.core.ReaderConfiguration;
//import org.arch.framework.automate.generater.converter.SchemaConvertable;
//import org.arch.framework.automate.generater.core.ApiSchemaData;
//import org.arch.framework.automate.generater.core.DatabaseSchemaData;
//import org.arch.framework.automate.generater.properties.MethodProperties;
//import org.arch.framework.automate.generater.properties.TableProperties;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.function.Function;
//
///**
// * @author lait.zhang@gmail.com
// * @description: TODO
// * @weixin PN15855012581
// * @date 3/24/2021 10:06 AM
// */
//@Component
//@AllArgsConstructor
//public class DatabaseSchemaConverter extends AbstractSchemaService implements SchemaConvertable {
//
//    private final DatabaseService databaseService;
//
//    @Override
//    public Function<ReaderConfiguration, List<DatabaseSchemaData>> convertTable() {
//        return params -> {
//            // 获取数据库的table
////            List<TableProperties> tableProperties = databaseService.getDatabaseTablesInfo(params.getDatabaseProperties(), params.getDatabaseName());
////            return CollectionUtils.isEmpty(tableProperties) ? Lists.newArrayList() : tableProperties;
//            return null;
//        };
//
//    }
//
//
//    @Override
//    public Function<ReaderConfiguration, List<ApiSchemaData>> convertMethod() {
//        return null;
//    }
//}
