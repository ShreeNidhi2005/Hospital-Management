
package com.example.loginapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                    .allowedOrigins(
                        "https://hospital-management-seven-pied.vercel.app"
                    )
                    .allowedMethods(
                        "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
                    )
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
