package com.pokewith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.pokewith.user.repository", "com.pokewith.raid.repository"})
@SpringBootApplication
public class PokewithApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokewithApplication.class, args);
    }

}
