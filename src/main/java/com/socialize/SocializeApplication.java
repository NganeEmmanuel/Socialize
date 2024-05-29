package com.socialize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.context.annotation.Profile;

@SpringBootApplication
//@Profile("local") todo use this to configure profile to use
public class SocializeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocializeApplication.class, args);
    }

}
