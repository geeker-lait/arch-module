//package com.unichain.pay.sharelink.log;
//
//import ch.qos.logback.classic.pattern.MessageConverter;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class LogMessageConverter extends MessageConverter {
//
//    /**
//     * 日志脱敏开关
//     */
//    private static String converterCanRun = "true";
//    /**
//     * 整理出的日志脱敏关键字
//     */
//    private static String[] keysArray = new String[]{
//            "name", "account", "idType", "idCode", "mobile",  //实体类
//            "checkAcctNo", "checkAcctNm", "certSeq", "checkPhone",        //鉴权
//            "payerAcctNo", "payerNm",    //"certSeq","checkPhone", 	//消费
////    	"payerAcctNo","payerNm","certSeq","checkPhone",		//撤销
////    	"payerAcctNo","payerNm",	//退款
////    	"payerAcctNo","payerNm","certSeq","checkPhone",	//单笔代收
//            "payeeAcctNo", "payeeNm", "payeeCertSeq",        //单笔代付
////    	"checkAcctNo","checkAcctNm","certSeq","checkPhone",	//快捷支付鉴权
////    	"checkAcctNo","checkAcctNm","certSeq","checkPhone",	//快捷支付签约
////    	"payerAcctNo","payerNm","certSeq","checkPhone",		//快捷支付协议支付
////    	"payerAcctNo","payerNm","certSeq","checkPhone"		//快捷支付退款
//            "cstNo", "realName", "payAcc", "certNo", "regPhone", "REALNAME", "PAYACC", "CERTNO", "REGPHONE",    //原风控接口
//            "userName", "registerMobile", "creditNo", "customerMobile", "bankCardNo",        //快捷支付签约实体类
//            "USERNAME", "REGISTERMOBILE", "CREDITNO", "CUSTOMERMOBILE", "BANKCARDNO",        //快捷支付签约风控
//            "payAccount", "otherName", "PAYACCOUNT", "OTHERNAME",    //快捷支付协议支付风控
//            "returnAccountNo", "RETURNACCOUNTNO",    //快捷支付退款风控
//            "signAccount", "signAccountName", "signIdCode", "signMobile",    //鉴权流水信息
//            "acctNo", "paymentAccount", "bindAccount", "accountNo", "payerName", "payeeName", "payeeAct", "payeracctno",
//            "payeracctnm", "payeeacctno", "payeeacctnm"
//    };
//    private static Pattern pattern = Pattern.compile("[0-9a-zA-Z]");
//
//    /**
//     * 自测方法
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        String tempMsg = "{sign=f88898b2677e62f1ad54b9e330c0a27e, idCode=130333198901192762, realName=李哪娜, key=c5d34d4c3c71cc45c88f32b4f13da887, mobile=13210141605, account=6226430106137525}";
//        String tempMsg1 = "{\"reason\":\"成功 \",\"result\":{\"jobid\":\"JH2131171027170837443588J6\",\"realName\":\"李哪娜\",\"account\":\"6226430106137525\",\"idCode\":\"130333198901192762\",\"mobile\":\"13210141605\",\"res\":\"1\",\"message\":\"验证成功\"},\"error_code\":0}";
//        LogMessageConverter sc = new LogMessageConverter();
//        System.out.println(sc.invokeMsg(tempMsg));
//        System.out.println(sc.invokeMsg(tempMsg1));
//    }
//
//    @Override
//    public String convert(ILoggingEvent event) {
//        // 获取原始日志
//        String oriLogMsg = event.getFormattedMessage();
//        // 获取脱敏后的日志
//        String afterLogMsg = invokeMsg(oriLogMsg);
//        return afterLogMsg;
//    }
//
//    /**
//     * 处理日志字符串，返回脱敏后的字符串
//     *
//     * @param msg
//     * @return
//     */
//    public String invokeMsg(final String oriMsg) {
//        String tempMsg = oriMsg;
//        if ("true".equals(converterCanRun)) {
//            // 处理字符串
//            if (keysArray != null && keysArray.length > 0) {
//                for (String key : keysArray) {
//                    int index = -1;
//                    do {
//                        index = tempMsg.indexOf(key, index + 1);
//                        if (index != -1) {
//                            // 判断key是否为单词字符
//                            if (isWordChar(tempMsg, key, index)) {
//                                continue;
//                            }
//                            // 寻找值的开始位置
//                            int valueStart = getValueStartIndex(tempMsg, index + key.length());
//
//                            // 查找值的结束位置（逗号，分号）........................
//                            int valueEnd = getValuEndEIndex(tempMsg, valueStart);
//
//                            // 对获取的值进行脱敏
//                            String subStr = tempMsg.substring(valueStart, valueEnd);
//                            subStr = tuomin(subStr, key);
//                            ///////////////////////////
//                            tempMsg = tempMsg.substring(0, valueStart) + subStr + tempMsg.substring(valueEnd);
//                        }
//                    } while (index != -1);
//                }
//            }
//        }
//        return tempMsg;
//    }
//
//    /**
//     * 判断从字符串msg获取的key值是否为单词 ， index为key在msg中的索引值
//     *
//     * @return
//     */
//    private boolean isWordChar(String msg, String key, int index) {
//
//        // 必须确定key是一个单词............................
//        if (index != 0) { // 判断key前面一个字符
//            char preCh = msg.charAt(index - 1);
//            Matcher match = pattern.matcher(preCh + "");
//            if (match.matches()) {
//                return true;
//            }
//        }
//        // 判断key后面一个字符
//        char nextCh = msg.charAt(index + key.length());
//        Matcher match = pattern.matcher(nextCh + "");
//        return match.matches();
//    }
//
//    /**
//     * 获取value值的开始位置
//     *
//     * @param msg        要查找的字符串
//     * @param valueStart 查找的开始位置
//     * @return
//     */
//    private int getValueStartIndex(String msg, int valueStart) {
//        // 寻找值的开始位置.................................
//        do {
//            char ch = msg.charAt(valueStart);
//            if (ch == ':' || ch == '=' || ch == '>' || ch == '：') { // key与 value的分隔符
//                valueStart++;
//                ch = msg.charAt(valueStart);
//                if (ch == '"') {
//                    valueStart++;
//                }
//                break;    // 找到值的开始位置
//            } else {
//                valueStart++;
//            }
//        } while (true);
//        return valueStart;
//    }
//
//    /**
//     * 获取value值的结束位置
//     *
//     * @return
//     */
//    private int getValuEndEIndex(String msg, int valueEnd) {
//        do {
//            if (valueEnd == msg.length()) {
//                break;
//            }
//            char ch = msg.charAt(valueEnd);
//
//            if (ch == '"') { // 引号时，判断下一个值是结束，分号还是逗号决定是否为值的结束
//                if (valueEnd + 1 == msg.length()) {
//                    break;
//                }
//                char nextCh = msg.charAt(valueEnd + 1);
//                if (nextCh == ';' || nextCh == ',') {
//                    // 去掉前面的 \  处理这种形式的数据
//                    while (valueEnd > 0) {
//                        char preCh = msg.charAt(valueEnd - 1);
//                        if (preCh != '\\') {
//                            break;
//                        }
//                        valueEnd--;
//                    }
//                    break;
//                } else {
//                    valueEnd++;
//                }
//            } else if (ch == ';' || ch == ',' || ch == '}' || ch == '<' || ch == '&' || ch == '，' || ch == '；') {
//                break;
//            } else {
//                valueEnd++;
//            }
//
//        } while (true);
//        return valueEnd;
//    }
//
//    private String tuomin(String submsg, String key) {
//        /**
//         * 证件号码
//         */
//        List<String> idCodeList = Arrays.asList("idCode", "certSeq", "payeeCertSeq",
//                "cstNo", "certNo", "CERTNO", "creditNo", "CREDITNO", "CSTNO", "signIdCode");
//        /**
//         * 手机号码
//         */
//        List<String> phoneList = Arrays.asList("mobile", "checkPhone", "signMobile",
//                "regPhone", "REGPHONE", "registerMobile", "customerMobile", "REGISTERMOBILE", "CUSTOMERMOBILE");
//        /**
//         * 银行卡号
//         */
//        List<String> accountList = Arrays.asList("account", "checkAcctNo", "payerAcctNo", "payeeAcctNo",
//                "payAcc", "PAYACC", "bankCardNo", "BANKCARDNO", "payAccount", "PAYACCOUNT", "returnAccountNo", "RETURNACCOUNTNO",
//                "signAccount", "acctNo", "paymentAccount", "bindAccount", "accountNo", "payeeAct", "payeracctno", "payeeacctno");
//        /**
//         * 姓名
//         */
//        List<String> nameList = Arrays.asList("name", "checkAcctNm", "payerNm", "payeeNm",
//                "realName", "REALNAME", "userName", "USERNAME", "otherName", "OTHERNAME", "signAccountName",
//                "payerName", "payeeName", "payeracctnm", "payeeacctnm");
//
//        submsg = submsg.trim();
//        // idcard：身份证号, realname：姓名, bankcard：银行卡号, mobile：手机号
//        if (idCodeList.contains(key)) {
//            return LogUtil.idCardNum(submsg);
//        }
//        if (nameList.contains(key)) {
//            return LogUtil.chineseName(submsg);
//        }
//        if (accountList.contains(key)) {
//            return LogUtil.bankCard(submsg);
//        }
//        if (phoneList.contains(key)) {
//            return LogUtil.mobilePhone(submsg);
//        }
//        return submsg;
//    }
//
//
//}
