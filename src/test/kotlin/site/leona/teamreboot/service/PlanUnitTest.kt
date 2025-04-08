package site.leona.teamreboot.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import site.leona.teamreboot.common.entity.enums.ResponseStatus
import site.leona.teamreboot.entity.Feature
import site.leona.teamreboot.entity.Plan
import site.leona.teamreboot.entity.PlanFeature
import site.leona.teamreboot.entity.enums.FeatureUnit
import site.leona.teamreboot.model.PlanDto
import site.leona.teamreboot.repository.FeatureRepository
import site.leona.teamreboot.repository.PlanFeatureRepository
import site.leona.teamreboot.repository.PlanRepository
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class PlanUnitTest {

    @Mock
    lateinit var planRepository: PlanRepository

    @Mock
    lateinit var featureRepository: FeatureRepository

    @Mock
    lateinit var planFeatureRepository: PlanFeatureRepository

    @InjectMocks
    lateinit var planService: PlanServiceImpl

    @Test
    fun `요금제_정상생성_테스트`() {
        val featureId: Long = 0L
        val feature = Feature(name = "AI 번역", defaultLimit = 2000, creditPerUse = 10, unit = FeatureUnit.CHARS)

        val param = PlanDto.CreateParam(
            name = "테스트 요금제1",
            features = listOf(
                PlanDto.FeatureModel(id = featureId, customLimit = 2000)
            )
        )

        val plan = Plan.testPlan(3L, "테스트 요금제1")

        whenever(planRepository.save(any<Plan>())).thenReturn(plan)
        whenever(featureRepository.findByFeatureIdIn(any<List<Long>>())).thenReturn(listOf(feature))
        whenever(planFeatureRepository.saveAll(any<List<PlanFeature>>())).thenReturn(emptyList<PlanFeature>())

        val response = planService.createPlan(param)

        // then
        assertNotNull(response.planId)
        assertEquals(ResponseStatus.SUCCESS, response.status)
    }
}