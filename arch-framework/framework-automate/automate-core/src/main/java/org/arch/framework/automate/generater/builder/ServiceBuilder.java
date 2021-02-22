//package org.arch.framework.automate.generater.builder;
//
//import com.unichain.framework.code.api.Buildable;
//import com.unichain.framework.code.database.model.Table;
//import com.unichain.framework.code.model.ClassModel;
//import com.unichain.framework.code.model.ServiceModel;
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
//@Component("serviceBuilder")
//public class ServiceBuilder implements Buildable<ServiceModel> {
//    @Autowired
//    private ProjectModel projectModel;
//    @Override
//    public ServiceModel buildData(PropertiesModel propertiesModel, Table table) {
//        ServiceModel serviceModel = new ServiceModel();
//        serviceModel.setClassName(UPPER_CAMEL_CONVERT.convert(table.getName()));
//        wrapperDefaultValue(propertiesModel,serviceModel);
//        return serviceModel;
//    }
//
//    @Override
//    public void buildFile(PomModel pomModel, ServiceModel serviceModel) {
//
//        ServiceModel implServiceModel = new ServiceModel();
//        BeanUtils.copyProperties(serviceModel,implServiceModel);
//
//        List<ClassModel> implememtsClassList = new ArrayList();
//        ClassModel implementsClass = new ClassModel();
//        implementsClass.setName("CrudService");
//        implementsClass.setPkg("com.unichain.framework.crud.api");
//        List<ClassModel> genericTypes = new ArrayList<>();
//        ClassModel entityGenClass = new ClassModel();
//        entityGenClass.setName(serviceModel.getClassName().concat(projectModel.getConfig().getEntitySuffix()));
//        entityGenClass.setPkg(serviceModel.getBasePkg().concat("."+ projectModel.getConfig().getEntitySuffix().toLowerCase()));
//        genericTypes.add(entityGenClass);
//        implementsClass.setGenericTypes(genericTypes);
//        implememtsClassList.add(implementsClass);
//        serviceModel.setImplementsClazz(implememtsClassList);
//        // 创建service
//        genClassFile(pomModel,serviceModel);
//
//
//
//
//
//
//
//
//
//
//
//
//        // 设置实现类
//        ClassModel extendsClass = new ClassModel();
//        extendsClass.setName("CrudServiceImpl");
//        extendsClass.setPkg("com.unichain.framework.crud.service");
//
//        genericTypes = new ArrayList<>();
//        ClassModel genericTypeDao = new ClassModel();
//        genericTypeDao.setName(serviceModel.getClassName().concat(projectModel.getConfig().getDaoSuffix()));
//        genericTypeDao.setPkg(pomModel.getProperties().getBasePkg()+"." + projectModel.getConfig().getDaoSuffix().toLowerCase());
//        genericTypes.add(genericTypeDao);
//        ClassModel genericTypeEntity = new ClassModel();
//        genericTypeEntity.setName(serviceModel.getClassName().concat(projectModel.getConfig().getEntitySuffix()));
//        genericTypeEntity.setPkg(pomModel.getProperties().getBasePkg()+"." + projectModel.getConfig().getEntitySuffix().toLowerCase());
//        genericTypes.add(genericTypeEntity);
//        extendsClass.setGenericTypes(genericTypes);
//        implServiceModel.setExtendsClazz(extendsClass);
//
//
//        // 设置接口
//        List<ClassModel> implementsClazzList = implServiceModel.getImplementsClazz();
//        if(implementsClazzList == null ){
//            implementsClazzList = new ArrayList<>();
//        }
//        ClassModel implementsClazz = new ClassModel();
//        implementsClazz.setName(serviceModel.getClassName().concat(projectModel.getConfig().getServiceSuffix()));
//        implementsClazz.setPkg(pomModel.getProperties().getBasePkg()+"." + projectModel.getConfig().getServiceSuffix().toLowerCase());
//        implementsClazzList.add(implementsClazz);
//        implServiceModel.setImplementsClazz(implementsClazzList);
//
//        // 生成实现类
//        genClassImplFile(pomModel,implServiceModel,SERVICE_IMPL_TEMPLATE);
//    }
//
//    @Override
//    public String getTemplate() {
//        return "templates/java/service.ftl";
//    }
//
//}
