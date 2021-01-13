package org.arch.framework.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * Description: 钉钉工具类
 *
 * @author kenzhao
 */
@EnableAsync
@Component
@Slf4j
public class DingDingUtils {

  public static void main(String[] args)  throws Exception {
    DingDingUtils dingDingUtils = new DingDingUtils();
    dingDingUtils.sendDing("https://oapi.dingtalk.com/robot/send?access_token=8d0bea7fe8a5c31d60a22223e5012ef64db4bd51b7e0b24d60b3decb72399c47","栎尚--\n你的快递已到\n请携带工卡前往\t邮件中心领取");
  }

  @Async
  public void sendDing(String dingUrl,String content) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("msgtype","text");
    JSONObject conObj = new JSONObject();
    conObj.put("content",content);
    jsonObject.put("text", conObj);
    String result = null;
    try {
      result = HttpUtils.doPostJson(dingUrl,jsonObject.toString());
    } catch (Exception e) {
      log.error("钉钉异常发送失败");
    }
  }
}