package org.arch.framework.automate.generater.xmind;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.common.utils.ChangeToPinYinJP;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.core.SchemaService;
import org.arch.framework.automate.generater.properties.*;
import org.arch.framework.beans.utils.UnZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 2:39 PM
 */
@Slf4j
@Service
public class XmindSchemaService implements SchemaService {
    @Autowired
    ChangeToPinYinJP changeToPinYinJP;

    /**
     * 获取xmind中实体/表的定义
     *
     * @return
     */
    @Override
    public List<TableProperties> getTableProperties() {
        return null;
    }

    /**
     * 获取xmind中的api/方法定义
     *
     * @return
     */
    @Override
    public List<MethodProperties> getApiProperties() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public List<SchemaMetadata> getSchemaMatedatas() {
        return null;
    }


    /**
     * 获取xmind中的api定义
     *
     * @param res
     * @param configuration
     * @return
     */
    public List<? extends SchemaMetadata> getApiMetadate(String res, Map<String, String> configuration) {
        // 从类路劲加载
        if (-1 != res.indexOf("classpath:")) {
            res = new ClassPathResource(res.split(":")[1]).getAbsolutePath();
        }
        String destDirPath = "../unzip";
        try {
            File file = new File(res);
            UnZipUtil.unZip(file, destDirPath);
        } catch (Exception e) {
            log.error("解压xmind文件异常：res:{}", res, e);
        }
        try {
            return parseMetaData(destDirPath.concat(File.separator).concat("content.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<XmindProperties> parseMetaData(String path) throws Exception {
        List<XMind> xMinds = loadMetaData(path);
//        TopicNode topicNode = convertTopic(xMinds);
        List<XmindProperties> xmindProperties = new ArrayList<>();
//        convertProperties("", topicNode, xmindProperties);
        //第一层默认project
        XMindNode projectNode = xMinds.get(0).getRootTopic();
        XmindProject xmindProject = new XmindProject();
        convertProperties("", projectNode, null, xmindProject);
        return xmindProject.getModules();
    }


    //加载元数据
    private List<XMind> loadMetaData(String path) throws Exception {
        List<XMind> xMinds = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(path);
            String jsonStr = IOUtils.toString(inputStream, "utf8");
            xMinds = JSONObject.parseObject(jsonStr, new TypeReference<List<XMind>>() {
            });
            log.info("解析出的xmind文件json：{}", JSON.toJSONString(xMinds));
        } catch (IOException e) {
            log.error("加载元数据io异常,path:{}", path, e);
            throw new Exception("加载元数据异常");
        } catch (Exception e) {
            log.error("加载元数据异常,path:{}", path, e);
            throw new Exception("加载元数据异常");
        }
        return xMinds;
    }


    private void convertParams(List<XMindNode> Params, ParamProperties pParamProperties) {
        List<ParamProperties> paramPropertiesList = new ArrayList<>();
        for (XMindNode paramNode : Params) {
            String paramNodeTitle = paramNode.getTitle();
            String[] paramNodeTitles = paramNodeTitle.split(":", 3);
            //类型
            String paramType = "";
            //名称
            String paramValue = "";
            //描述
            String paramDesc = "";
            if (paramNodeTitles.length == 3) {
                paramType = paramNodeTitles[0];
                paramValue = paramNodeTitles[1];
                paramDesc = paramNodeTitles[2];
                //有描述没有名称，用拼音替换
                if (StringUtils.isEmpty(paramValue) && StringUtils.isNotEmpty(paramDesc)) {
                    paramValue = changeToPinYinJP.changeToTonePinYin(paramDesc);
                }

            } else {

            }
            ParamProperties paramProperties = new ParamProperties();
            paramProperties.setDescr(paramDesc);
            //类型和名称颠倒，xmind中没有按照顺序来
            paramProperties.setJavaTyp(paramValue);
            paramProperties.setType("");
            paramProperties.setName(paramType);
            if (paramNode != null && paramNode.getChildren() != null &&
                    !CollectionUtils.isEmpty(paramNode.getChildren().getAttached())) {
                convertParams(paramNode.getChildren().getAttached(), paramProperties);
            }
            paramPropertiesList.add(paramProperties);
        }
        pParamProperties.setChilds(paramPropertiesList);
    }


    /**
     * 递归生成xmindProperties
     *
     * @param pkgName          包名称
     * @param xMindNode
     * @param pXmindProperties 父节点
     * @throws Exception
     */
    private void convertProperties(String pkgName, XMindNode xMindNode, XmindProperties pXmindProperties, XmindProject xmindProject) throws Exception {
        String title = xMindNode.getTitle();
        String[] titles = title.split(":", 3);
        //类型
        String type = "";
        //名称
        String name = "";
        //描述
        String description = "";
        if (titles.length == 3) {
            type = titles[0];
            name = titles[1];
            description = titles[2];
            //有描述没有名称，用拼音替换
            if (StringUtils.isEmpty(name) && StringUtils.isNotEmpty(description)) {
                name = changeToPinYinJP.changeToTonePinYin(description);
            }
        } else {

        }
        //层级从project开始
        if (TopicTyp.PROJECT.getType().equals(type)) {
            pkgName = name;
            if (xMindNode != null && xMindNode.getChildren() != null && !CollectionUtils.isEmpty(xMindNode.getChildren().getAttached())) {
                XmindProperties xmindProperties = new XmindProperties();
                xmindProperties.setTopicName(name);
                xmindProperties.setPkg(pkgName);
                xmindProperties.setDescr(description);
                for (XMindNode children : xMindNode.getChildren().getAttached()) {
                    convertProperties(pkgName, children, xmindProperties, xmindProject);
                }
                xmindProject.getModules().add(xmindProperties);
            }
        }
        //递归生成模块
        else if (TopicTyp.MODULE.getType().equals(type)) {
            if (StringUtils.isNotEmpty(pkgName)) {
                pkgName = pkgName + "." + name;
            }
            XmindProperties xmindProperties = new XmindProperties();
            xmindProperties.setTopicName(name);
            xmindProperties.setPkg(pkgName);
            xmindProperties.setDescr(description);
            if (xMindNode != null && xMindNode.getChildren() != null && !CollectionUtils.isEmpty(xMindNode.getChildren().getAttached())) {
                for (XMindNode children : xMindNode.getChildren().getAttached()) {
                    convertProperties(pkgName, children, xmindProperties, xmindProject);
                }
            }
            pXmindProperties.getChildren().add(xmindProperties);
        } else if (TopicTyp.PKG.getType().equals(type)) {
            if (StringUtils.isNotEmpty(pkgName)) {
                pkgName = pkgName + "." + name;
            }
            XmindProperties xmindProperties = new XmindProperties();
            xmindProperties.setTopicName(name);
            xmindProperties.setPkg(pkgName);
            xmindProperties.setDescr(description);
            if (xMindNode != null && xMindNode.getChildren() != null && !CollectionUtils.isEmpty(xMindNode.getChildren().getAttached())) {
                for (XMindNode children : xMindNode.getChildren().getAttached()) {
                    convertProperties(pkgName, children, pXmindProperties, xmindProject);
                }
            }
            pXmindProperties.getChildren().add(xmindProperties);

        } else if (TopicTyp.API.getType().equals(type)) {
            if (pkgName.endsWith(".")) {
                pkgName = pkgName.substring(0, pkgName.lastIndexOf("."));
            }
            XmindProperties xmindProperties = new XmindProperties();
            xmindProperties.setPkg(pkgName);
            xmindProperties.setPattern(SchemaPattern.API.getPattern());
            xmindProperties.setTopicName(name);
            xmindProperties.setDescr(description);
            //拼装方法
            if (xMindNode != null && xMindNode.getChildren() != null && !CollectionUtils.isEmpty(xMindNode.getChildren().getAttached())) {
                for (XMindNode methodNode : xMindNode.getChildren().getAttached()) {
                    String methodNodeTitle = methodNode.getTitle();
                    String[] methodNodeTitles = methodNodeTitle.split(":", 3);
                    //类型
                    String httpMethod = "";
                    //名称
                    String methodName = "";
                    //描述
                    String methodDesc = "";
                    if (methodNodeTitles.length == 3) {
                        httpMethod = methodNodeTitles[0];
                        methodName = methodNodeTitles[1];
                        methodDesc = methodNodeTitles[2];
                        //有描述没有名称，用拼音替换
                        if (StringUtils.isEmpty(methodName) && StringUtils.isNotEmpty(methodDesc)) {
                            methodName = changeToPinYinJP.changeToTonePinYin(methodDesc);
                        }

                    } else {

                    }
                    MethodProperties methodProperties = new MethodProperties();
                    methodProperties.setDescr(methodDesc);
                    methodProperties.setName(methodName);
                    methodProperties.setHttpMethod(httpMethod);
                    //拼装参数
                    if (methodNode != null && methodNode.getChildren() != null && !CollectionUtils.isEmpty(methodNode.getChildren().getAttached())) {
                        List<ParamProperties> inputs = new ArrayList<>();
                        for (XMindNode paramNode : methodNode.getChildren().getAttached()) {
                            String paramNodeTitle = paramNode.getTitle();
                            String[] paramNodeTitles = paramNodeTitle.split(":", 3);
                            //类型
                            String paramType = "";
                            //名称
                            String paramValue = "";
                            //描述
                            String paramDesc = "";
                            if (paramNodeTitles.length == 3) {
                                paramType = paramNodeTitles[0];
                                paramValue = paramNodeTitles[1];
                                paramDesc = paramNodeTitles[2];
                                //有描述没有名称，用拼音替换
                                if (StringUtils.isEmpty(paramValue) && StringUtils.isNotEmpty(paramDesc)) {
                                    paramValue = changeToPinYinJP.changeToTonePinYin(paramDesc);
                                }

                            } else {

                            }
                            if (TopicTyp.INPUT.getType().equals(paramType)) {
                                ParamProperties paramProperties = new ParamProperties();
                                paramProperties.setDescr(paramDesc);
                                paramProperties.setJavaTyp(paramValue);
                                paramProperties.setType(TopicTyp.INPUT.getType());
                                paramProperties.setName(paramValue);
                                if (paramNode != null && paramNode.getChildren() != null && !CollectionUtils.isEmpty(paramNode.getChildren().getAttached())) {
                                    convertParams(paramNode.getChildren().getAttached(), paramProperties);
                                }
                                inputs.add(paramProperties);
                            } else if (TopicTyp.OUTPUT.getType().equals(paramType)) {
                                ParamProperties paramProperties = new ParamProperties();
                                paramProperties.setDescr(paramDesc);
                                paramProperties.setJavaTyp(paramValue);
                                paramProperties.setType(TopicTyp.OUTPUT.getType());
                                paramProperties.setName(paramValue);
                                if (StringUtils.isEmpty(paramValue) && paramNode != null && paramNode.getChildren() != null && !CollectionUtils.isEmpty(paramNode.getChildren().getAttached())) {
                                    paramProperties.setName("map");
                                }
                                if (paramNode != null && paramNode.getChildren() != null && !CollectionUtils.isEmpty(paramNode.getChildren().getAttached())) {
                                    convertParams(paramNode.getChildren().getAttached(), paramProperties);
                                }
                                methodProperties.setOutput(paramProperties);
                            }
                        }
                        methodProperties.setInput(inputs);
                    }
                    xmindProperties.getApis().add(methodProperties);
                }
            }
            pXmindProperties.getChildren().add(xmindProperties);
        } else if (TopicTyp.ENTITY.getType().equals(type)) {
            if (pkgName.endsWith(".")) {
                pkgName = pkgName.substring(0, pkgName.lastIndexOf("."));
            }
            TableProperties tableProperties = new TableProperties();
            tableProperties.setName(name);
            tableProperties.setComment(description);
            pXmindProperties.setPattern(SchemaPattern.MVC.getPattern());
            //拼装方法
            if (xMindNode != null && xMindNode.getChildren() != null && !CollectionUtils.isEmpty(xMindNode.getChildren().getAttached())) {

                List<ColumnsProperties> columnsPropertiesList = new ArrayList<>();
                for (XMindNode paramNode : xMindNode.getChildren().getAttached()) {
                    String paramNodeTitle = paramNode.getTitle();
                    String[] paramNodeTitles = paramNodeTitle.split(":", 3);
                    //类型
                    String paramType = "";
                    //名称
                    String paramValue = "";
                    //描述
                    String paramDesc = "";
                    if (paramNodeTitles.length == 3) {
                        paramType = paramNodeTitles[0];
                        paramValue = paramNodeTitles[1];
                        paramDesc = paramNodeTitles[2];
                        //有描述没有名称，用拼音替换
                        if (StringUtils.isEmpty(paramValue) && StringUtils.isNotEmpty(paramDesc)) {
                            paramValue = changeToPinYinJP.changeToTonePinYin(paramDesc);
                        }

                    } else {

                    }
                    ColumnsProperties columnsProperties = new ColumnsProperties();
                    columnsProperties.setTyp(paramValue);
                    columnsProperties.setName(paramType);
                    columnsProperties.setComment(paramDesc);
                    columnsPropertiesList.add(columnsProperties);
                }
                tableProperties.setColumns(columnsPropertiesList);
            }
            pXmindProperties.getTables().add(tableProperties);
        }
    }


}
