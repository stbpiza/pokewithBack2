package com.pokewith.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi publicApi() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch(paths)
                .build();
    }

}
