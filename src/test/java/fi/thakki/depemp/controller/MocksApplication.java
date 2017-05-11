package fi.thakki.depemp.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("fi.thakki.depemp")
public class MocksApplication {

    public static void main(
            String[] args) {
        SpringApplication.run(MocksApplication.class, args);
    }
}
