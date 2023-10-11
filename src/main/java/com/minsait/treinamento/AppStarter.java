package com.minsait.treinamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.yml")
public class AppStarter  {


    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }

}
