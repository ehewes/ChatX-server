package com.chatx.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class ChatXApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatXApplication.class, args);
    }
}