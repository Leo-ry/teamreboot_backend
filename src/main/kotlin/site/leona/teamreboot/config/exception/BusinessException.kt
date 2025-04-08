package site.leona.teamreboot.config.exception

import site.leona.teamreboot.common.entity.enums.ErrorCode

class BusinessException: BaseException {
    constructor(errorCode: ErrorCode): super(errorCode = errorCode)
    constructor(message: String): super(ErrorCode.INTERNAL_SERVER_ERROR, message)
}