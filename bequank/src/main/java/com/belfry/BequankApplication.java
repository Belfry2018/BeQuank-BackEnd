package com.belfry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.belfry.bequank.repository")

public class BequankApplication {

    public static void main(String[] args) {


        SpringApplication.run(BequankApplication.class, args);
    }
}
