package site.leona.teamreboot.config.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import site.leona.teamreboot.common.model.GlobalResponse
import site.leona.teamreboot.common.model.RequestContext
import site.leona.teamreboot.config.exception.BusinessException
import java.time.Instant

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = ["com.site.leona.teamreboot.api"])
class FailHandler {

    @ExceptionHandler(BusinessException::class)
    fun businessFailure(exception:BusinessException, request: HttpServletRequest): ResponseEntity<GlobalResponse<Nothing>> {
        val start: Instant = RequestContext.getStartTime()
        val errorMsg: String = exception.message.toString()

        val response = GlobalResponse.error<Nothing>(
            message = "비즈니스 오류발생: $errorMsg",
            path = request.requestURL.toString(),
            start = start
        )

        return ResponseEntity.status(exception.getStatus()).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgValid(exception: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<GlobalResponse<Nothing>> {
        val start: Instant = RequestContext.getStartTime()
        val errorMsg: String = exception
            .bindingResult
            .fieldErrors
            .joinToString(" ,")
            {"${it.field}: ${it.defaultMessage}"}

        val response = GlobalResponse.error<Nothing>(
            message = "정상적인 요청이 아닙니다: $errorMsg",
            path = request.requestURL.toString(),
            start = start
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handlerMethodValid(exception: HandlerMethodValidationException, request: HttpServletRequest): ResponseEntity<GlobalResponse<Nothing>> {
        val start: Instant = RequestContext.getStartTime()
        val errorMsg: String = exception
            .parameterValidationResults
            .flatMap { res -> res.resolvableErrors.map {
                err -> val param = res.methodParameter.parameterName ?: "Need Check"
                "$param: ${err.defaultMessage}"
            }}
            .joinToString(" ,")

        val response = GlobalResponse.error<Nothing>(
            message = errorMsg,
            path = request.requestURL.toString(),
            start = start
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolation(exception: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<GlobalResponse<Nothing>> {
        val start: Instant = RequestContext.getStartTime()
        val errorMsg: String = exception
            .constraintViolations
            .map {err -> err.propertyPath + ": "  + err.message}
            .joinToString(" ,")

        val response = GlobalResponse.error<Nothing>(
            message = errorMsg,
            path = request.requestURL.toString(),
            start = start
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(MissingPathVariableException::class)
    fun missingPathVariable(exception: MissingPathVariableException, request: HttpServletRequest): ResponseEntity<GlobalResponse<Nothing>> {
        val start: Instant = RequestContext.getStartTime()

        val errorMsg = exception.variableName + " 가 없습니다."

        val response = GlobalResponse.error<Nothing>(
            message = errorMsg,
            path = request.requestURL.toString(),
            start = start
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(MissingRequestValueException::class)
    fun missingParameter(exception: MissingRequestValueException, request: HttpServletRequest): ResponseEntity<GlobalResponse<Nothing>> {
        val start: Instant = RequestContext.getStartTime()

        val errorMsg = when(exception) {
            is MissingServletRequestParameterException -> "${exception.parameterName} 가 없습니다."
            is MissingRequestHeaderException -> "${exception.headerName} 가 없습니다."
            else -> "필수값이 없습니다."
        }

        val response = GlobalResponse.error<Nothing>(
            message = errorMsg,
            path = request.requestURL.toString(),
            start = start
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }
}