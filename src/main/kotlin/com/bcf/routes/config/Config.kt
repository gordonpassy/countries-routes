package com.bcf.routes.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

val objectMapper = jacksonObjectMapper()

@Configuration
class SwaggerConfig {
    @Bean
    fun openApiInit(): OpenAPI? {
        val description = "BCF Route APIs"
        val version     = "v1.0"
        val title       = "Route APis"
        val name        = "Gordon Okello"
        val email       = "gordon.okello1@gmail.com"

        return OpenAPI()
            .info(
                Info().title(title).version(version)
                    .contact(Contact().email(email).name(name))
                    .description(description)
                    .termsOfService("http://swagger.io/terms/")
                    .license(License().name("Apache 2.0").url("https://springdoc.org"))
            )
            .addServersItem( Server()
                .url("http://localhost:8080")
            )
    }
}