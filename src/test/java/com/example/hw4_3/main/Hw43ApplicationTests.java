package com.example.hw4_3.main;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.example.hw4_3")
@EntityScan("com.example.hw4_3")
@SpringBootTest
class Hw43ApplicationTests {

    @Test
    void contextLoads() {
    }

}
