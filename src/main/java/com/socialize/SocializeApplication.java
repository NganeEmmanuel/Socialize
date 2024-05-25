package com.socialize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class SocializeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocializeApplication.class, args);
    }

}
