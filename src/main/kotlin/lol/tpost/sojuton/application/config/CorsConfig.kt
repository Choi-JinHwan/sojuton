package lol.tpost.sojuton.application.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(CorsProperties::class)
class CorsConfig


@ConfigurationProperties(prefix = "cors")
data class CorsProperties(
    val allowedOrigins: List<String>?
)
