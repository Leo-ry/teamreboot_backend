package site.leona.teamreboot.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.leona.teamreboot.common.entity.enums.ErrorCode
import site.leona.teamreboot.config.exception.BusinessException
import site.leona.teamreboot.model.StatisticsDto
import site.leona.teamreboot.repository.CreditUsageRespositorySupport
import site.leona.teamreboot.repository.CustomerRepository
import java.time.LocalDate

@Transactional
@Service
class StatisticsServiceImpl(
    private val customerRepository: CustomerRepository,
    private val creditUsageRepositorySupport: CreditUsageRespositorySupport
): StatisticsService {
    override fun getCustomerUsages(
        customerId: Long,
        featureId: Long,
        from: LocalDate,
        to: LocalDate
    ): StatisticsDto.UsageStatResponse {
        // 1. 고객 정보 조회
        val customer = customerRepository.findById(customerId).orElseThrow { throw BusinessException(ErrorCode.CUSTOMER_NOT_FOUND) }

        // 2. 고객이 사용한 모든 이력 조회
        val creditUsage = creditUsageRepositorySupport.getCustomerCreditUsages(customerId, from, to, featureId)

        return StatisticsDto.UsageStatResponse(
            customerId = customer.customerId,
            from = from,
            to = to,
            creditBalance = customer.creditBalance,
            featureUsages = creditUsage
        )
    }
}