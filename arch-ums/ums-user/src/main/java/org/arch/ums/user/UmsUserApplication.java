package org.arch.ums.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 * ums user 模块
 * </p>
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class UmsUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UmsUserApplication.class, args);
    }
}