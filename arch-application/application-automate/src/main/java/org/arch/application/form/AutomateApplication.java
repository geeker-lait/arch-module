package org.arch.application.form;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.arch.framework.automate.from"})
@MapperScan(basePackages = {"org.arch.framework.automate.from.mapper"})
public class AutomateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomateApplication.class, args);
	}

}
