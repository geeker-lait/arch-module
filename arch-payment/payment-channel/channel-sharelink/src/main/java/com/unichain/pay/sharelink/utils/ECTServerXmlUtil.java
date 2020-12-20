package com.unichain.pay.sharelink.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Macro
 */
public class ECTServerXmlUtil {

    public static final String CPRES_IARES = "IARes";    //签约
    public static final String CPRES_CPRES = "CPRes";    //消费
    public static final String CPRES_QURES = "SQRes";    //查询订单
    public static final String CPRES_SRRES = "SRRes";    //退款
    public static final String CPRES_SPRES = "SPRes";   //代付
    public static final String CPRES_QSORES = "QSORes";    //机构查询
    public static final String CPRES_EPRES = "EPRes";    //对公代付
    public static final String CPRES_BCRES = "BatReq";   //送盘文件
    public static final String CPRES_QBCRES = "NoticeRes";  //回盘文件
    public static final String CPRES_QBATREQ = "QBatReq"; //查询接口
    public static final String CPRES_BTRES = "BTRes"; //银行端解约通知
    public static final String ECTDATA = "EctData";
    public static final String MESSAGE = "Message";
    public static final String MESSAGE_ID = "id";
    public static final String CSREQ = "CSReq";            //请求报文
    public static final String CSRES = "CSRes";            //应答报文
    private static final Logger logger = LoggerFactory.getLogger(ECTServerXmlUtil.class);

    /**
     * Function : 解析报文，将xml报文解析成map后反射为beanClass的实例
     *
     * @param xml
     * @param beanClass
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToObject(String xml, Class<T> beanClass) throws Exception {

        //如果解析失败，可能在这里直接抛错，由外层函数catch
        Map<String, String> map = xmlToMap(xml);
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();
        for (Class clazz = obj.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        }
        return (T) obj;
    }

    /**
     * 证通接受报文：CSReq
     *
     * @param xmlString
     * @return
     */
    public static Map<String, String> xmlToMap(String xmlString) throws Exception {
        try {
            Map<String, String> map = new HashMap<String, String>();
            //此处可能产生异常
            Document document = DocumentHelper.parseText(xmlString);
            Element rootElement = document.getRootElement();
            Element CSReq = rootElement.element(ECTServerXmlUtil.MESSAGE).element(ECTServerXmlUtil.CSREQ);
            List node = CSReq.elements();
            for (Iterator it = node.iterator(); it.hasNext(); ) {
                Element elm = (Element) it.next();
                map.put(elm.getName(), elm.getText());
                elm = null;
            }
            node = null;
            rootElement = null;
            document = null;
            return map;
        } catch (Exception e) {
            //虽然catch了异常，但是并不处理，打印错误日志后直接抛出
            logger.error("解析XML报文异常", e);
            throw e;
        }
    }


    /**
     * Function : 使用BeaUtils将object转成map后再转成xml
     *
     * @param obj
     * @param CPResType
     * @return
     */
    public static String objectToXml(Object obj, String CPResType) {
        Map<String, Object> map = BeanUtils.beanToMap(obj);
        Map<String, String> map2 = new HashMap<String, String>();
        for (Map.Entry<String, Object> m : map.entrySet()) {
            map2.put(m.getKey(), (String) m.getValue());
        }
        return mapToXml(map2, CPResType);
    }

    /**
     * 证通返回报文 ：CPRes
     *
     * @param map
     * @param cSReqId
     * @return
     */
    public static String mapToXml(Map<String, String> map, String CPResId) {
        if (map == null)
            return "";
        //生产xml的document对象
        Document document = DocumentHelper.createDocument();
        Element ectData = document.addElement(ECTServerXmlUtil.ECTDATA);
        Element message = ectData.addElement(ECTServerXmlUtil.MESSAGE);
        message.addAttribute(ECTServerXmlUtil.MESSAGE_ID, UUIDUtils.getUUIDString(16));
        Element CSRes = message.addElement(ECTServerXmlUtil.CSRES);
        CSRes.addAttribute(ECTServerXmlUtil.MESSAGE_ID, CPResId);
        for (Map.Entry<String, String> m : map.entrySet()) {
            Element keyElement = CSRes.addElement(m.getKey());
            keyElement.setText(m.getValue() == null ? "" : m.getValue());
        }
        return doc2String(document);
    }

    /**
     * Function : 将一个dom的document对象转成一个string
     *
     * @param document
     * @return
     */
    public static String doc2String(Document document) {
        String s = "";
        try {
            //使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //使用UTF-8编码
            OutputFormat format = new OutputFormat("  ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("解析报文异常");
        }
        return s;
    }

    /**
     * Function : 使用BeanUtils的方法将object转成map后，
     *
     * @param obj
     * @param CPResType
     * @return
     */
    public static String objectToXml_XXX(Object obj, String CPResType) {
        Map<String, Object> map = BeanUtils.beanToMap(obj);
        return mapToXml_XXX(map, CPResType);
    }

    public static String mapToXml_XXX(Map<String, Object> map, String CPResId) {
        if (map == null)
            return "";
        //生成xml的document对象
        Document document = DocumentHelper.createDocument();
        Element ectData = document.addElement(ECTServerXmlUtil.ECTDATA);
        Element message = ectData.addElement(ECTServerXmlUtil.MESSAGE);
        message.addAttribute(ECTServerXmlUtil.MESSAGE_ID, UUIDUtils.getUUIDString(16));
        Element CSRes = message.addElement(ECTServerXmlUtil.CSRES);
        CSRes.addAttribute(ECTServerXmlUtil.MESSAGE_ID, CPResId);
        for (Map.Entry<String, Object> m : map.entrySet()) {
            if (m.getValue() instanceof String) {
                Element keyElement = CSRes.addElement(m.getKey());
                keyElement.setText(m.getValue() == null ? "" : (String) m.getValue());
            }
            if (m.getValue() instanceof List) {
                Element keyElement = CSRes.addElement(m.getKey());
                for (Object obj : (List) m.getValue()) {
                    Element info = keyElement.addElement(StringUtils.uncapitalize(obj.getClass().getSimpleName()));
                    Map<String, Object> infoMap = BeanUtils.beanToMap(obj);
                    for (Map.Entry<String, Object> infoEntry : infoMap.entrySet()) {
                        Element infoKey = info.addElement(infoEntry.getKey());
                        infoKey.setText(infoEntry.getValue() == null ? "" : String.valueOf(infoEntry.getValue()));
                    }
                }
            }
        }

        return doc2String(document);
    }

    /**
     * 组装返回xml
     *
     * @param map
     * @param CSResId
     * @return
     */
    public static String mapToResponseXml(Map<String, String> map, String CSResId) {

        if (map == null)
            return "";
        Document document = DocumentHelper.createDocument();
        Element ectData = document.addElement(ECTXmlUtil.ECTDATA);
        Element message = ectData.addElement(ECTXmlUtil.MESSAGE);

        message.addAttribute(ECTXmlUtil.MESSAGE_ID, UUIDUtils.getUUIDString(16));
        Element csReq = message.addElement(ECTXmlUtil.CSRES);
        csReq.addAttribute(ECTXmlUtil.MESSAGE_ID, CSResId);

        for (Map.Entry<String, String> m : map.entrySet()) {
            Element keyElement = csReq.addElement(m.getKey());
            keyElement.setText(m.getValue() == null ? "" : m.getValue());
        }

        return doc2String(document);

    }


}
