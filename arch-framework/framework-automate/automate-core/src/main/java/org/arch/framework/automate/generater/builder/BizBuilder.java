//package org.arch.framework.automate.generater.builder;
//
//import com.unichain.framework.code.api.Buildable;
//import com.unichain.framework.code.database.model.Table;
//import com.unichain.framework.code.model.BizModel;
//import com.unichain.framework.code.model.ClassModel;
//import com.unichain.framework.code.model.properties.PomModel;
//import com.unichain.framework.code.model.properties.ProjectModel;
//import com.unichain.framework.code.model.properties.PropertiesModel;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component("bizBuilder")
//public class BizBuilder implements Buildable<BizModel> {
//    @Autowired
//    private ProjectModel projectModel;
//
//    @Override
//    public BizModel buildData(PropertiesModel propertiesModel, Table table) {
//        BizModel bizModel = new BizModel();
//        bizModel.setClassName(UPPER_CAMEL_CONVERT.convert(table.getName()));
//        wrapperDefaultValue(propertiesModel,bizModel);
//        return bizModel;
//    }
//
//    @Override
//    public void buildFile(PomModel pomModel, BizModel bizModel) {
//
//        BizModel implBizModel = new BizModel();
//        BeanUtils.copyProperties(bizModel,implBizModel);
//
//        List<ClassModel> implememtsClassList = new ArrayList();
//        ClassModel implementsClass = new ClassModel();
//        implementsClass.setName("CrudBiz");
//        implementsClass.setPkg("com.unichain.framework.crud.api");
//        List<ClassModel> genericTypes = new ArrayList<>();
//        ClassModel entityGenClass = new ClassModel();
//        entityGenClass.setName(bizModel.getClassName().concat(projectModel.getConfig().getEntitySuffix()));
//        entityGenClass.setPkg(bizModel.getBasePkg().concat("."+ projectModel.getConfig().getEntitySuffix().toLowerCase()));
//        genericTypes.add(entityGenClass);
//        implementsClass.setGenericTypes(genericTypes);
//        implememtsClassList.add(implementsClass);
//        bizModel.setImplementsClazz(implememtsClassList);
//
//        genClassFile(pomModel, bizModel);
//
//
//
//
//
//        // 设置接口
//        List<ClassModel> implementsClazzList = implBizModel.getImplementsClazz();
//        if(implementsClazzList == null ){
//            implementsClazzList = new ArrayList<>();
//        }
//        ClassModel implementsClazz = new ClassModel();
//        implementsClazz.setName(implBizModel.getClassName().concat(projectModel.getConfig().getBizSuffix()));
//        implementsClazz.setPkg(pomModel.getProperties().getBasePkg() + "." + projectModel.getConfig().getBizSuffix().toLowerCase());
//        implementsClazzList.add(implementsClazz);
//        implBizModel.setImplementsClazz(implementsClazzList);
//        implBizModel.setEntitySuffix(projectModel.getConfig().getEntitySuffix());
//        genClassImplFile(pomModel,implBizModel,BIZ_IMPL_TEMPLATE);
//    }
//
//    @Override
//    public String getTemplate() {
//        return "templates/java/biz.ftl";
//    }
//}
