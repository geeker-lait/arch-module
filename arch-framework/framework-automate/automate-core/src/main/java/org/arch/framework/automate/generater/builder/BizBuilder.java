//package org.arch.framework.automate.generater.builder;
//
//import cn.hutool.extra.template.TemplateEngine;
//import lombok.extern.slf4j.Slf4j;
//import org.arch.framework.automate.generater.core.Buildable;
//import org.arch.framework.automate.generater.core.SchemaPatternable;
//import org.arch.framework.automate.generater.core.TemplateName;
//import org.arch.framework.automate.generater.properties.DocumentProperties;
//import org.arch.framework.automate.generater.properties.PomProperties;
//import org.arch.framework.automate.generater.properties.ProjectProperties;
//import org.springframework.stereotype.Service;
//
//import java.nio.file.Path;
//
//@Slf4j
//@Service
//public class BizBuilder extends AbstractBuilder implements Buildable {
//
//
//    @Override
//    public TemplateName getTemplateName() {
//        return TemplateName.BIZ;
//    }
//
//    @Override
//    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, SchemaPatternable schemaData) {
//        doBuild(path, engine, projectProperties, documentProperties, schemaData);
//    }
//
//    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaPatternable schemaData) {
//        log.info("BizBuilder start");
//        buildPackageFile(projectProperties.getCover(), path, templateEngine, projectProperties, documentProperties, schemaData);
//    }
//
//}
