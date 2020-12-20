package org.arch.framework.generater.core;

import org.arch.framework.generater.ex.CodegenException;
import org.arch.framework.generater.metadata.ModuleInfo;
import org.arch.framework.generater.render.RenderingRequest;

import java.io.IOException;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
public class DdlProcessor extends AbstractProcessor implements FtlProcessor {
    @Override
    public String getFile() {
        return "ddl.ftl";
    }

    @Override
    public void process(String code, RenderingRequest renderingRequest) {
        createFile(code,renderingRequest);
    }


    @Override
    public void createModule(RenderingRequest renderingRequest) {

    }


    @Override
    public void createFile(String code, RenderingRequest renderingRequest) {
        try {
            saveToFile(code, renderingRequest.getSavePath(), "ddl-schema.sql", renderingRequest.isCover());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodegenException(String.format("render %s code source failed.", renderingRequest.getEntity().getClassName()), e);
        }
    }
}
