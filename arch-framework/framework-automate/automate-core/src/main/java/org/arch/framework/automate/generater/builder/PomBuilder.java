package org.arch.framework.automate.generater.builder;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/24/2021 9:36 PM
 */
@Slf4j
@Component
public class PomBuilder {

    @Autowired
    private GeneratorConfig generatorConfig;

    //private List<PomProperties> pomList = new ArrayList();

    /*public PomBuilder(List<PomProperties> pomList) {
        this.pomList = pomList;
    }*/

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
            List<PomProperties> child = pom.getModules();
            if (child != null && child.size() > 0) {
                List<PomProperties> entityList = this.treeNodeToList(child);
                result.addAll(entityList);
            }
        }
        if (result.size() > 0) {
            for (PomProperties pom : result) {
                pom.setModules(null);
            }
        }
        return result;
    }

    /**
     * list->tree
     *
     * @param pomProperties
     * @param allList
     * @return
     */
    private List<PomProperties> listToTreeNode(PomProperties pomProperties, List<PomProperties> allList) {
        List<PomProperties> pomList = new ArrayList<>();
        for (PomProperties pom : allList) {
            if (pom.getPid().equals(pomProperties.getId())) {
                if (pomProperties.getModules() == null) {
                    pomProperties.setModules(new ArrayList<>());
                }
                pomProperties.getModules().add(findChild(pom, allList));
            }
        }
        pomList.add(pomProperties);
        return pomList;
    }

    /**
     * 查找子节点
     *
     * @param pomProperties
     * @param allList
     * @return
     */
    private PomProperties findChild(PomProperties pomProperties, List<PomProperties> allList) {
        for (PomProperties pom : allList) {
            if (pom.getPid().equals(pomProperties.getId())) {
                if (pomProperties.getModules() == null) {
                    pomProperties.setModules(new ArrayList<>());
                }
                pomProperties.getModules().add(findChild(pom, allList));
            }
        }
        return pomProperties;
    }

}
