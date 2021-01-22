package com.unichain.pay.yeepay.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yeepay.g3.sdk.yop.client.YopClient3;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class YeepayService {

    // 商编前面加SQKK
    static String appKey = "SQKK10000469946";
    // 商户私钥，测试用这个，到时候正式更换成自己的生成的私钥
    static String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCrS0KstpuJs75jrO2Tc7x5WfeQ8ER1tPr34OPiJfETinvTQeNzZc13V1Wm+yhk5Xsv4OWuSHCok5MwhEgc4cb03loTcS4pqAHL4u13hEycf+fTkoIQ88IpmFMcnu0l3dYqzj34fEgH7EN4byCDDd9cNtZWuzA9iitwJuoCjx4sJqxvftxPQV6gbPZEwIWPw3i1ALG8LgKDRiB/i8hw4PfRCGrYtwjOjwKsiEetxNUpROs8nUSpRBQnr8G1Z85sN6wH83eH3y2uJ4O4G/Eao3FG8rHVWPIiQaXtyFqFx8B3pFAwqYuxWaciInlNPTLMd1Ee+BDqQ9FN/tzy/q5CSTevAgMBAAECggEBAJiAYbT4wpMfNrLxI3ayhKsZgQJGFv0gioujad9OXkpCcamMsJ5tlTbZx0TpuHXTpQ/kTzgSAFLlSBbavoUQMZySVWmXyzyE+kx2FWrhm399lHzVo/zJuCRmHCCQEZwz21ey1JNkupBrNUqEzVJASIqFu9/tua4gVDn+OzraBkfREWV9E6CmQ0xl7SNz9awWpg/8zTqLMsXSR3Q6+ezd5vHZTZvkG5sLh2cKgylZoZawJgrGpW5mt+nXlPhvXmW6XcX730DWNvTgIOtduDZ0Ig5JEya8V9XC+1OcHAkpXXMG2ohABZtIKKS4yLWSwgCFErgVyien5T2kdEIGPbnE55ECgYEA88r2DHhssH9fSIzHo6yL3VMit30Qs6fjxmQXcqxewYtk11yPpn2ROaGBYitzsv/kgVLECRsfIREGsUjPqz7Al8NpgqoQEf27zWaf6x59cs3g2vB6rtilvng8C3dnSFjKZQ/tbXv/QHDz/A549rwm4fO689ptjegS3zcAaptF7okCgYEAs976k3NCpln7FzGKtMrMk5my/ed8Dj70cntxdwNBcsOhbbh1HbCsn2F/PLdqd6bJc+Qdi7hIGecUZ/6/CuEDY/8jZqm9b6B4vnxZFBTUyqHcL8cXmMUDi9p7SoSsCZ92RCD0x4SbOjhym7yuvw14gk+hlBSvWuC+YhEMRlinJncCgYBJ+12Pizvwk7amnZI36TTIhWITrLBU1K4almVHN2fJ9DM157DwJUrc4lYRJH6H43/EfwleegyITFJrmlzq6rAnXfW24UTfMNC9FFeTUj1fiXqi9jdEuBoUIwiVsjZ1jfxdjufOQcLEG4LvCrVKqu5hw0UIm1CDr9mKQ3as41HlgQKBgCjBD+NSzTolzxdtOTFHddzHiV+wEFKl/vrlb0r46N5Y5v2WOqr0edhO3eZi5HOhzak9eVhL88IyslPxy1VqsDr69wlu0iY1pMX8JK7BHYmf7OTCZl1N3kTUxvSWZOh1QfWjxfJi4Ezrt0QEF0/gfHqCEmkb2rNrkpdjp3VU5uJ3AoGBANhQ1Jl3X3UOVXQm0ZXQ7OkPkWE5XZIOb+WUcB7JTXKmpAArqI2FOe3Ki+8rMWDNgDUu+/nvSSWzrAsG8+jR9Hp+fSQ6poagwpWcitPjFYQip6RUBx6Cv6mBCMXDGnxOQIi8GJCITT86SsF1YbirDmi/Yd58xPEBbQdjysRWhr8e";

    public static Map<String, String> yeepayYOP(Map<String, String> map, String Uri) throws IOException {

        // 传三参的方式方法
        YopRequest yoprequest = new YopRequest(appKey, private_key);

        Map<String, String> result = new HashMap<String, String>();

        Set<Entry<String, String>> entry = map.entrySet();
        for (Entry<String, String> s : entry) {
            yoprequest.addParam(s.getKey(), s.getValue());
        }
        System.out.println("yoprequest:" + yoprequest.getParams());

        // 向YOP发请求
        YopResponse yopresponse = YopClient3.postRsa(Uri, yoprequest);
        System.out.println("请求YOP之后结果：" + yopresponse.toString());
        System.out.println("请求YOP之后结果：" + yopresponse.getStringResult());

//        	对结果进行处理
        if ("FAILURE".equals(yopresponse.getState())) {
            if (yopresponse.getError() != null)
                result.put("errorcode", yopresponse.getError().getCode());
            result.put("errormsg", yopresponse.getError().getMessage());
            System.out.println("错误明细：" + yopresponse.getError().getSubMessage());
            System.out.println("系统处理异常结果：" + result);
            return result;
        }
        // 成功则进行相关处理
        if (yopresponse.getStringResult() != null) {
            result = parseResponse(yopresponse.getStringResult());

            System.out.println("yopresponse.getStringResult: " + result);

        }

        return result;
    }


    public static Map<String, String> yeepayYOP1(Map<String, Object> map, String Uri, String merchantno, String privateKey) throws IOException {

        // 传三参的方式方法
        YopRequest yoprequest = new YopRequest("SQKK" + merchantno, privateKey);

        Map<String, String> result = new HashMap<String, String>();

        Set<Entry<String, Object>> entry = map.entrySet();
        for (Entry<String, Object> s : entry) {
            yoprequest.addParam(s.getKey(), s.getValue());
        }
        System.out.println("yoprequest:" + yoprequest.getParams());

        // 向YOP发请求
        YopResponse yopresponse = YopClient3.postRsa(Uri, yoprequest);
        System.out.println("请求YOP之后结果：" + yopresponse.toString());
        System.out.println("请求YOP之后结果：" + yopresponse.getStringResult());

//        	对结果进行处理
        if ("FAILURE".equals(yopresponse.getState())) {
            if (yopresponse.getError() != null)
                result.put("errorcode", yopresponse.getError().getCode());
            result.put("errormsg", yopresponse.getError().getMessage());
            result.put("state","FAILURE");
            System.out.println("错误明细：" + yopresponse.getError().getSubMessage());
            System.out.println("系统处理异常结果：" + result);
            return result;
        }
        // 成功则进行相关处理
        if (yopresponse.getStringResult() != null) {
            result = parseResponse(yopresponse.getStringResult());
            result.put("state","SUCCESS");
            System.out.println("yopresponse.getStringResult: " + result);

        }

        return result;
    }


    // 将获取到的yopresponse转换成json格式
    public static Map<String, String> parseResponse(String yopresponse) {

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap = JSON.parseObject(yopresponse, new TypeReference<TreeMap<String, String>>() {
        });
        System.out.println("将结果yopresponse转化为map格式之后: " + jsonMap);
        return jsonMap;
    }

}
