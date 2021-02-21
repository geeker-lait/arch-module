package org.arch.framework.automate;

import com.alibaba.fastjson.JSONArray;
import org.arch.framework.automate.common.metadata.DatabaseInfo;
import org.arch.framework.automate.common.utils.FreeMarkerUtils;
import org.arch.framework.automate.generater.builder.DaoProcessor;
import org.arch.framework.automate.generater.builder.DdlProcessor;
import org.arch.framework.automate.generater.builder.EntityProcessor;
import org.arch.framework.automate.generater.builder.PomProcessor;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.render.RenderingRequest;

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
        String file = "uni-life-form.xlsx";
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
        List<DatabaseInfo> databaseInfosList =  excelUtils.getDatabaseInfos();
        System.out.println(gson.toJSONString(databaseInfosList));

        List<RenderingRequest> renderingRequests=new ArrayList<>();
        // 后面读取yml,实现定义生成
        RenderingRequest renderingRequest = new RenderingRequest();
        renderingRequest.setFtlPath(ftlPath + "templates");
        renderingRequest.setFtlName("ddl.ftl");

        renderingRequest.setSavePath(savePath);
        renderingRequest.setDatabaseInfos(excelUtils.getDatabaseInfos());
        renderingRequest.setCover(true);


        FreeMarkerUtils.process(renderingRequest);
        FreeMarkerUtils.addFtlProcessor(new DdlProcessor());
        FreeMarkerUtils.process(renderingRequest);






        FreeMarkerUtils.addFtlProcessor(new DdlProcessor());
        FreeMarkerUtils.addFtlProcessor(new DaoProcessor());
        FreeMarkerUtils.addFtlProcessor(new PomProcessor());
        FreeMarkerUtils.addFtlProcessor(new EntityProcessor());
        databaseInfosList.forEach(databaseInfo -> {

            RenderingRequest pomRenderingRequest = new RenderingRequest();
            pomRenderingRequest.setFtlPath(ftlPath + "templates");
            pomRenderingRequest.setFtlName("pom.ftl");
            pomRenderingRequest.setSavePath(savePath);
            pomRenderingRequest.setDatabaseInfos(excelUtils.getDatabaseInfos());
            pomRenderingRequest.setCover(true);
            pomRenderingRequest.setModuleName(databaseInfo.getModuleName());


            RenderingRequest daoRenderingRequest = new RenderingRequest();
            daoRenderingRequest.setFtlPath(ftlPath + "templates");
            daoRenderingRequest.setFtlName("dao.ftl");
            daoRenderingRequest.setPackageName("org.arch.projects");
            daoRenderingRequest.setSavePath(savePath);
            daoRenderingRequest.setDatabaseInfos(excelUtils.getDatabaseInfos());
            daoRenderingRequest.setCover(true);
            daoRenderingRequest.setModuleName(databaseInfo.getModuleName());


            RenderingRequest entityRenderingRequest = new RenderingRequest();
            entityRenderingRequest.setFtlPath(ftlPath + "templates");
            entityRenderingRequest.setFtlName("entity.ftl");
            entityRenderingRequest.setPackageName("org.arch.projects");
            entityRenderingRequest.setSavePath(savePath);
            entityRenderingRequest.setDatabaseInfos(excelUtils.getDatabaseInfos());
            entityRenderingRequest.setCover(true);
            entityRenderingRequest.setModuleName(databaseInfo.getModuleName());


            renderingRequests.add(pomRenderingRequest);
            renderingRequests.add(entityRenderingRequest);
            renderingRequests.add(daoRenderingRequest);

            renderingRequests.forEach(r -> FreeMarkerUtils.process(r));
        });
    }
}
