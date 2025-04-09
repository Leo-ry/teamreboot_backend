package site.leona.teamreboot.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.leona.teamreboot.common.entity.enums.ErrorCode
import site.leona.teamreboot.config.exception.BusinessException
import site.leona.teamreboot.entity.CreditTransaction
import site.leona.teamreboot.entity.CreditUsage
import site.leona.teamreboot.entity.enums.CreditUsageStatus
import site.leona.teamreboot.entity.enums.FeatureUnit
import site.leona.teamreboot.entity.enums.Type
import site.leona.teamreboot.model.CustomerDto
import site.leona.teamreboot.repository.*
import java.time.LocalDate
import java.time.LocalDateTime

@Transactional
@Service
class CustomerServiceImpl(
    private val creditUsageRepository: CreditUsageRepository,
    private val creditTransactionRepository: CreditTransactionRepository,
    private val customerRepository: CustomerRepository,
    private val planRepository: PlanRepository,
    private val planFeatureRepositorySupport: PlanFeatureRepositorySupport,
    private val creditUsageRepositorySupport: CreditUsageRepositorySupport,
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

        // 4. 기본 제한량에 대해서 체크
        // 만약 글자수 단위 제한인 경우 -> 매회당 제한걸림
        // 월 회수제한인 경우 해당 월의 시작부터 사용하고자하는 일까지의 사용량 체크해야함
        // 4-1. 일단 갈자수 단위 제한부터 시작
        val unitUsed = param.unitUsed

        if (feature.unit === FeatureUnit.CHARS && unitUsed > feature.defaultLimit) {
            throw BusinessException(ErrorCode.PLAN_LIMIT_EXCEEDED)
        }

        // 4-2. 월횟수 단위 제한 -> 기존 사용기록 검색 필요
        // 시작일 체크
        val startDate = LocalDate.now().withDayOfMonth(1)
        val endDate = LocalDate.now()

        val creditUsageCount: Long = creditUsageRepositorySupport.getCountCustomerFeatureUsage(customerId, feature.featureId, startDate, endDate)

        if (feature.unit === FeatureUnit.MONTHLY && creditUsageCount > feature.defaultLimit) {
            throw BusinessException(ErrorCode.PLAN_LIMIT_EXCEEDED)
        }

        // 5. 사용 크레딧 계산
        val creditPerUnit = feature.creditPerUse
        val totalCreditToUse = feature.unit.calculateCredit(unitUsed, creditPerUnit)

        if (totalCreditToUse > customer.creditBalance) {
            throw BusinessException(ErrorCode.INSUFFICIENT_CREDIT)
        }

        // 5. 고객의 크레딧 차감
        param.doChangeCustomer(customer, customer.creditBalance - totalCreditToUse)
        customerRepository.save(customer)

        // 6. 크레딧 사용 이력 저장
        val usageCredit = creditUsageRepository.save(CreditUsage.doCreate(customer, feature, LocalDateTime.now(), unitUsed, totalCreditToUse, CreditUsageStatus.USAGE))

        // 7. 트랜잭션 저장
        val creditTransaction = CreditTransaction.doCreate(customer, totalCreditToUse, Type.USAGE, usageCredit)
        creditTransactionRepository.save(creditTransaction)

        // 8. 리턴 객채 생성후 완료 처리
        return CustomerDto.UseFeatureResponse(customerId, totalCreditToUse, 0L);
    }
}