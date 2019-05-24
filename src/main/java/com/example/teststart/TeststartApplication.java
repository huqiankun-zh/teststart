package com.example.teststart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;



@SpringBootApplication

@PropertySource("classpath:jdbc.properties")
public class TeststartApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeststartApplication.class, args);
    }

}

