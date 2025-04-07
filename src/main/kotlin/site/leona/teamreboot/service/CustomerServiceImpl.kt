package site.leona.teamreboot.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.leona.teamreboot.model.CustomerDto
import site.leona.teamreboot.repository.CustomerRepository
import site.leona.teamreboot.repository.PlanRepository

@Transactional
@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val planRepository: PlanRepository
): CustomerService {
    override fun assignPlan(customerId: Long, param: CustomerDto.AssignPlanParam): CustomerDto.AssignPlanResponse {
        // 1. 가져온 고객 ID 로 실제 존재하는 고객인지 확인
        val customer =  customerRepository.findById(customerId).orElseThrow { RuntimeException("Customer not found!") }

        // 2. 가져온 Plan ID 로 실제 존재하는 요금제인지 확인
        val plan = planRepository.findById(param.planId).orElseThrow { RuntimeException("Plan not found!") }

        // 3. 둘다 확인된 경우 고객 정보에 요금제 정보를 업데이트 처리
        param.doChangeCustomer(customer, plan)
        customerRepository.save(customer)

        // 4. 리턴 객체 생성후 완료 처리
        return CustomerDto.AssignPlanResponse(customer.customerId)
    }

    override fun useFeature(customerId: Long, param: CustomerDto.UseFeatureParam): CustomerDto.UseFeatureResponse {
        TODO("Not yet implemented")
    }
}