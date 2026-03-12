package com.example.restservice.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT")
public class OpenApiConfig {
  // @Bean
  // public OpenAPI openAPI() {
  //
  // String schemeName = "bearerAuth";
  //
  // return new OpenAPI()
  // .addSecurityItem(new SecurityRequirement().addList(schemeName))
  // .components(new Components()
  // .addSecuritySchemes(
  // schemeName,
  // new io.swagger.v3.oas.models.security.SecurityScheme()
  // .name(schemeName)
  // .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
  // .scheme("bearer")
  // .bearerFormat("JWT")));
  // }
}
