package org.arch.framework.automate.generater.builder;

import com.unichain.framework.code.api.Buildable;
import com.unichain.framework.code.database.model.Table;
import com.unichain.framework.code.model.ControllerModel;
import com.unichain.framework.code.model.properties.PomModel;
import com.unichain.framework.code.model.properties.PropertiesModel;
import org.springframework.stereotype.Component;

@Component("controllerBuilder")
public class ControllerBuilder implements Buildable<ControllerModel> {


    @Override
    public ControllerModel buildData(PropertiesModel propertiesModel, Table table) {
        ControllerModel controllerModel = new ControllerModel();
        controllerModel.setClassName(UPPER_CAMEL_CONVERT.convert(table.getName()));
        controllerModel.setPkg(propertiesModel.getPkg());
        controllerModel.setRequestMapping(Buildable.LOWER_UNDERSCORE_CONVERT.convert(table.getName()));
        wrapperDefaultValue(propertiesModel,controllerModel);
        return controllerModel;
    }

    @Override
    public void buildFile(PomModel pomModel, ControllerModel dataModel) {
//        genClassFile(pomModel,dataModel);
//        ConfigProperties propertiesModel = pomModel.getProperties();
//        String fileName = dataModel.getPrefix()+ dataModel.getClassName() + dataModel.getSuffix();
//        String serviceFile = pomModel.getPath()+ File.separator + fileName  + "." + propertiesModel.getExtName();
//        int i = propertiesModel.getTemplate().lastIndexOf("/");
//        // 模板文件
//        String ftlFile = propertiesModel.getTemplate().substring(i + 1);
//        String ftlPaht = propertiesModel.getTemplate().substring(0,i);
//        try {
//            Freemarker.printFile(ftlPaht, ftlFile, serviceFile, dataModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public String getTemplate() {
        return "templates/java/controller.ftl";
    }
}
