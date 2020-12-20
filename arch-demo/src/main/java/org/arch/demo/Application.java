package org.arch.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * <p>
 * Application
 * </p>
 *
 * @author lait
 * @since 2020-11-13 10:30:40
 * @description 
 **/

@SpringBootApplication
@MapperScan(basePackages = {"org.arch.demo.crud.mapper"})
@EnableOAuth2Sso
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
