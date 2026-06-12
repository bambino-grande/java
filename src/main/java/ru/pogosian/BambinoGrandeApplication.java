package ru.pogosian;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BambinoGrandeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BambinoGrandeApplication.class, args);
    }
}