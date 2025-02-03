package com.chatx.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@PropertySource("classpath:core.properties")
@EntityScan("com.chatx.core.entity")
@EnableJpaRepositories("com.chatx.core.repository")
public class ChatXApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatXApplication.class, args);
    }
}