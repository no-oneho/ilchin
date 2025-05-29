package org.groupware.ilchin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class IlchinApplication {

    public static void main(String[] args) {
        SpringApplication.run(IlchinApplication.class, args);
    }

}
