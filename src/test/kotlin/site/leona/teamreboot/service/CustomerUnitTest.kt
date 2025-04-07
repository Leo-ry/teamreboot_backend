package site.leona.teamreboot.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import site.leona.teamreboot.repository.CustomerRepository
import site.leona.teamreboot.repository.PlanRepository

@ExtendWith(MockitoExtension::class)
class CustomerUnitTest {

    @Mock
    lateinit var customerRepository: CustomerRepository

    @Mock
    lateinit var planRepository: PlanRepository

    @InjectMocks
    lateinit var customerService: CustomerServiceImpl

    @Test
    fun `고객_요금제_정상연결_테스트`() {


    }
}