//package com.unichain.pay.channel.mfe88.start;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//@Controller
//@SpringBootApplication
//@ComponentScan(basePackages = { "com.unichain.pay.channel.mfe88"})
//@MapperScan(basePackages = {"demo.dao","mybatisGenerator.dao"})
//public class StartApplication extends SpringBootServletInitializer {
//	@Autowired
//	private Environment env;
//	public SpringApplicationBuilder configure(SpringApplicationBuilder aplication) {
//		return aplication.sources(StartApplication.class);
//	}
//	public static void main(String[] args) {
//		SpringApplication.run(StartApplication.class, args);
//	}
//	@RequestMapping("/")
//	public String test(ModelMap map) throws Exception{
//		map.put("wwwUrl", env.getProperty("wwwUrl"));
//		map.put("apiUrl", env.getProperty("apiUrl"));
//		if (env.getProperty("apiUrl").contains("uppApi"))
//			return "uppmonica";
//		else if(env.getProperty("apiUrl").equals("http://139.129.206.79:29606/PayApi/")){
//			return "ali";
//		}else
//			return "saasmonica";
//	}
//}
