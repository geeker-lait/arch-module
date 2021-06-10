package org.arch.auth.sso;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 单独登录 APP
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 14:09
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.arch.admin", "org.arch.auth.sso", "org.arch.ums"},
excludeFilters = {
       @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.arch.ums.*.rest.*Rest")
})
@EnableAsync
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = {"org.arch.ums.*.api"})
public class SsoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SsoApplication.class)
                .run(args);
    }
}
