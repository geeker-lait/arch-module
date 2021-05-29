//package com.unichain.pay.huifu.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.google.gson.Gson;
//import com.huifu.saturn.cfca.CFCASignature;
//import com.huifu.saturn.cfca.SignResult;
//import com.huifu.saturn.cfca.VerifyResult;
//import com.unichain.pay.huifu.properties.HuifuProperties;
//import com.unichain.pay.huifu.dto.HuifuPayConfigDto;
//import org.apache.commons.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.nio.charset.Charset;
//
//public class HuifuSignatureUtils {
//    private final static Logger logger = LoggerFactory.getLogger(HuifuSignatureUtils.class);
//    /**
//     * npay 加签
//     *
//     * @param params
//     * @return
//     */
//    public static String sign(String params, HuifuProperties huifuConfig) {
//        logger.info("待加签内容："+params);
//        // 进行base64转换
//        String base64RequestParams = Base64.encodeBase64String(params.getBytes(Charset.forName("utf-8")));
//        // 加签
//        SignResult signResult = HuifuCFCASignature.signature(huifuConfig.getMerchantNo(), base64RequestParams, "utf-8");
//        if ("000".equals(signResult.getCode())) {
//            return signResult.getSign();
//        } else {
//            return "加签失败";
//        }
//    }
//
//    /**
//     * npay 验签
//     *
//     * @param responseJson
//     * @return
//     */
//    public static String parseCVResult(String responseJson, HuifuProperties huifuConfig) {
//        // 将json格式字符串转换为json对象
//        JSONObject jsonObject = JSON.parseObject(responseJson);
//        // 取得返回数据密文
//        String sign = jsonObject.getString("check_value");
//        // 进行验签，参数1为汇付商户号，固定为100001
//        VerifyResult verifyResult = HuifuCFCASignature.verifyMerSign(huifuConfig.getNpayMerId(), sign,
//                "utf-8",huifuConfig.getMerchantNo());
//        Gson gson = new Gson();
//        logger.info("验签 verifyResult:"+   gson.toJson(verifyResult));
//        if ("000".equals(verifyResult.getCode())) {
//
//            // 取得base64格式内容
//            String content = new String(verifyResult.getContent(),
//                    Charset.forName("utf-8"));
//            // base64格式解码
//            String decrptyContent = new String(Base64.decodeBase64(content),
//                    Charset.forName("utf-8"));
//
//            logger.info("解签结果："+decrptyContent);
//            return decrptyContent;
//        } else {
//            logger.info("解签失败");
//            return "验签失败";
//        }
//    }
//
//    /**
//     * @author dai_dlc@163.com
//     * @create 2019.4.17
//     * 重写加签
//     */
//    public static String getSign(String valueObj, HuifuPayConfigDto properties) {
//        logger.info("待加签内容是：{}", valueObj);
//        // 加签用 pfx 文件，请换成商户自己导出的证书
//        String pfxFile = properties.getPfxPath();
//        // 加签用密码，导出 pfx 证书时的密码
//        String pfxFilePwd = properties.getPfxPwd();
//        // 加签（先使用base64对参数进行加密）
//        SignResult signResult = CFCASignature.signature(pfxFile, pfxFilePwd, Base64.encodeBase64String(valueObj.getBytes(Charset.forName("utf-8"))), "utf-8");
//        logger.info("汇付加签,加签完成后返回的数据为：{}", signResult);
//        // 加签后的结果返回
//        if ("000".equals(signResult.getCode())) {
//            return signResult.getSign();
//        } else {
//            logger.error("汇付加签出现错误");
//            return "加签失败";
//        }
//    }
//
//    /**
//     * @author dai_dlc@163.com
//     * @create 2019.4.17
//     * 重写验签方法
//     */
//    public static String parseSign(String responseString, HuifuPayConfigDto properties) {
//        logger.info("汇付验签，待验证参数为：{}", responseString);
//        JSONObject jsonObject = JSON.parseObject(responseString);
//        String sign = jsonObject.getString("check_value");
//        // 解签用的证书，请换成商户自己下载的证书
//        String cerFile = properties.getCerPath();
//        VerifyResult verifyResult = CFCASignature.verifyMerSign("100001", sign, "UTF-8", cerFile);
//
//
//        if ("000".equals(verifyResult.getCode())) {
//            String content = new String(verifyResult.getContent(), Charset.forName("UTF-8"));
//            // 返回相应后结果时，需要先对解签的结果进行base64解密
//            String decrptyContent = new String(Base64.decodeBase64(content), Charset.forName("utf-8"));
//            logger.info("汇付解签后的结果为decrptyContent：{}", decrptyContent);
//            return decrptyContent;
//        } else {
//            logger.error("汇付解签出现问题");
//            return "验签失败";
//        }
//    }
//}
