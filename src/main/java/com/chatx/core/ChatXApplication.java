package com.chatx.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@PropertySource("classpath:core.properties")
@EntityScan("com.chatx.core.entity")
public class ChatXApplication {
    private static final Logger logger = LoggerFactory.getLogger(ChatXApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ChatXApplication.class, args);
        logger.info("core.properties file loaded successfully");
    }
}