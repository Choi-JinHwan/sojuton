package lol.tpost.sojuton.application.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val corsProperties: CorsProperties,
) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").apply {
            corsProperties.allowedOrigins?.toTypedArray()?.let {
                allowedOrigins(*it)
            }
        }
            .allowedMethods(HttpMethod.OPTIONS.name(), HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.PUT.name())
    }
}
