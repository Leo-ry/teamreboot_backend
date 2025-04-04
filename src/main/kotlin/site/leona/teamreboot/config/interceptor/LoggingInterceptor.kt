package site.leona.teamreboot.config.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import site.leona.teamreboot.common.model.RequestContext
import site.leona.teamreboot.config.LogFactory
import java.lang.Exception
import java.time.Instant

@Component
class LoggingInterceptor: HandlerInterceptor, LogFactory {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val instant = Instant.now()
        RequestContext.setStartTime(instant)

        request.setAttribute("startTime", instant.epochSecond)
        log.info("[Request Start] Method : {}  ||  URI : {}  ||  Call Time : {}", request.method, request.requestURL, instant.epochSecond)

        return true;
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        RequestContext.clear()
        log.info("[Response Start] Method : {}  ||  URI : {}", request.method, request.requestURL)
    }
}