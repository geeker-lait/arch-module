package org.arch.workflow.ums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 人员服务启动类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年3月27日
 */
@SpringBootApplication(scanBasePackages = "org.arch.workflow")
public class IdentityApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentityApplication.class, args);
    }
}
