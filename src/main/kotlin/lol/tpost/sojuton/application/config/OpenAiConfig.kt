package lol.tpost.sojuton.application.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(OpenAiProperties::class)
class OpenAiConfig

@ConfigurationProperties(prefix = "open-ai")
data class OpenAiProperties(
    val key: String,
)
