package site.leona.teamreboot.config.exception

import org.springframework.http.HttpStatus
import site.leona.teamreboot.common.entity.enums.ErrorCode

abstract class BaseException(
    private val errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR,
    private val errorMsg: String = errorCode.message,
) : RuntimeException(errorMsg) {
    fun getStatus(): HttpStatus = HttpStatus.valueOf(errorCode.httpStatus.value())
    fun getCode(): String = errorCode.code
}