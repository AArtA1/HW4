package com.example.hw4_3.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan("com.example.hw4_3")
@EntityScan("com.example.hw4_3")
@EnableJpaRepositories("com.example.hw4_3")
@SpringBootApplication
public class Hw43Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw43Application.class, args);
    }
}
