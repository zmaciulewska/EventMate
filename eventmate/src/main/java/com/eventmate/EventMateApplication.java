package com.eventmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventMateApplication.class, args);
    }

}
