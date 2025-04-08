package site.leona.teamreboot.service

import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import site.leona.teamreboot.common.entity.enums.ErrorCode
import site.leona.teamreboot.config.exception.BusinessException
import site.leona.teamreboot.entity.CreditTransaction
import site.leona.teamreboot.entity.CreditUsage
import site.leona.teamreboot.entity.enums.CreditUsageStatus
import site.leona.teamreboot.entity.enums.Type
import site.leona.teamreboot.model.CustomerDto
import site.leona.teamreboot.repository.*
import java.time.LocalDateTime

@Transactional
@Service
class CustomerServiceImpl(
    private val creditUsageRepository: CreditUsageRepository,
    private val creditTransactionRepository: CreditTransactionRepository,
    private val customerRepository: CustomerRepository,
    private val planRepository: PlanRepository,
//    private val planFeatureRepository: PlanFeatureRepository
    private val planFeatureRepositorySupport: PlanFeatureRepositorySupport,
): CustomerService {
    override fun assignPlan(customerId: Long, param: CustomerDto.AssignPlanParam): CustomerDto.AssignPlanResponse {
        // 1. 가져온 고객 ID 로 실제 존재하는 고객인지 확인
        val customer =  customerRepository.findById(customerId).orElseThrow { BusinessException(ErrorCode.CUSTOMER_NOT_FOUND) }

        // 2. 가져온 Plan ID 로 실제 존재하는 요금제인지 확인
        val plan = planRepository.findById(param.planId).orElseThrow { BusinessException(ErrorCode.FEATURE_NOT_FOUND) }

        // 3. 둘다 확인된 경우 고객 정보에 요금제 정보를 업데이트 처리
        param.doChangeCustomer(customer, plan)
        customerRepository.save(customer)

        // 4. 리턴 객체 생성후 완료 처리
        return CustomerDto.AssignPlanResponse(customer.customerId)
    }

    override fun useFeature(customerId: Long, param: CustomerDto.UseFeatureParam): CustomerDto.UseFeatureResponse {
        // 1. 가져온 고객 ID 로 실제 존재하는 고객인지 확인
        val customer =  customerRepository.findById(customerId).orElseThrow { BusinessException(ErrorCode.CUSTOMER_NOT_FOUND) }

        // 2. 고객이 실제로 요금제를 등록한 고객인지 판별
        customer.plan ?: throw BusinessException(ErrorCode.FEATURE_NOT_FOUND)

        // 3. 등록된 Plan 에 사용하고자하는 Feature 가 포함되어있는지 확인
        val planFeature = planFeatureRepositorySupport.getCustomerPlanFeature(customer.customerId, param.featureId) ?: throw BusinessException(ErrorCode.FEATURE_NOT_FOUND)

        val feature = planFeature.feature ?: throw BusinessException(ErrorCode.FEATURE_NOT_FOUND)

        // 4. Feature 와 PlanFeature 를 확인해서 사용량이 남아있는지, 그리고 사용한 사용량보다 작은지 판별
        val unitUsed = param.unitUsed
        if (unitUsed > planFeature.customLimit) {
            throw BusinessException(ErrorCode.PLAN_LIMIT_EXCEEDED)
        }

        val creditPerUnit = feature.creditPerUse
        val totalCreditToUse = feature.unit.calculateCredit(unitUsed, creditPerUnit)

        if (totalCreditToUse > customer.creditBalance) {
            throw BusinessException(ErrorCode.INSUFFICIENT_CREDIT)
        }

        // 5. 고객의 크레딧 차감
        param.doChangeCustomer(customer, customer.creditBalance - totalCreditToUse)
        customerRepository.save(customer)

        // 6. 크레딧 사용 이력 저장
        val usageCredit = CreditUsage.doCreate(customer, feature, LocalDateTime.now(), totalCreditToUse, CreditUsageStatus.USAGE);
        creditUsageRepository.save(usageCredit)

        // 7. 트랜잭션 저장
        val creditTransaction = CreditTransaction.doCreate(customer, totalCreditToUse, Type.USAGE, usageCredit)
        creditTransactionRepository.save(creditTransaction)


        // 8. 리턴 객채 생성후 완료 처리
        return CustomerDto.UseFeatureResponse(customerId, totalCreditToUse, 0L);
    }
}