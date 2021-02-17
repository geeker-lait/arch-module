package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;

import java.io.File;
import java.io.IOException;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
public class DaoProcessor extends AbstractProcessor implements TemplateProcessor {
    @Override
    public TemplateName getTemplate() {
        return TemplateName.DAO;
    }

    @Override
    public void process(String code, RenderingRequest renderingRequest) {
        createModule(renderingRequest);
        createFile(code,renderingRequest);
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
