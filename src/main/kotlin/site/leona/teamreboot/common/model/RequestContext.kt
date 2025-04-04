package site.leona.teamreboot.common.model

import java.time.Instant

/**
 * 요청시작 시간에 대해서 관리하기 위해서 스레드 단위 관리 모듈 생성
 */
class RequestContext(
) {
    companion object {
        private val REQUEST_CONTEXT_THREAD_LOCAL: ThreadLocal<Instant> = ThreadLocal()

        fun setStartTime(time: Instant = Instant.now()) {
            REQUEST_CONTEXT_THREAD_LOCAL.set(time)
        }

        fun getStartTime(): Instant = REQUEST_CONTEXT_THREAD_LOCAL.get() ?: Instant.now()

        fun clear() {
            REQUEST_CONTEXT_THREAD_LOCAL.remove()
        }
    }
}