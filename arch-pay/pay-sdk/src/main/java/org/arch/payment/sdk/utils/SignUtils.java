package org.arch.payment.sdk.utils;

import org.apache.http.message.BasicNameValuePair;
import org.arch.framework.beans.utils.StringUtils;
import org.arch.payment.sdk.SignType;
import org.arch.payment.sdk.exception.PayErrorException;
import org.arch.payment.sdk.exception.PayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 签名 工具
 *
 * @author egan
 * <pre>
 * email egzosn@gmail.com
 * date 2016/11/9 17:45
 * </pre>
 */
public enum SignUtils implements SignType {

    MD5 {
        /**
         *
         * @param content           需要签名的内容
         * @param key               密钥
         * @param characterEncoding 字符编码
         * @return 签名值
         */
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            return MD5.sign(content, key, characterEncoding);
        }

        /**
         * 签名字符串
         * @param text 需要签名的字符串
         * @param sign 签名结果
         * @param key 密钥
         * @param characterEncoding 编码格式
         * @return 签名结果
         */
        @Override
        public boolean verify(String text, String sign, String key, String characterEncoding) {
            return MD5.verify(text, sign, key, characterEncoding);
        }
    },
    HMACSHA256 {
        @Override
        public String getName() {
            return "HMAC-SHA256";
        }

        /**
         * 签名
         *
         * @param content           需要签名的内容
         * @param key               密钥
         * @param characterEncoding 字符编码
         *
         * @return 签名值
         */
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            Mac sha256HMAC = null;
            try {
                sha256HMAC = Mac.getInstance("HmacSHA256");
                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(characterEncoding), "HmacSHA256");
                sha256HMAC.init(secretKey);
                byte[] array = sha256HMAC.doFinal(content.getBytes(characterEncoding));
                StringBuilder sb = new StringBuilder();
                for (byte item : array) {
                    sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
                }
                return sb.toString().toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                LOG.error("", e);
            } catch (InvalidKeyException e) {
                LOG.error("", e);
            } catch (UnsupportedEncodingException e) {
                LOG.error("", e);
            }

            throw new PayErrorException(new PayException("fail", "HMACSHA256 签名异常"));
        }

        /**
         * 签名字符串
         *
         * @param text              需要签名的字符串
         * @param sign              签名结果
         * @param key               密钥
         * @param characterEncoding 编码格式
         *
         * @return 签名结果
         */
        @Override
        public boolean verify(String text, String sign, String key, String characterEncoding) {
            return createSign(text, key, characterEncoding).equals(sign);
        }
    },

    RSA {
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            return RSA.sign(content, key, characterEncoding);
        }

        @Override
        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return RSA.verify(text, sign, publicKey, characterEncoding);
        }
    },

    RSA2 {
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            return RSA2.sign(content, key, characterEncoding);
        }

        @Override
        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return RSA2.verify(text, sign, publicKey, characterEncoding);
        }
    },
    SHA1 {
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            return SHA1.sign(content, key, characterEncoding);
        }

        @Override
        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return SHA1.verify(text, sign, publicKey, characterEncoding);
        }
    },
    SHA256 {
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            return SHA256.sign(content, key, characterEncoding);
        }

        @Override
        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return SHA256.verify(text, sign, publicKey, characterEncoding);
        }
    },
    SM3 {
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            return RSA2.sign(content, key, characterEncoding);
        }

        @Override
        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return RSA2.verify(text, sign, publicKey, characterEncoding);
        }
    };
    private static final Logger LOG = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“@param separator”字符拼接成字符串
     *
     * @param parameters 参数
     * @return 去掉空值与签名参数后的新签名，拼接后字符串
     */
    public static String parameterText(Map parameters) {
        return parameterText(parameters, "&");

    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“@param separator”字符拼接成字符串
     *
     * @param parameters 参数
     * @param separator  分隔符
     * @return 去掉空值与签名参数后的新签名，拼接后字符串
     */
    public static String parameterText(Map parameters, String separator) {
        return parameterText(parameters, separator, "signature", "sign", "key", "sign_type");
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“@param separator”字符拼接成字符串
     *
     * @param parameters 参数
     * @param separator  分隔符
     * @param ignoreKey  需要忽略添加的key
     * @return 去掉空值与签名参数后的新签名，拼接后字符串
     */
    public static String parameterText(Map<String, Object> parameters, String separator, String... ignoreKey) {
        return parameterText(parameters, separator, true, ignoreKey);
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“@param separator”字符拼接成字符串
     *
     * @param parameters      参数
     * @param separator       分隔符
     * @param ignoreNullValue 需要忽略NULL值
     * @param ignoreKey       需要忽略添加的key
     * @return 去掉空值与签名参数后的新签名，拼接后字符串
     */
    public static String parameterText(Map<String, Object> parameters, String separator, boolean ignoreNullValue, String... ignoreKey) {
        if (parameters == null) {
            return "";
        }

        if (null != ignoreKey) {
            Arrays.sort(ignoreKey);
        }
        StringBuffer sb = new StringBuffer();
        // TODO 2016/11/11 10:14 author: egan 已经排序好处理
        if (parameters instanceof SortedMap) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                Object v = entry.getValue();
                if (null == v) {
                    continue;
                }
                String valStr = v.toString().trim();
                if ("".equals(valStr) || (null != ignoreKey && Arrays.binarySearch(ignoreKey, entry.getKey()) >= 0)) {
                    continue;
                }
                sb.append(entry.getKey()).append("=").append(valStr).append(separator);
            }
            if (sb.length() > 0 && !"".equals(separator)) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();

        }

        return sortMapParameterText(parameters, separator, ignoreNullValue, ignoreKey);

    }


    private static String sortMapParameterText(Map<String, Object> parameters, String separator, boolean ignoreNullValue, String... ignoreKey) {
        StringBuffer sb = new StringBuffer();
        // TODO 2016/11/11 10:14 author: egan 未排序须处理
        List<String> keys = new ArrayList<String>(parameters.keySet());
        //排序
        Collections.sort(keys);
        for (String k : keys) {
            String valueStr = "";
            Object o = parameters.get(k);
            if (ignoreNullValue && null == o) {
                continue;
            }
            if (o instanceof String[]) {
                String[] values = (String[]) o;

                for (int i = 0; i < values.length; i++) {
                    String value = values[i].trim();
                    if ("".equals(value)) {
                        continue;
                    }
                    valueStr += (i == values.length - 1) ? value : value + ",";
                }
            } else {
                valueStr = o.toString();
            }
            if (StringUtils.isBlank(valueStr) || (null != ignoreKey && Arrays.binarySearch(ignoreKey, k) >= 0)) {
                continue;
            }
            sb.append(k).append("=").append(valueStr).append(separator);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 将参数集合(事前做好排序)按分割符号拼凑字符串并加密为MD5
     * example: mchnt_cd+"|"  +order_id+"|"+order_amt+"|"+order_pay_type+"|"+page_notify_url+"|"+back_notify_url+"|"+order_valid_time+"|"+iss_ins_cd+"|"+goods_name+"|"+"+goods_display_url+"|"+rem+"|"+ver+"|"+mchnt_key
     *
     * @param parameters 参数集合
     * @param separator  分隔符
     * @return 参数排序好的值
     */
    public static String parameters2Md5Str(Object parameters, String separator) {
        StringBuffer sb = new StringBuffer();

        if (parameters instanceof LinkedHashMap) {
            Set<String> keys = (Set<String>) ((LinkedHashMap) parameters).keySet();
            for (String key : keys) {
                String val = ((LinkedHashMap) parameters).get(key).toString();
                sb.append(val).append(separator);

            }
        } else if (parameters instanceof List) {
            for (BasicNameValuePair bnv : ((List<BasicNameValuePair>) parameters)) {
                sb.append(bnv.getValue()).append(separator);
            }
        }

        return StringUtils.isBlank(sb.toString()) ? "" : sb.deleteCharAt(sb.length() - 1).toString();
    }


    /**
     * 获取随机字符串
     *
     * @return 随机字符串
     */
    public static String randomStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String getName() {
        return this.name();
    }

    /**
     * 签名
     *
     * @param parameters        需要进行排序签名的参数
     * @param key               密钥
     * @param characterEncoding 编码格式
     * @return 签名值
     */
    public String sign(Map parameters, String key, String characterEncoding) {

        return createSign(parameterText(parameters, "&"), key, characterEncoding);
    }

    /**
     * 签名
     *
     * @param parameters        需要进行排序签名的参数
     * @param key               密钥
     * @param separator         分隔符  默认 &amp;
     * @param characterEncoding 编码格式
     * @return 签名值
     */
    public String sign(Map parameters, String key, String separator, String characterEncoding) {

        return createSign(parameterText(parameters, separator), key, characterEncoding);

    }


    /**
     * 签名字符串
     *
     * @param params            需要签名的字符串
     * @param sign              签名结果
     * @param key               密钥
     * @param characterEncoding 编码格式
     * @return 签名结果
     */
    public boolean verify(Map params, String sign, String key, String characterEncoding) {
        //判断是否一样
        return this.verify(parameterText(params), sign, key, characterEncoding);
    }


}
