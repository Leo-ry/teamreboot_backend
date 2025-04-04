package site.leona.teamreboot.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import site.leona.teamreboot.config.interceptor.LoggingInterceptor

@Configuration
class WebConfig(private val loggingInterceptor: LoggingInterceptor): WebMvcConfigurer {

    // 공통 로깅 모듈 추가 -> 로그 및 시간 측정용
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loggingInterceptor)
    }
}