package com.pokewith.swagger;

import com.pokewith.auth.TokenValue;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String title = "Pokewith API";
    private static final String version = "1.5";
    private static final String description = """
            Pokewith API
            """;


    @Bean
    public GroupedOpenApi publicApi() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch(paths)
                .build();
    }


    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name(TokenValue.accessToken);

        SecurityRequirement securityItem = new SecurityRequirement().addList(TokenValue.accessToken);

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(TokenValue.accessToken, securityScheme))
                .addSecurityItem(securityItem)
                .info(new Info().title("").version("1.0").description(description));
    }
}
