package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/24/2021 9:36 PM
 */
@Slf4j
@Component
public class PomBuilder extends AbstractBuilder implements Buildable {

    @Autowired
    private GeneratorConfig generatorConfig;


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.POM;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {
        buildPom(projectProperties.getCover(), path, pomProperties, engine);
    }

    private void buildPom(boolean cover, Path path, PomProperties pomProperties, TemplateEngine engine) {
        Path pomFilePath = Paths.get(path.toString().concat(File.separator).concat("pom.xml"));
        // 写入文件
        if (Files.exists(pomFilePath)) {
            // 是否覆盖
            if (!cover) {
                log.info("skip {} due to file exists.", path);
                return;
            } else {
                try {
                    Files.delete(pomFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (pomProperties.isRoot()) {
            if (null != pomProperties.getDependencyManagement()) {
                //pomProperties.getDependencyManagement().addAll(DEPS.get());
            } else {
                //pomProperties.setDependencies(DEPS.get());
            }
            //DEPS.remove();
        }
        JSONObject jsonObject = JSONUtil.parseObj(pomProperties);
        Set<String> dt = pomProperties.getDocumentTyps();
        String appname = dt.stream().filter(t -> t.equalsIgnoreCase(TemplateName.APPLICATION.name())).findAny().orElse(null);
        if (null != appname) {
            jsonObject.putOpt(appname, true);
        }
        // 获取并渲染模板
        engine.getTemplate("pom.ftl").render(jsonObject, new File(pomFilePath.toString()));
        log.info("\n\ncreate pom file in : {}\npom json :{}\n\n", pomFilePath, pomProperties);
    }

    /**
     * 建立树形结构
     *
     * @return
     */
    public List<PomProperties> buildNode() {
        PomProperties pomProperties = generatorConfig.getProject().getPom();
        List<PomProperties> treeFolder = new ArrayList();
        for (PomProperties folderNode : getRootNode(pomProperties)) {
            buildChildNode(folderNode);
//            folderNode = buildChildFolder(folderNode);
//            treeFolder.add(folderNode);
        }
        return treeFolder;
    }

    /**
     * 递归，建立子树形结构
     *
     * @param pomProperties
     * @return
     */
    private PomProperties buildChildNode(PomProperties pomProperties) {
        for (PomProperties pomNode : pomProperties.getModules()) {
            if (pomNode.getModules() == null || pomNode.getModules().size() == 0) {
                // todo
            }
            buildChildNode(pomNode);
            return null;
        }
        return null;
    }

    /**
     * 获取根节点
     *
     * @param pomProperties
     * @return
     */
    private List<PomProperties> getRootNode(PomProperties pomProperties) {
        List<PomProperties> rootFolderLists = new ArrayList();
        for (PomProperties pomNode : pomProperties.getModules()) {
            if (pomNode.getModules() == null || pomNode.getModules().size() == 0) {
                rootFolderLists.add(pomNode);
            }
        }
        return rootFolderLists;
    }


    /**
     * tree->list
     *
     * @param pomList
     * @return
     */
    private List<PomProperties> treeNodeToList(List<PomProperties> pomList) {
        List<PomProperties> result = new ArrayList<>();
        for (PomProperties pom : pomList) {
            result.add(pom);
            Set<PomProperties> child = pom.getModules();
            if (child != null && child.size() > 0) {
                //List<PomProperties> entityList = this.treeNodeToList(child);
                //result.addAll(entityList);
            }
        }
        if (result.size() > 0) {
            for (PomProperties pom : result) {
               // pom.setModules(null);
            }
        }
        return result;
    }


//
//    /**
//     * list->tree
//     *
//     * @param pomProperties
//     * @param allList
//     * @return
//     */
//    private List<PomProperties> listToTreeNode(PomProperties pomProperties, List<PomProperties> allList) {
//        List<PomProperties> pomList = new ArrayList<>();
//        for (PomProperties pom : allList) {
//            if (pom.getPid().equals(pomProperties.getId())) {
//                if (pomProperties.getModules() == null) {
//                    pomProperties.setModules(new ArrayList<>());
//                }
//                pomProperties.getModules().add(findChild(pom, allList));
//            }
//        }
//        pomList.add(pomProperties);
//        return pomList;
//    }
//
//    /**
//     * 查找子节点
//     *
//     * @param pomProperties
//     * @param allList
//     * @return
//     */
//    private PomProperties findChild(PomProperties pomProperties, List<PomProperties> allList) {
//        for (PomProperties pom : allList) {
//            if (pom.getPid().equals(pomProperties.getId())) {
//                if (pomProperties.getModules() == null) {
//                    pomProperties.setModules(new ArrayList<>());
//                }
//                pomProperties.getModules().add(findChild(pom, allList));
//            }
//        }
//        return pomProperties;
//    }

}
