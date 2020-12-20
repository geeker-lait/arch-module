package org.arch.auth.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import java.util.function.Consumer;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("org.arch.auth.*")
public class Oauth2AuthApplication {

    public static void main(String[] args) {
        Consumer<String[]> consumer = (x) -> SpringApplication.run(Oauth2AuthApplication.class, x);
        consumer.accept(args);
    }

}
