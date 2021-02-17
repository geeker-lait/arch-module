package org.arch.framework.automate.generater.builder;

import com.unichain.framework.code.api.Buildable;
import com.unichain.framework.code.database.model.Table;
import com.unichain.framework.code.model.ClassModel;
import com.unichain.framework.code.model.DaoModel;
import com.unichain.framework.code.model.properties.PomModel;
import com.unichain.framework.code.model.properties.ProjectModel;
import com.unichain.framework.code.model.properties.PropertiesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("daoBuilder")
public class DaoBuilder implements Buildable<DaoModel> {

    @Autowired
    private ProjectModel projectModel;

    @Override
    public DaoModel buildData(PropertiesModel propertiesModel, Table table) {

        DaoModel daoModel = new DaoModel();
        daoModel.setClassName(UPPER_CAMEL_CONVERT.convert(table.getName()));
        wrapperDefaultValue(propertiesModel,daoModel);

        // 设置泛型
        ClassModel extendsClass =  daoModel.getExtendsClazz();
        if(extendsClass == null){
            extendsClass = new ClassModel();
        }
        extendsClass.setName("CrudMapper");
        extendsClass.setPkg("com.unichain.framework.crud.api");
        List<ClassModel> genericTypes = new ArrayList<>();
        ClassModel genericType = new ClassModel();
        genericType.setName(daoModel.getClassName().concat(projectModel.getConfig().getEntitySuffix()));
        genericType.setPkg(daoModel.getBasePkg().concat("." + projectModel.getConfig().getEntitySuffix().toLowerCase()));
        genericTypes.add(genericType);
        extendsClass.setGenericTypes(genericTypes);
        daoModel.setExtendsClazz(extendsClass);
        return daoModel;
    }

    @Override
    public void buildFile(PomModel pomModel, DaoModel dataModel) {


        genClassFile(pomModel,dataModel);


    }

    @Override
    public String getTemplate() {
        return "templates/java/dao.ftl";
    }

}
