package com.techparts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.techparts")
public class TechPartsSolutionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechPartsSolutionsApplication.class, args);
    }
}
