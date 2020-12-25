package com.uni.skit.user;

import com.uni.skit.user.config.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackages = {"com.uni"})
@EntityScan(basePackages = {"com.uni.*.entity"})
@EnableTransactionManagement
@EnableConfigurationProperties({SecurityProperties.class})
public class SkitUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkitUserApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
