package br.com.oneguy.testr2dbc.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
//@io.swagger.v3.oas.annotations.security.SecurityScheme(
//    name = "bearerAuth",
//    type = SecuritySchemeType.HTTP,
//    bearerFormat = "JWT",
//    scheme = "bearer"
//)
class SpringDocConfiguration {
    @Autowired
    val buildInfo: BuildProperties? = null

    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .packagesToScan("br.com.oneguy.testr2dbc.controller")
            .group("testr2dbc")
            .build()
    }

    @Bean
    fun springOpenAPI(): OpenAPI? {
        return OpenAPI()
            .components(
                Components().addSecuritySchemes(
                    "bearer-key",
                    SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                )
            )
            .info(
                Info().title("R2DBC API")
                    .version(buildInfo?.version ?: run { "V" })
            )
    }
}
