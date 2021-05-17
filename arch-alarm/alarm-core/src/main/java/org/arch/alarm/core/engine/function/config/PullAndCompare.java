package org.arch.alarm.core.engine.function.config;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @author lait.zhang@gmail.com
 * @description: 拉取并比较 pull and compare
 * @weixin PN15855012581
 * @date 5/14/2021 5:36 PM
 */
@Slf4j
public class PullAndCompare extends AbstractFunction {

    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);

        // 添加转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        log.info("SimpleRestClient初始化完成");
    }

    /**
     * 读取配置中心
     * "OFSCC('http://baidu.com','shopId=${shopId}','name=delivery&value=1|2|3')";
     *
     * @param env
     * @param arg1 api
     * @param arg2 参数
     * @param arg3 满足条件对
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        String api = FunctionUtils.getStringValue(arg1, env);
        String sparam = FunctionUtils.getStringValue(arg2, env);
        String cpair = FunctionUtils.getStringValue(arg3, env);
        Map<String, String> dynamicParams = new HashMap<>();
        // 这里可以自定读取配置，动态增加
        Arrays.asList("shopId", "storeNo", "orderId", "orderNo").forEach(k -> {
            Object v = env.get(k);
            // 增加仓店id
            if (v != null && StringUtils.isNotBlank(v.toString())) {
                dynamicParams.put(k, v.toString());
            }
        });
        if (api != null) {
            // 统一替换${}占位符
            StrSubstitutor strSubstitutor = new StrSubstitutor(dynamicParams);
            sparam = strSubstitutor.replace(sparam);
            Map<String, String> pmap = convertMap(sparam);
            try {
                List<Map> result = restTemplate.postForObject(api, pmap, List.class);
                if (result != null && result.size() > 0) {
                    // 全部转为map
                    Map<String, String> cmap = convertMap(cpair);
                    Set<String> kset = convertMap(cpair).keySet();
                    int i = 0;
                    //cmap.get(k)
                    for (Map map : result) {
                        /*ShopConfigInfoDTO shopConfigInfoDTO = new ShopConfigInfoDTO();
                        BeanUtils.populate(shopConfigInfoDTO, map);
                        if (shopConfigInfoDTO.getName() == null || shopConfigInfoDTO.getValue() == null) {
                            continue;
                        }
                        for (String k : kset) {
                            Set<String> sets = Arrays.stream(cmap.get(k).split("\\|")).collect(Collectors.toSet());
                            if (k.equalsIgnoreCase(shopConfigInfoDTO.getName()) && sets.contains(shopConfigInfoDTO.getValue())) {
                                i++;
                            }
                        }*/
                    }
                    if (cmap.size() == i) {
                        return AviatorBoolean.valueOf(true);
                    }
                }
            } catch (Exception e) {
                log.error("配置中心函数计算错误：{}", e.getMessage());
                return AviatorBoolean.valueOf(false);
            }

        }
        return AviatorBoolean.valueOf(false);
    }

    @Override
    public String getName() {
        return "PAC";
    }

    private Map<String, String> convertMap(String sparam) {
        Map<String, String> mparam = new HashMap<>();
        if (StringUtils.isNotBlank(sparam)) {
            String[] params = sparam.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] p = params[i].split("=");
                if (p.length == 2) {
                    mparam.put(p[0], p[1]);
                }
            }
        }
        return mparam;
    }
}
