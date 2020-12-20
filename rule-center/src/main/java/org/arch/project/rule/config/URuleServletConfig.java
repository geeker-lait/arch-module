package org.arch.project.rule.config;

import com.bstek.urule.console.servlet.URuleServlet;
import org.arch.project.rule.Bootstrap;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URuleServletConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Bootstrap.class);
    }

    @Bean
    public URuleServlet createDispatcherServlet() {
        return new URuleServlet();
    }

    @Bean
    public ServletRegistrationBean createDispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(createDispatcherServlet(), "/urule/*");
        return registration;
    }

}