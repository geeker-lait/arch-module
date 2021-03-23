package org.arch.framework.automate.generater.xmind;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.ParamProperties;
import org.arch.framework.automate.generater.properties.XmindProperties;
import org.arch.framework.automate.generater.core.SchemaMetadata;
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
public class XmindService {
    /**
     * 获取xmind中实体的定义
     *
     * @param res
     * @param configuration
     * @return
     */
    public List<? extends SchemaMetadata> getEntityMetadate(String res, Map<String, String> configuration) {
        return new ArrayList<>();
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
        TopicNode topicNode = convertTopic(xMinds);
        List<XmindProperties> xmindProperties = new ArrayList<>();
        convertProperties("", topicNode, xmindProperties);
        return xmindProperties;
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

    private TopicNode convertTopic(List<XMind> xMinds) throws Exception {
        TopicNode topicNode = new TopicNode();
        XMindNode xMindNode = xMinds.get(0).getRootTopic();
        //第一层默认项目级别
        String[] titles = xMindNode.getTitle().split(":");
        if (titles.length != 3) {
            throw new Exception("title解析错误");
        }
//        TopicType topicType = TopicType.getTopicType(titles[0]);
//        if (topicType == null) {
//            throw new Exception("不存在的topic类型");
//        }
        //todo
        topicNode.setId(System.currentTimeMillis());
        topicNode.setTyp(titles[0]);
        topicNode.setValue(titles[1]);
        topicNode.setDescr(titles[2]);
        recursionXmind(xMindNode.getChildren().getAttached(), topicNode);
        log.info(JSON.toJSONString(topicNode));
        return topicNode;
    }

    /**
     * 递归调用
     *
     * @param xMindNodes
     * @param pTopicNode
     * @throws Exception
     */
    private void recursionXmind(List<XMindNode> xMindNodes, TopicNode pTopicNode) throws Exception {
        if (!CollectionUtils.isEmpty(xMindNodes)) {
            List<TopicNode> topicNodes = new ArrayList<>();
            for (XMindNode xMindNode : xMindNodes) {
                String[] titles = xMindNode.getTitle().split(":");
//                if (titles.length != 3) {
//                    continue;
//                }
//                TopicType topicType = TopicType.getTopicType(titles[0]);
//                if (topicType == null) {
//                    log.warn("不存在的topic类型:{}", titles[0]);
//                }

                TopicNode topicNode = new TopicNode();
                topicNode.setId(System.currentTimeMillis());
                if (titles.length == 3) {
                    topicNode.setTyp(titles[0]);
                    topicNode.setValue(titles[1]);
                    topicNode.setDescr(titles[2]);
                } else if (titles.length == 2) {
                    topicNode.setTyp(titles[0]);
                    topicNode.setValue(titles[1]);
                }
                else if (titles.length == 1) {
                    topicNode.setValue(titles[0]);
                } else {
                    continue;
                }

                if (xMindNode.getChildren() != null) {
                    recursionXmind(xMindNode.getChildren().getAttached(), topicNode);
                }
                topicNodes.add(topicNode);
            }
            pTopicNode.setChildNodes(topicNodes);
        }
    }

    private void convertProperties(String pkgName, TopicNode topicNode, List<XmindProperties> xmindPropertiesList) throws Exception {
        XmindProperties xmindProperties = new XmindProperties();
        if (TopicTyp.PROJECT.getType().equals(topicNode.getTyp())) {
            for (TopicNode topicNode1 : topicNode.getChildNodes()) {
                convertProperties(topicNode.getValue(), topicNode1, xmindPropertiesList);
            }
        }
        //模块、包，拼接名称
        else if (TopicTyp.MODULE.getType().equals(topicNode.getTyp()) || TopicTyp.PKG.getType().equals(topicNode.getTyp())) {
            if (StringUtils.isNotEmpty(pkgName)) {
                pkgName = pkgName + "." + topicNode.getValue();
            }
            for (TopicNode topicNode1 : topicNode.getChildNodes()) {
                convertProperties(pkgName, topicNode1, xmindPropertiesList);
            }
        } else if (TopicTyp.API.getType().equals(topicNode.getTyp())) {
            if (pkgName.endsWith(".")) {
                pkgName = pkgName.substring(0, pkgName.lastIndexOf("."));
            }
            xmindProperties.setPkg(pkgName);
            xmindProperties.setTopicName(topicNode.getValue());
            xmindProperties.setDescr(topicNode.getDescr());
            //拼装方法
            if (!CollectionUtils.isEmpty(topicNode.getChildNodes())) {
                List<MethodProperties> methodPropertiesList = new ArrayList<>();
                for (TopicNode methodNode : topicNode.getChildNodes()) {
                    MethodProperties methodProperties = new MethodProperties();
                    methodProperties.setDescr(methodNode.getDescr());
                    methodProperties.setName(methodNode.getValue());
                    methodProperties.setHttpMethod(methodNode.getTyp());
                    //拼装参数
                    if (!CollectionUtils.isEmpty(methodNode.getChildNodes())) {
                        List<ParamProperties> inputs = new ArrayList<>();
                        for (TopicNode paramNode : methodNode.getChildNodes()) {
                            if (TopicTyp.INPUT.getType().equals(paramNode.getTyp())) {
                                ParamProperties paramProperties = new ParamProperties();
                                paramProperties.setDescr(paramNode.getDescr());
                                paramProperties.setJavaTyp(paramNode.getTyp());
                                paramProperties.setType(TopicTyp.INPUT.getType());
                                paramProperties.setName(paramNode.getValue());
                                if (!CollectionUtils.isEmpty(paramNode.getChildNodes())) {
                                    convertParams(paramNode.getChildNodes(), paramProperties);
                                }
                                inputs.add(paramProperties);
                            } else if (TopicTyp.OUTPUT.getType().equals(paramNode.getTyp())) {
                                ParamProperties paramProperties = new ParamProperties();
                                paramProperties.setDescr(paramNode.getDescr());
                                paramProperties.setJavaTyp(paramNode.getTyp());
                                paramProperties.setType(TopicTyp.OUTPUT.getType());
                                paramProperties.setName(paramNode.getValue());
                                if (!CollectionUtils.isEmpty(paramNode.getChildNodes())) {
                                    convertParams(paramNode.getChildNodes(), paramProperties);
                                }
                                methodProperties.setOutput(paramProperties);
                            }
                        }
                        methodProperties.setInput(inputs);
                    }
                    methodPropertiesList.add(methodProperties);
                }
                xmindProperties.setApis(methodPropertiesList);
            }
            xmindPropertiesList.add(xmindProperties);
        }


    }

    private void convertParams(List<TopicNode> Params, ParamProperties pParamProperties) {
        List<ParamProperties> paramPropertiesList = new ArrayList<>();
        for (TopicNode paramNode : Params) {
            ParamProperties paramProperties = new ParamProperties();
            paramProperties.setDescr(paramNode.getDescr());
            //类型和名称颠倒，xmind中没有按照顺序来
            paramProperties.setJavaTyp(paramNode.getValue());
            paramProperties.setType("");
            paramProperties.setName(paramNode.getTyp());
            if (!CollectionUtils.isEmpty(paramNode.getChildNodes())) {
                convertParams(paramNode.getChildNodes(), paramProperties);
            }
            paramPropertiesList.add(paramProperties);
        }
        pParamProperties.setChilds(paramPropertiesList);
    }
}
