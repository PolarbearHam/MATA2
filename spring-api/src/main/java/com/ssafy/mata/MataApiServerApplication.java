package com.ssafy.mata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class MataApiServerApplication {
    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "{/resources}");
        SpringApplication.run(MataApiServerApplication.class, args);
    }
}
