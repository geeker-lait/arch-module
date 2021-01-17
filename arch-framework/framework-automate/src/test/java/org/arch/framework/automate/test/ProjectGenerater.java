package org.arch.framework.automate.test;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONArray;
import org.arch.framework.automate.generater.core.DaoProcessor;
import org.arch.framework.automate.generater.core.DdlProcessor;
import org.arch.framework.automate.generater.core.ModuleInfos;
import org.arch.framework.automate.generater.core.TableSchema;
import org.arch.framework.automate.generater.metadata.ModuleInfo;
import org.arch.framework.automate.generater.render.RenderingRequest;
import org.arch.framework.automate.generater.utils.FreeMarkerUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 7:26 PM
 */
public class ProjectGenerater {

    public static void main(String[] args) throws Exception {
        String os = System.getProperty("os.name");
        String file = "uni-life-pms.xlsx";
        String path = ProjectGenerater.class.getResource("/").getPath();

        String ftlPath;
        String savePath;
        if (os.toLowerCase().startsWith("win")) {
            ftlPath = path.substring(1).replace("target/test-classes/", "src/main/resources/");
            savePath = path.substring(1).replace("target/test-classes/", "src/test/resources/");
        } else {
            ftlPath = path.replace("target/test-classes/", "src/main/resources/");
            savePath = path.replace("target/test-classes/", "src/test/resources/");
        }

        FileInputStream fileInputStream = new FileInputStream(savePath + file);
        JSONArray gson = new JSONArray();
        ModuleInfos<TableSchema> excelUtils = new ModuleInfos(file, fileInputStream, TableSchema.class);
        List<ModuleInfo> moduleInfosList =  excelUtils.getModuleInfos();
        System.out.println(gson.toJSONString(moduleInfosList));

        List<RenderingRequest> renderingRequests=new ArrayList<>();
        // 后面读取yml,实现定义生成
        RenderingRequest renderingRequest = new RenderingRequest();
        renderingRequest.setFtlPath(ftlPath + "templates");
        renderingRequest.setFtlName("ddl.ftl");
        renderingRequest.setSavePath(savePath);
        renderingRequest.setModuleInfos(excelUtils.getModuleInfos());
        renderingRequest.setCover(true);


        FreeMarkerUtils.process(renderingRequest);
        FreeMarkerUtils.addFtlProcessor(new DdlProcessor());
        FreeMarkerUtils.process(renderingRequest);






        FreeMarkerUtils.addFtlProcessor(new DdlProcessor()).add(new DaoProcessor());
        moduleInfosList.forEach(moduleInfo -> {

            RenderingRequest daoRenderingRequest = new RenderingRequest();
            daoRenderingRequest.setFtlPath(ftlPath + "templates");
            daoRenderingRequest.setFtlName("dao.ftl");
            daoRenderingRequest.setSavePath(savePath);
            daoRenderingRequest.setModuleInfos(excelUtils.getModuleInfos());
            daoRenderingRequest.setCover(true);
            daoRenderingRequest.setModuleName(moduleInfo.getModuleName());


            RenderingRequest entityRenderingRequest = new RenderingRequest();
            entityRenderingRequest.setFtlPath(ftlPath + "templates");
            entityRenderingRequest.setFtlName("entity.ftl");
            entityRenderingRequest.setSavePath(savePath);
            entityRenderingRequest.setModuleInfos(excelUtils.getModuleInfos());
            entityRenderingRequest.setCover(true);
            entityRenderingRequest.setModuleName(moduleInfo.getModuleName());

            renderingRequests.add(entityRenderingRequest);
            renderingRequests.add(daoRenderingRequest);

            renderingRequests.forEach(renderingRequest1 -> FreeMarkerUtils.process(renderingRequest1));
        });
    }
}
