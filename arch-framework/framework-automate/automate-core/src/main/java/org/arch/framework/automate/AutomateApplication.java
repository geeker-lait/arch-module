package org.arch.framework.automate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/18/2021 3:58 PM
 */
@SpringBootApplication(scanBasePackages = {"org.arch.framework.automate"})
//@MapperScan("org.arch.framework.automate")
public class AutomateApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutomateApplication.class, args);
    }
}
