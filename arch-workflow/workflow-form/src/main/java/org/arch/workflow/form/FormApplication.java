package org.arch.workflow.form;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 表单接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018年8月27日
 */
@SpringBootApplication(scanBasePackages = "org.arch.workflow")
public class FormApplication {

    public static void main(String[] args) {
        SpringApplication.run(FormApplication.class, args);
    }
}
