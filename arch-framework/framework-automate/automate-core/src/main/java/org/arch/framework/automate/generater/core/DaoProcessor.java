package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.config.DataProperties;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.config.ProjectProperties;
import org.arch.framework.automate.generater.config.TableProperties;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;
import org.arch.framework.beans.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
@Service
public class DaoProcessor extends AbstractProcessor implements TemplateProcessor {
    @Override
    public void build(GeneratorConfig generatorConfig) {
        /**
         *     private String path;
         *     private String name;
         *     private String basePkg;
         *     private PomProperties pom;
         */
        ProjectProperties projectProperties = generatorConfig.getProject();
        File projectRoot = projectProperties.getFile();
        // 创建模块文件夹
        if (!FileUtil.exist(projectRoot)) {
            projectRoot.mkdirs();
        }
        generatorConfig.getPackages();
        // 创建dao
        generatorConfig.getDatabase().getTables().forEach(table->{

            String name = table.getName();

        });



    }

    @Override
    public TemplateName getTemplate() {
        return TemplateName.DAO;
    }

    @Override
    public DataProperties buildData() {
        return null;
    }

    @Override
    public void process(String code, RenderingRequest renderingRequest) {
        createModule(renderingRequest);
        createFile(code, renderingRequest);
    }

    @Override
    public void createModule(RenderingRequest renderingRequest) {
        creatMavenDirectory(renderingRequest);
    }


    @Override
    public void createFile(String code, RenderingRequest renderingRequest) {
        String pack = renderingRequest.getPackageName().replace(".", File.separator);
        renderingRequest.getDatabaseInfos().forEach(databaseInfo -> {
            String mn = databaseInfo.getModuleName();
            databaseInfo.getEntityInfos().forEach(entityInfo -> {
                try {
                    saveToFile(code, renderingRequest.getSavePath() + File.separator + mn + File.separator + MAIN_JAVA + pack + File.separator + mn + File.separator + "dao", entityInfo.getTableName() + ".java", renderingRequest.isCover());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CodegenException(String.format("render %s code source failed.", renderingRequest.getEntity().getClassName()), e);
                }
            });
        });
    }

}
