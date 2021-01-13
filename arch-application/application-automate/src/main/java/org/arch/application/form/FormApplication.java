package org.arch.application.form;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"org.arch.application.form.mapper"})
public class FormApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormApplication.class, args);
	}

}
