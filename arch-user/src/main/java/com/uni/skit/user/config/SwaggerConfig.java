package com.uni.skit.user.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .securitySchemes(Lists.newArrayList(apiKey())).select()
                .apis(RequestHandlerSelectors.basePackage("com.uni.skit.user"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().version("1.0.0").title("skit API").description(DESC).build();
    }

    private ApiKey apiKey() {
        return new ApiKey("token", "Authorization", "header");
    }

    private static final String DESC = "bama 接口说明\n\n"
            + "统一数据返回格式: \n"
            + "  { \n"
            + "    \"success\": true,   // 是否成功  \n"
            + "    \"code\": 0,   // 状态码  \n"
            + "    \"data\": {...}, // 数据  \n"
            + "    \"message\": \"请求成功\" //描述信息  \n"
            + "  }\n\n"
            + "token： 放在 HTTP header Authorization 中。\n";
}
