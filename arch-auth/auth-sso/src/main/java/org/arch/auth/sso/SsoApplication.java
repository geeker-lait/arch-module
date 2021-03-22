package org.arch.auth.sso;

import org.arch.framework.beans.utils.IpUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 单独登录 APP
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 14:09
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.arch.admin", "org.arch.auth.sso"})
@EnableAsync
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = {"org.arch.ums.feign"})
public class SsoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SsoApplication.class)
                .properties("REAL-MAC:" + IpUtils.getMACAddress())
                .run(args);
    }
}
