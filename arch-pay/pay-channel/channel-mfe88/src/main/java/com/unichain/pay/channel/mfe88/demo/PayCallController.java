//package com.unichain.pay.channel.mfe88.demo;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.InetAddress;
//import java.net.URLDecoder;
//import java.net.UnknownHostException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.unichain.pay.channel.mfe88.demo.entity.PostCode;
//import com.unichain.pay.channel.mfe88.demo.entity.Trade;
//import com.unichain.pay.channel.mfe88.demo.entity.UppOrderLogs;
//import com.unichain.pay.channel.mfe88.mybatisGenerator.dao.UppOrderLogsMapper;
//import com.unichain.pay.channel.mfe88.utils.FileUtil;
//import com.unichain.pay.channel.mfe88.utils.ObjectMapperUtil;
//import com.unichain.pay.channel.mfe88.utils.Tools;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequestMapping(value = "/demo")
//@Service
//@Slf4j
//public class PayCallController {
//    @Autowired
//    UppOrderLogsMapper uppOrderLogsMapper;
//    @Autowired
//    private Environment env;
//
//    @ResponseBody
//    @RequestMapping(value = "/payCall")
//    public String payCall(HttpServletRequest request, HttpSession session) {
//        String content = "<br/><br/>" + getIpAddress(request);
//        content += new Date() + ":收到异步通知：原始内容:  ";
//        boolean flagString = false;
//        try {
//            Map<String, Object> requestMap = ObjectMapperUtil.readValue(request, Map.class);
//            System.out.print("接收到参数：" + requestMap);
//            if (requestMap == null || requestMap.isEmpty())
//                content += "异步通知返回数据为空";
//            else {
//                String keyType = env.getProperty("keyType");
//                Trade trade = new Trade();
//                trade.setSignType(keyType);
//                if (keyType.equals("1"))
//                    trade.setMd5Key(env.getProperty("md5Key"));
//                if (keyType.equals("2")) {
//                    trade.setPublicKey(env.getProperty("rsaPublicKey"));
//                    trade.setPrivateKey(env.getProperty("rsaPrivateKey"));
//                }
//                if (keyType.equals("3")) {
//                    trade.setBankPublicKey(env.getProperty("RSACertCer"));
//                }
//                String sb = Demo.createLinkString(requestMap);
//                content += requestMap + "<br>异步通知验证签名：签名串:" + sb;
//                flagString = Demo.checkSign(trade, sb, requestMap.get("sign") + "");
//                content += flagString == true ? " 验证签名成功" : "  验证签名失败";
//
//            }
//        } catch (Exception e) {
//            content = e.toString();
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            String basePath = session.getServletContext().getRealPath("/"); // 获取基本路径
//            FileUtil.write(basePath + "WEB-INF/payCall.txt", content);
//            return flagString == true ? Tools.getGsonDisableHtml().toJson("SUCCESS") : Tools.getGsonDisableHtml().toJson("FAILED");
//        }
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/upppayCall")
//    public String upppayCall(HttpServletRequest request, HttpSession session) {
//        String content = "<br/><br/>" + getIpAddress(request);
//        content += new Date() + ":收到异步通知：原始内容:  ";
//        boolean flagString = false;
//        try {
//            Map<String, Object> requestMap = ObjectMapperUtil.readValue(request, Map.class);
//            System.out.print("接收到参数：" + requestMap);
//            if (requestMap == null || requestMap.isEmpty())
//                content += "异步通知返回数据为空";
//            else {
//                String keyType = env.getProperty("keyType");
//                Trade trade = new Trade();
//                trade.setKeyType(keyType);
//                if (keyType.equals("1"))
//                    trade.setMd5Key(env.getProperty("md5Key"));
//                if (keyType.equals("2")) {
//                    trade.setPublicKey(env.getProperty("m_rsaPublicKey"));
//                    trade.setPrivateKey(env.getProperty("m_rsaPrivateKey"));
//                }
//                if (keyType.equals("3")) {
//                    trade.setBankPublicKey(env.getProperty("RSACertCer"));
//                }
//                content += requestMap + "<br>异步通知验证签名：";
//                String singStr = Demo.createLinkString(UppDemo.paraFilter(requestMap)); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
//                flagString = UppDemo.verifySign(URLDecoder.decode(requestMap.get("sign") + "", "utf-8"), trade.getPublicKey(), singStr);
//                content += "  签名串:" + singStr + (flagString == true ? "验证签名成功" : "验证签名失败");
//            }
//        } catch (NumberFormatException e) {
//            content = e.toString();
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            content = e.toString();
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String basePath = session.getServletContext().getRealPath("/"); // 获取基本路径
//        FileUtil.write(basePath + "WEB-INF/upppayCall.txt", content);
//        return flagString == true ? Tools.getGsonDisableHtml().toJson("SUCCESS") : Tools.getGsonDisableHtml().toJson("FAILED");
//    }
//
//    public static String getIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/checkPayCall")
//    public String checkPayCall(ModelMap map, String mOrderNo, HttpSession session) {
//        String basePath = session.getServletContext().getRealPath("/"); // 获取基本路径
//        String content = "";
//        try {
//            if (mOrderNo.isEmpty())
//                content = FileUtil.readToList(basePath + "WEB-INF/payCall.txt");
//            else
//                content = FileUtil.readForString(basePath + "WEB-INF/payCall.txt", mOrderNo.replace(" ", ""));
//        } catch (FileNotFoundException e) {
//            content = e.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            content = e.toString();
//        }
//        return content;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/uppcheckPayCall")
//    public String uppcheckPayCall(ModelMap map, String mOrderNo, HttpSession session) {
//        String basePath = session.getServletContext().getRealPath("/"); // 获取基本路径
//        String content = "";
//        try {
//            if (mOrderNo.isEmpty())
//                content = FileUtil.readToList(basePath + "WEB-INF/upppayCall.txt");
//            else
//                content = FileUtil.readForString(basePath + "WEB-INF/upppayCall.txt", mOrderNo.replace(" ", ""));
//        } catch (FileNotFoundException e) {
//            content = e.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            content = e.toString();
//        }
//        return content;
//    }
//
//    @RequestMapping("/toPage")
//    public String toPage(ModelMap map, String pageName, String url, Long needSubmit, Long merge) throws Exception {
//        String apiUrl = env.getProperty("apiUrl");
//        getCommonData(apiUrl,map,pageName,url,needSubmit);
//        //three environment
//        String toPage;
//        if (apiUrl.contains("uppApi")) {
//            //upp
//            getUppParameter(map, pageName, url);
//            toPage = "demo/upp/" + pageName;
//        } else if (apiUrl.equals("http://139.129.206.79:29606/PayApi/")) {
//            //ali
//            toPage = "demo/ali/" + pageName;
//        } else {
//            //互联网
//            toPage = "demo/saas/" + pageName;
//        }
//
//        return toPage;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/getLogs")
//    public String getLogs(String mOrderNo, String cxOrderNo) {
//        String outStr = "";
//        UppOrderLogs bean = new UppOrderLogs();
//        long DAY_IN_MS = 1000 * 60 * 60 * 24;
////		bean.setCreateTime(new Date(System.currentTimeMillis() - (2 * DAY_IN_MS)));//four days
//        SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = sdfr.format(new Date(System.currentTimeMillis() - (1 * DAY_IN_MS)));
//        bean.setStartCreateTime(dateString);
//        List<UppOrderLogs> list = new ArrayList<UppOrderLogs>();
//        if (!Tools.isEmpty(mOrderNo) && !Tools.isEmpty(cxOrderNo)) {
//            bean.setOrderNo(mOrderNo.replace(" ", ""));
//            list = uppOrderLogsMapper.findAll(bean);
//            bean.setOrderNo(cxOrderNo.replace(" ", ""));
//            List<UppOrderLogs> list2 = new ArrayList<UppOrderLogs>();
//            list2 = uppOrderLogsMapper.findAll(bean);
//            if (null != list && list.size() > 0) {
//                list.addAll(list2);
//            }
//            outStr += Tools.getGsonDisableHtml().toJson(list);
//        } else if (Tools.isEmpty(mOrderNo) && !Tools.isEmpty(cxOrderNo)) {
//            bean.setOrderNo(cxOrderNo.replace(" ", ""));
//            list = uppOrderLogsMapper.findAll(bean);
//            if (null != list && list.size() > 0) {
//                outStr += Tools.getGsonDisableHtml().toJson(list);
//            }
//        } else if (!Tools.isEmpty(mOrderNo) && Tools.isEmpty(cxOrderNo)) {
//            bean.setOrderNo(mOrderNo.replace(" ", ""));
//            list = uppOrderLogsMapper.findAll(bean);
//            if (null != list && list.size() > 0) {
//                outStr += Tools.getGsonDisableHtml().toJson(list);
//            }
//        } else {
//            return Tools.getGsonDisableHtml().toJson("");
//        }
//        return outStr.isEmpty() ? Tools.getGsonDisableHtml().toJson("") : outStr;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "download")
//    public void download(String fileurl, HttpSession session, HttpServletResponse response) throws IOException {
//        String basePath = session.getServletContext().getRealPath("/"); // 获取基本路径
//        if (null != fileurl && !fileurl.equals("")) {
//            /* 第一步:根据文件路径获取文件 */
//            File file = new File(basePath + fileurl);
//            if (file.exists()) { // 文件存在
//                /* 第二步：根据已存在的文件，创建文件输入流 */
//                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//                /* 第三步：创建缓冲区，大小为流的最大字符数 */
//                byte[] buffer = new byte[inputStream.available()]; // int
//                /* 第四步：从文件输入流读字节流到缓冲区 */
//                inputStream.read(buffer);
//                /* 第五步： 关闭输入流 */
//                inputStream.close();
//                String fileName = file.getName();// 获取文件名
//                response.reset();
//                response.addHeader("Content-Disposition",
//                        "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
//                response.addHeader("Content-Length", "" + file.length());
//                /* 第六步：创建文件输出流 */
//                OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//                response.setContentType("application/octet-stream");
//                /* 第七步：把缓冲区的内容写入文件输出流 */
//                outputStream.write(buffer);
//                /* 第八步：刷空输出流，并输出所有被缓存的字节 */
//                outputStream.flush();
//                /* 第九步：关闭输出流 */
//                outputStream.close();
//            } // end if (file.exists())
//        } else {
//            return;
//        }
//    }
//    public void getCommonData(String apiUrl,ModelMap map,String pageName,String url,Long needSubmit) throws UnknownHostException {
//        String postUrl;
//        postUrl = getPageName(apiUrl, pageName, url);
//        map.put("postUrl", postUrl);
//        map.put("url", url);
//        map.put("needSubmit", "" + needSubmit);
//        if (needSubmit == null) {
//            map.put("orderTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));// 订单时间);
//            map.put("orderNo", Demo.nextGeneratorOrderNo());
//            map.put("payerIp", InetAddress.getLocalHost().getHostAddress());
//        }
//        if (pageName.equals("log"))
//            map.put("logUrl", env.getProperty("logUrl"));
//        map.put("rsaPrivateKey", env.getProperty("rsaPrivateKey"));
//        map.put("rsaPublicKey", env.getProperty("rsaPublicKey"));
//        map.put("merchantNo", env.getProperty("merchantNo"));
//        map.put("md5Key", env.getProperty("md5Key"));
//        map.put("RSACertPfx", env.getProperty("RSACertPfx"));
//        map.put("RSACertCer", env.getProperty("RSACertCer"));
//        map.put("certKeyPassword", env.getProperty("certKeyPassword"));
//        map.put("apiUrl", env.getProperty("apiUrl"));
//        map.put("wwwUrl", env.getProperty("wwwUrl"));
//        map.put("pageUrl", env.getProperty("pageUrl"));
//        map.put("bgUrl", env.getProperty("bgUrl"));
//        map.put("aesKey", env.getProperty("aesKey"));
//        map.put("openid", env.getProperty("openid"));
//        map.put("bankCardNo", env.getProperty("bankCardNo"));
//        map.put("idCode", env.getProperty("idCode"));
//        map.put("phone", env.getProperty("phone"));
//        map.put("userName", env.getProperty("userName"));
//        map.put("cvv2", env.getProperty("cvv2"));
//        map.put("validPeriod", env.getProperty("validPeriod"));
//        map.put("keyType", env.getProperty("keyType"));
//        //wechat
//        map.put("appId", env.getProperty("appId"));
//        map.put("timeStamp", env.getProperty("timeStamp"));
//        map.put("nonceStr", env.getProperty("nonceStr"));
//        map.put("package1", env.getProperty("package"));
//        map.put("signType", env.getProperty("signType"));
//        map.put("paySign", env.getProperty("paySign"));
//    }
//    public void getUppParameter(ModelMap map, String pageName, String url) {
//        if (pageName.equals("uppcontent") && url.equals("orderListSearch")) {
//            long DAY_IN_MS = 1000 * 60 * 60 * 24;
//            SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
//            String dateString = sdfr.format(new Date(System.currentTimeMillis() + (1 * DAY_IN_MS)));
//            map.put("order_time_start", sdfr.format(new Date(System.currentTimeMillis())) + " 00:00:00");
//            map.put("order_time_end", dateString + " 00:00:00");
//        }
//        map.put("orgNo", env.getProperty("orgNo"));
//        map.put("userId", env.getProperty("userId"));
//        map.put("payPassWord", env.getProperty("payPassWord"));
//        map.put("merchantOrgNo", env.getProperty("merchantOrgNo"));
//        map.put("payUserId", env.getProperty("payUserId"));
//        map.put("m_rsaPrivateKey", env.getProperty("m_rsaPrivateKey"));
//        map.put("m_rsaPublicKey", env.getProperty("m_rsaPublicKey"));
//        map.put("m_md5Key", env.getProperty("m_md5Key"));
//        map.put("m_aesKey", env.getProperty("m_aesKey"));
//    }
//
//    public String getPageName(String apiUrl, String pageName, String url) {
//        String postUrl;
//        String name;
//        if (apiUrl.contains("uppApi")) {
//            name = "upp_" + pageName;
//        } else {
//            name = "cashier_" + pageName;
//
//        }
//        if (pageName.equals("content")) {
//            name += "_" + url;
//        }
//        postUrl = PostCode.getPostUrl(apiUrl,name);
//        return postUrl;
//    }
//    //	@ResponseBody
////	@RequestMapping(value = "/changeProperty")
////	public String changeProperty(String property) {
////		if (property != null) {
////			try {
//////				PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
//////				props.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
//////				props.setLocations(new PathMatchingResourcePatternResolver()
//////						.getResources("classpath:/application-" + property + ".properties"));
//////				SpringApplication.exit(new ApplicationContext(StartApplication.class),0);
////				ConfigurableApplicationContext ctx = SpringApplication.run(StartApplication.class);
////				ctx.close();
////				SpringApplication  app = new SpringApplication(StartApplication.class);
////				app.setAdditionalProfiles(property);
////				app.run();
////				return "修改成功";
////			} catch (Exception e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////				return e.toString();
////			}
////		} else
////			return "property不能为空";
////	}
//}
