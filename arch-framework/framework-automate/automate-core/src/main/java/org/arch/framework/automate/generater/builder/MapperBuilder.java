//package org.arch.framework.automate.generater.builder;
//
//import com.unichain.framework.code.api.Buildable;
//import com.unichain.framework.code.api.DataModel;
//import com.unichain.framework.code.database.model.Table;
//import com.unichain.framework.code.model.MapperModel;
//import com.unichain.framework.code.model.properties.PomModel;
//import com.unichain.framework.code.model.properties.PropertiesModel;
//import org.springframework.stereotype.Component;
//
//@Component("mapperBuilder")
//public class MapperBuilder implements Buildable {
//
//
//    @Override
//    public MapperModel buildData(PropertiesModel pkgPath, Table table) {
//        return null;
//    }
//
//
//    @Override
//    public void buildFile(PomModel pomModel, DataModel dataModel) {
//
//    }
//
//    @Override
//    public String buildFolder(PomModel pomModel) {
//        return null;
//    }
//
//
//    protected void build() {
//
//        // 设置模块上下文环境
//        // 构建模块结构
//        // 构建模块内部文件
//        buildJavaFile();
//        // 构建模块资源文件
//        buildResourcesFile();
//
//    }
//
//
//
//    /**
//     * 构建java文件
//     */
//    private void buildJavaFile() {
////        List<Table> tables = moduleContext.getTables();
////        // 构建java文件
////        tables.forEach(table -> {
////            String name = table.getName();
////
////            DaoModel daoModel = new DaoModel(table);
//////            daoModel.setSuffix("Mapper");
//////            daoModel.setEntity(name);
//////            daoModel.setPk("Long");
////
////            //TemplateBuilder.init(new DaoTemplate(name,"Dao","java","dao.ftl"),daoModel);
////        });
//
//
//
//    }
//
//
//    @Override
//    public String getTemplate() {
//        return "templates/java/mapper.ftl";
//    }
//
//
//    /**
//     * 构建resources配置文件
//     */
//    private void buildResourcesFile() {
//
//
//
//
//    }
//}
