package site.leona.teamreboot.service

import jakarta.persistence.ManyToOne
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import site.leona.teamreboot.common.entity.enums.ResponseStatus
import site.leona.teamreboot.config.exception.BusinessException
import site.leona.teamreboot.entity.CreditTransaction
import site.leona.teamreboot.entity.Customer
import site.leona.teamreboot.entity.Plan
import site.leona.teamreboot.model.CustomerDto
import site.leona.teamreboot.repository.*
import java.util.Optional
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class CustomerUnitTest {

    @Mock
    lateinit var planFeatureRepositorySupport: PlanFeatureRepositorySupport

    @Mock
    lateinit var planFeatureRepository: PlanFeatureRepository

    @Mock
    lateinit var creditTransactionRepository: CreditTransactionRepository

    @Mock
    lateinit var creditUsageRepository: CreditUsageRepository

    @Mock
    lateinit var customerRepository: CustomerRepository

    @Mock
    lateinit var planRepository: PlanRepository

    @InjectMocks
    lateinit var customerService: CustomerServiceImpl

    @Test
    fun `고객_요금제_정상연결_테스트`() {
        val customerId = 0L
        val planId = 0L
        val customer = Customer(name = "고객사A")
        val plan = Plan(name = "프리미엄 요금제")

        val param = CustomerDto.AssignPlanParam(planId = planId)

        whenever(customerRepository.findById(customerId)).thenReturn(Optional.of(customer))
        whenever(planRepository.findById(planId)).thenReturn(Optional.of(plan))
        whenever(customerRepository.save(any<Customer>())).thenReturn(customer)

        val result = customerService.assignPlan(customerId, param)

        assertEquals(customerId, result.customerId)
        assertEquals(ResponseStatus.SUCCESS, result.status)
        assertEquals(plan, customer.plan)
    }

    @Test
    fun `비정상 고객 연결 테스트`() {
        val param = CustomerDto.AssignPlanParam(planId = 1L)
        whenever(customerRepository.findById(999L)).thenReturn(Optional.empty())

        val ex = assertThrows<BusinessException> {
            customerService.assignPlan(999L, param)
        }

        assertEquals("고객 정보를 찾을 수 없습니다.", ex.message)
    }

    @Test
    fun `비정상 요금제 연결 테스트`() {
        val customer = Customer(name = "고객사A")
        val param = CustomerDto.AssignPlanParam(planId = 999L)

        whenever(customerRepository.findById(1L)).thenReturn(Optional.of(customer))
        whenever(planRepository.findById(999L)).thenReturn(Optional.empty())

        val ex = assertThrows<BusinessException> {
            customerService.assignPlan(1L, param)
        }

        assertEquals("해당 기능이 존재하지 않습니다.", ex.message)
    }


}