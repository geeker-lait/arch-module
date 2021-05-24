package org.arch.framework.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.utils.JsonUtils;
import org.arch.framework.beans.utils.StringUtils;
import org.arch.framework.spi.Componentable;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Slf4j
public class ComponentConfigure implements ComponentConfig {
    public static String DEFAULT_ENDPOINT_URL = "http://mc.arch.com";
    private static String redisKeyPrefix = null;
    private static final Properties applicationConfig = yaml2Properties("bootstrap.yml");
    private volatile MiddlewareConfig middlewareConfig = null;
    private final Object lockObject = new Object();

    private static RestTemplate restTemplate = new RestTemplateBuilder(new RestTemplateCustomizer[0])
            .requestFactory(SimpleClientHttpRequestFactory.class)
            .errorHandler(new ResponseErrorHandler() {
                public boolean hasError(ClientHttpResponse response) {
                    return false;
                }

                public void handleError(ClientHttpResponse response) {
                }
            }).build();

    static final TypeReference<Response<String>> typeReference = new TypeReference<Response<String>>() {
    };

    static final TypeReference<Response<MiddlewareConfig>> RESPONSE_TYPE_REFERENCE = new TypeReference<Response<MiddlewareConfig>>() {
    };


    public MiddlewareConfig getConfig(String appId, String secret) {
        if (StringUtils.isEmpty(this.getApplicationName())) {
            throw new ComponentInitException("name cannot be empty");
        } else {
            if (applicationConfig.get("spring.arch.redis.key-prefix") == null) {
                redisKeyPrefix = this.getApplicationName();
            } else {
                redisKeyPrefix = applicationConfig.get("spring.arch.redis.key-prefix").toString();
            }

            if (null == this.middlewareConfig) {
                synchronized (this.lockObject) {
                    if (null == this.middlewareConfig) {
                        String configUrl = getConfigUrl(appId, secret);
                        this.middlewareConfig = getSdkConfig(configUrl);
                        if (null != ComponentHolder.get()) {
                            try {
                                List<Componentable> midComponentList = ComponentHolder.get();
                                String version = null;
                                StringJoiner nameJoiner = new StringJoiner(",");

                                Componentable midComponent;
                                for (Iterator var8 = midComponentList.iterator(); var8.hasNext(); nameJoiner.add(midComponent.getComponentName())) {
                                    midComponent = (Componentable) var8.next();
                                    if (StringUtils.isEmpty(version)) {
                                        version = midComponent.getComponentVersion();
                                    }
                                }

                                String collectUrl = String.format("%s/collect/component", getEndpoint(), this.getApplicationName(), nameJoiner.toString(), version);
                                Map<String, Object> jsonMap = new HashMap();
                                jsonMap.put("applicationName", this.getApplicationName());
                                jsonMap.put("componentName", nameJoiner.toString());
                                jsonMap.put("componentVersion", version);
                                jsonMap.put("componentType", "SDK");
                                String reqJson = (new ObjectMapper()).writeValueAsString(jsonMap);
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.APPLICATION_JSON);
                                HttpEntity<String> request = new HttpEntity(reqJson, headers);
                                ResponseEntity<String> responseEntity = restTemplate.postForEntity(collectUrl, request, String.class, new Object[0]);
                                if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                                    log.warn("sdk信息上报失败");
                                }
                            } catch (Exception var15) {
                                log.error("sdk信息上报异常,", var15);
                            }
                        }
                    }
                }
            }
            return this.middlewareConfig;
        }
    }

    public static String getEndpoint() {
        String arch_mid_endpoint = System.getProperty("ARCH_MID_ENDPOINT");
        if (arch_mid_endpoint != null && !"".equals(arch_mid_endpoint)) {
            return arch_mid_endpoint;
        } else {
            String env = System.getenv("ARCH_MID_ENDPOINT");
            return env != null && !"".equals(env) ? env : DEFAULT_ENDPOINT_URL;
        }
    }

    public String getAppId() {
        return applicationConfig.get("middleware.appId").toString();
    }

    public String getSecret() {
        return applicationConfig.get("middleware.secret").toString();
    }

    public String getApplicationName() {
        return applicationConfig.get("middleware.name").toString();
    }

    public String getRedisKeyPrefix() {
        return redisKeyPrefix;
    }

    public static Properties yaml2Properties(String properties) {
        try {
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            propertiesFactoryBean.setLocation(new ClassPathResource(properties));
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        } catch (IOException var2) {
            throw new ComponentInitException(var2.getMessage(), var2);
        }
    }

    private static MiddlewareConfig getSdkConfig(String configUrl) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(configUrl, String.class, new Object[0]);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new ComponentInitException(JsonUtils.toJson(responseEntity.getBody()));
        } else {
            Response<MiddlewareConfig> listResponse = (Response) JsonUtils.fromJson((String) responseEntity.getBody(), RESPONSE_TYPE_REFERENCE);
            MiddlewareConfig middlewareConfig = (MiddlewareConfig) listResponse.getData();
            return middlewareConfig;
        }
    }

    private static String getConfigUrl(String appId, String secret) {
        log.info("Using endpoint {}", getEndpoint());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(String.format("%s/app/auth?appId=%s&secret=%s", getEndpoint(), appId, secret), String.class, new Object[0]);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new ComponentInitException(JsonUtils.toJson(responseEntity.getBody()));
        } else {
            Response<String> stringResponse = JsonUtils.fromJson(responseEntity.getBody(), typeReference);
            return stringResponse.getData();
        }
    }

    @Data
    @Builder
    public static class ApplicationSdkConfigVo {
        private String applicationName;
        private List<SdkConfigVo> sdkConfigVos;

        @Data
        @Builder
        public static class ConfigKvVo {
            private String key;
            private String value;
        }

        @Data
        @Builder
        public static class ConfigGroupVo {
            private String suffix;
            private List<ComponentConfigure.ApplicationSdkConfigVo.ConfigKvVo> configKvVos;
        }

        @Data
        @Builder
        public static class SdkConfigVo {
            private String sdkType;
            private List<ComponentConfigure.ApplicationSdkConfigVo.ConfigGroupVo> configGroupVos;
        }
    }
}
