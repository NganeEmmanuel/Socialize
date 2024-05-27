package com.socialize;

import com.socialize.security.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
=======
//import org.springframework.context.annotation.Profile;
>>>>>>> b20a258293afb45843b64759d9f0e6bdd5a4b8b1

@SpringBootApplication
//@Profile("local") todo use this to configure profile to use
public class SocializeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocializeApplication.class, args);
    }

}
