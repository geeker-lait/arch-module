package org.arch.framework.automate.generater.xmind;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.ParamProperties;
import org.arch.framework.automate.generater.properties.XmindProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 2:39 PM
 */
@Slf4j
@Service
public class XmindService {

    public XmindProperties parseMetaData(String path) throws Exception {
        List<XMind> xMinds = loadMetaData(path);
        TopicNode topicNode = convertTopic(xMinds);
        List<XmindProperties> xmindProperties = new ArrayList<>();
        return convertProperties("", topicNode, xmindProperties);
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
                if (titles.length != 3) {
                    continue;
                }
//                TopicType topicType = TopicType.getTopicType(titles[0]);
//                if (topicType == null) {
//                    log.warn("不存在的topic类型:{}", titles[0]);
//                }
                TopicNode topicNode = new TopicNode();
                topicNode.setId(System.currentTimeMillis());
                topicNode.setTyp(titles[0]);
                topicNode.setValue(titles[1]);
                topicNode.setDescr(titles[2]);
                if (xMindNode.getChildren() != null) {
                    recursionXmind(xMindNode.getChildren().getAttached(), topicNode);
                }
                topicNodes.add(topicNode);
            }
            pTopicNode.setChildNodes(topicNodes);
        }
    }

    private XmindProperties convertProperties(String pkgName, TopicNode topicNode, List<XmindProperties> xmindPropertiesList) throws Exception {
        XmindProperties xmindProperties = new XmindProperties();
        //模块、包，拼接名称
        if (TopicTyp.MODULE.getType().equals(topicNode.getTyp()) || TopicTyp.PKG.getType().equals(topicNode.getTyp())) {
            if (StringUtils.isNotEmpty(pkgName)) {
                pkgName = pkgName + "." + topicNode.getValue();
            }
            for (TopicNode topicNode1 : topicNode.getChildNodes()) {
                convertProperties(pkgName, topicNode1, xmindPropertiesList);
            }
        } else if (TopicTyp.API.getType().equals(topicNode.getTyp())) {
            xmindProperties.setPkg(pkgName);
            xmindProperties.setTopicName(topicNode.getValue());
            xmindProperties.setDescr(topicNode.getDescr());
            //拼装方法
            if (!CollectionUtils.isEmpty(topicNode.getChildNodes())) {
                for (TopicNode methodNode : topicNode.getChildNodes()) {
                    MethodProperties methodProperties = new MethodProperties();
                    methodProperties.setDescr(methodNode.getDescr());
                    methodProperties.setName(methodNode.getValue());
                    methodProperties.setHttpMethod(methodNode.getTyp());
                    //拼装参数
                    if (!CollectionUtils.isEmpty(methodNode.getChildNodes())) {
                        for (TopicNode paramNode : methodNode.getChildNodes()) {
                            if (TopicTyp.INPUT.getType().equals(paramNode.getTyp())) {

                            } else if (TopicTyp.OUTPUT.getType().equals(paramNode.getTyp())) {
                                ParamProperties paramProperties=new ParamProperties();
                                paramProperties.setDescr(paramNode.getDescr());
                                paramProperties.setJavaTyp(TopicTyp.OUTPUT.getType());
                                paramProperties.setType(paramNode.getTyp());
                                paramProperties.setName(paramNode.getValue());
                            }
                        }
                    }
                }
            }

        }


    }
}
