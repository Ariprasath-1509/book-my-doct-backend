package com.bookmydoct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class BookMyDoctApplication {

    public static void main(String[] args) {
        log.info("Starting Book-My-Doct application...");
        SpringApplication.run(BookMyDoctApplication.class, args);
        log.info("Book-My-Doct application started successfully!");
    }

}
