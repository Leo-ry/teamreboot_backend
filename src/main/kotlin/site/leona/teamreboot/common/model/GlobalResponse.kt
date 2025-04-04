package site.leona.teamreboot.common.model

import java.time.Instant

data class GlobalResponse<T> (
    val isSuccess: Boolean = true,
    val message: String = "",
    val code: String = "",
    val startTime: Long = 0,
    val endTime: Long = 0,
    val path: String = "",
    val data: T? = null
) {
    companion object {

        // 공통 성공 응답시 사용하는 스태틱 메소드
        fun <T> success(data: T?, path: String, start: Instant) : GlobalResponse<T> {
            val end = Instant.now()
            return GlobalResponse(
                isSuccess = true,
                message = "OK",
                code = "200",
                startTime = start.epochSecond,
                endTime = end.epochSecond,
                path = path,
                data = data
            )
        }

        // 공통 실패 응답시 사용하는 스태틱 메소드
        fun <T> error(message: String, path: String, start: Instant) : GlobalResponse<T> {
            val end = Instant.now()
            return GlobalResponse(
                isSuccess = false,
                message = message,
                code = "500",
                startTime = start.epochSecond,
                endTime = end.epochSecond,
                path = path,
                data = null
            )
        }
    }
}