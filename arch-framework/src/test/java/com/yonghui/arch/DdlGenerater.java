package com.yonghui.arch;

import com.google.gson.Gson;
import org.arch.framework.generater.core.DdlFtlProcessor;
import org.arch.framework.generater.core.ModuleInfos;
import org.arch.framework.generater.core.TableSchema;
import org.arch.framework.generater.render.RenderingRequest;
import org.arch.framework.generater.utils.FreeMarkerUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 7:26 PM
 */
public class DdlGenerater {

    public static void main(String[] args) throws Exception {
        String file = "uni-life.xlsx";
        String path = DdlGenerater.class.getResource("/").getPath();
        String ftlPath = path.substring(1).replace("target/test-classes/", "src/main/resources/");
        String savePath = path.substring(1).replace("target/test-classes/", "src/test/resources/");
        FileInputStream fileInputStream = new FileInputStream(savePath + file);
        Gson gson=new Gson();
        ModuleInfos<TableSchema> excelUtils = new ModuleInfos(file,fileInputStream, TableSchema.class);
        System.out.println(gson.toJson(excelUtils.getModuleInfos()));


        RenderingRequest renderingRequest = new RenderingRequest();
        renderingRequest.setFtlPath(ftlPath+ File.separator+ "templates");
        renderingRequest.setFtlName("ddl.ftl");
        renderingRequest.setSavePath(savePath);
        renderingRequest.setModuleInfos(excelUtils.getModuleInfos());
        renderingRequest.setCover(true);

        FreeMarkerUtils.addFtlProcessor(new DdlFtlProcessor());
        FreeMarkerUtils.process(renderingRequest);
    }
}
