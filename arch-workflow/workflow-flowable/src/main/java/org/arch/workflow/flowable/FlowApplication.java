package org.arch.workflow.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 流程引擎服务类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月8日
 */
@SpringBootApplication(scanBasePackages = "org.arch.workflow")
public class FlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowApplication.class, args);
    }

}