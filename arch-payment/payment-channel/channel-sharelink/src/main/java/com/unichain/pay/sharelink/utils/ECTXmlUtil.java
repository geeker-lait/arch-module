package com.unichain.pay.sharelink.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * @author Administrator
 */
public class ECTXmlUtil {

    //请求标签
    public static final String CPREQ_IAREQ = "IAReq";    //签约
    public static final String CPREQ_CPREQ = "CPReq";    //支付
    public static final String CPREQ_QUREQ = "SQReq";    //单笔查询
    public static final String CPREQ_SRREQ = "SRReq";    //退货撤销
    public static final String CPREQ_QSOREQ = "QSOReq";    //机构查询
    public static final String CPREQ_SPREQ = "SPReq";   //代付
    public static final String CPREQ_EPREQ = "EPReq";   //对公代付
    public static final String CPREQ_BCRES = "BatReq";   //送盘文件
    public static final String CPREQ_QBATREQ = "QBatReq"; //查询接口
    public static final String CPREQ_CIAREQ = "CIAReq";  //解约请求
    public static final String CPREQ_QPREQ = "QPReq";  //快捷支付请求
    public static final String CPREQ_QIAREQ = "QIAReq";    //快捷支付-身份认证
    public static final String CPREQ_SIREQ = "SIReq";    //快捷支付-签约
    public static final String CPREQ_SCREQ = "SCReq";    //快捷支付-解约
    public static final String CPREQ_TSREQ = "TSReq";    //快捷支付-查询交易状态
    public static final String CPREQ_ATREQ = "ATReq";    //认证支付请求
    public static final String CPREQ_AMSREQ = "AMSReq";    //认证支付短信验证请求
    public static final String CPREQ_ARREQ = "ARReq";    //认证支付-退款
    //返回标签
    public static final String CPRES_QBCRES = "NoticeRes";  //回盘文件
    public static final String ECTDATA = "EctData";
    public static final String MESSAGE = "Message";
    public static final String MESSAGE_ID = "id";
    public static final String CSREQ = "CSReq";
    public static final String CSRES = "CSRes";
    private static final Logger logger = LoggerFactory.getLogger(ECTXmlUtil.class);

    public static String mapToXml(Map<String, Object> map, String CSReqId) {

        if (map == null)
            return "";
        Document document = DocumentHelper.createDocument();
        Element ectData = document.addElement(ECTXmlUtil.ECTDATA);
        Element message = ectData.addElement(ECTXmlUtil.MESSAGE);

        message.addAttribute(ECTXmlUtil.MESSAGE_ID, UUIDUtils.getUUIDString(16));
        Element csReq = message.addElement(ECTXmlUtil.CSREQ);
        csReq.addAttribute(ECTXmlUtil.MESSAGE_ID, CSReqId);

        for (Map.Entry<String, Object> m : map.entrySet()) {
            Element keyElement = csReq.addElement(m.getKey());
            keyElement.setText(m.getValue() == null ? "" : m.getValue().toString());
        }

        return doc2String(document);

    }

    public static Map<String, String> xmlToMap(String xmlString) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            Document document = DocumentHelper.parseText(xmlString);
            Element nodeElement = document.getRootElement();
            nodeElement.elements();
            nodeElement.element(ECTXmlUtil.MESSAGE).elements();
            Element CPRes = nodeElement.element(ECTXmlUtil.MESSAGE).element(ECTXmlUtil.CSRES);
            @SuppressWarnings("unchecked")
            List<Element> node = CPRes.elements();
            for (Iterator<Element> it = node.iterator(); it.hasNext(); ) {
                Element element = it.next();
                map.put(element.getName(), element.getText());
                element = null;
            }
            node = null;
            nodeElement = null;
            document = null;
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询支持机构专用的报文解析方法
     * 介于返回报文不再是简单的单级标签，而是多级嵌套标签，通用的解析方法不再适用
     * 所以单独写了一个解析报文的方法。
     *
     * @param xmlString
     * @return
     */
    public static Map<String, String> supportOrganXmlToMap(String xmlString) {

        try {
            Map<String, String> map = new TreeMap<String, String>();
            Document document = DocumentHelper.parseText(xmlString);
            Element nodeElement = document.getRootElement();
            nodeElement.elements();
            nodeElement.element(ECTXmlUtil.MESSAGE).elements();
            Element CPRes = nodeElement.element(ECTXmlUtil.MESSAGE).element(ECTXmlUtil.CSRES);
            map.put("merchantId", CPRes.element("merchantId").getText());
            map.put("retFlag", CPRes.element("retFlag").getText());
            map.put("resultCode", CPRes.element("resultCode").getText());
            map.put("resultMsg", CPRes.element("resultMsg").getText());
            map.put("sign", CPRes.element("sign").getText());
            map.put("organDigest", CPRes.element("organDigest").getText());
            nodeElement = null;
            document = null;
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析支持机构查询返回报文出错");
        }
        return null;

    }

    public static String doc2String(Document document) {
        String string = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码
            OutputFormat format = new OutputFormat(" ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            string = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return string;
    }

}
