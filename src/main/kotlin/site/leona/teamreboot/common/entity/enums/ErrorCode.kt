package site.leona.teamreboot.common.entity.enums

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus,
) {
    INVALID_INPUT("C001", "입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND("C002", "고객 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FEATURE_NOT_FOUND("F001", "해당 기능이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    PLAN_LIMIT_EXCEEDED("P001", "요금제 제한량을 초과하였습니다.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_CREDIT("C003", "크레딧이 부족합니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("S000", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
}