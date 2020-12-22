package org.arch.project.rule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ImportResource({"classpath:context.xml"})
public class Bootstrap {


    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void publishEvent() {

    }
}
