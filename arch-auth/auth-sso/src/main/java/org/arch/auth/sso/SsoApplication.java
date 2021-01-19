package org.arch.auth.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 单独登录 APP
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 14:09
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"org.arch.ums.feign"})
public class SsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }
}
