package com.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.netty.*"})
public class NettyChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyChatApplication.class, args);
    }

}
