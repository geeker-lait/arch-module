package org.arch.payment.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
        System.out.println("http://127.0.0.1:8080");
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void publishEvent() {

        //FulfilTicket fulfilTicket = new FulfilTicket();
        //FulfilRequest.of().getOrderParams()
        //OmsOrder omsOrder = new OmsOrder();
        // 履约
        //fulfilService.fulfil(fulfilTicket);
    }
}
