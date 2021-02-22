//package org.arch.framework.automate.generater.builder;
//
//import com.unichain.framework.code.api.Buildable;
//import com.unichain.framework.code.database.model.Table;
//import com.unichain.framework.code.model.ApplicationModel;
//import com.unichain.framework.code.model.properties.PomModel;
//import com.unichain.framework.code.model.properties.PropertiesModel;
//import org.springframework.stereotype.Component;
//
//
//@Component("applicationBuilder")
//public class ApplicationBuilder implements Buildable<ApplicationModel> {
//
//
//    @Override
//    public ApplicationModel buildData(PropertiesModel propertiesModel, Table table) {
//        ApplicationModel applicationModel = new ApplicationModel();
//        if(table!=null) {
//            applicationModel.setClassName(UPPER_CAMEL_CONVERT.convert(table.getName()));
//            applicationModel.setRequestMapping(Buildable.LOWER_UNDERSCORE_CONVERT.convert(table.getName()));
//        } else {
//            applicationModel.setClassName(propertiesModel.getClassName());
//        }
//        applicationModel.setPkg(propertiesModel.getPkg());
//        wrapperDefaultValue(propertiesModel,applicationModel);
//        return applicationModel;
//
//    }
//
//    @Override
//    public void buildFile(PomModel pomModel, ApplicationModel applicationModel) {
//        genClassFile(pomModel,applicationModel);
//    }
//
//    @Override
//    public String getTemplate() {
//        return "templates/java/application.ftl";
//    }
//
//}
