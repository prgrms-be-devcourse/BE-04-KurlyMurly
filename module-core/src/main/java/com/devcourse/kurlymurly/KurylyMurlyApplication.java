package com.devcourse.kurlymurly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KurylyMurlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(KurylyMurlyApplication.class, args);
    }
}
