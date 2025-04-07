package site.leona.teamreboot.config.handler

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import site.leona.teamreboot.common.model.GlobalResponse
import site.leona.teamreboot.common.model.RequestContext
import java.time.Instant

@RestControllerAdvice(basePackages = ["com.site.leona.teamreboot.api"])
class DefaultHandler : ResponseBodyAdvice<Any> {

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        val start: Instant = RequestContext.getStartTime()

        // val path: String = (request as? ServletServerHttpRequest)?.servletRequest?.requestURI ?: "unknown"
        // NOTICE -> 실 환경에서는 앞에서 리버스프록시 돌리고 CloudFlare 같은 방어도구를 갖출 수 있으므로 대비
        val path: String = request.uri.path

        if (body is GlobalResponse<*>) {
            return body
        }

        return GlobalResponse.success(body, path, start)
    }
}