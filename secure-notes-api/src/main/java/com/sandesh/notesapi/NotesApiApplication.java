package com.sandesh.notesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesApiApplication {

    private static final Logger log = LoggerFactory.getLogger(NotesApiApplication.class);

    public static void main(String[] args) {
        log.info("Starting Notes API application");
        SpringApplication.run(NotesApiApplication.class, args);
    }
}