package com.devcourse.kurlymurly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class KurlyMurlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(KurlyMurlyApplication.class, args);
    }
}
