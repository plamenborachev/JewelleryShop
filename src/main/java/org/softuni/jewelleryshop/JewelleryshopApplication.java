package org.softuni.jewelleryshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JewelleryshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JewelleryshopApplication.class, args);
    }

}
