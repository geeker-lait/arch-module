package org.arch.application.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * <p>
 * Application
 * </p>
 *
 * @author lait
 * @since 2020-11-13 10:30:40
 * @description
 **/

@SpringBootApplication(scanBasePackages = {"org.arch.application.demo"})
@MapperScan(basePackages = {"org.arch.application.demo.crud.mapper"})
//@EnableOAuth2Sso
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
