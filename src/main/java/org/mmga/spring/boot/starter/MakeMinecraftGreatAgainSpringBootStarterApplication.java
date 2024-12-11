package org.mmga.spring.boot.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"org.mmga.spring.boot.starter.properties"})
public class MakeMinecraftGreatAgainSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakeMinecraftGreatAgainSpringBootStarterApplication.class, args);
    }

}