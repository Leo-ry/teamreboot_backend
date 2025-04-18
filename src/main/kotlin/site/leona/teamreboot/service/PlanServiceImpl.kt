package site.leona.teamreboot.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.leona.teamreboot.common.entity.enums.ErrorCode
import site.leona.teamreboot.config.exception.BusinessException
import site.leona.teamreboot.entity.Plan
import site.leona.teamreboot.model.PlanDto
import site.leona.teamreboot.repository.FeatureRepository
import site.leona.teamreboot.repository.PlanFeatureRepository
import site.leona.teamreboot.repository.PlanRepository

@Transactional
@Service
class PlanServiceImpl(
    val planRepository: PlanRepository,
    val planFeatureRepository: PlanFeatureRepository,
    val featureRepository: FeatureRepository
): PlanService {

    override fun createPlan(param: PlanDto.CreateParam): PlanDto.CreateResponse {
        // 받아온 파라미터를 기초로 요금제 생성 -> 코틀린에서는 생성한 객체를 다시 새로운 변수에 담지않는 경우 id 값이 자바의 리플렉션처럼 반영되지않음 -> 반드시 생성된 객체는 신규 변수로 선언해서 받을 것
        val newPlan = planRepository.save(param.toPlan().invoke())

        // 필요한 기능에 대해서 실제로 존재하는 기능인지, 활성화 상태인지 체크
        val featureIds: List<Long> = param.features.map { it.id }
        val activeFeatures = featureRepository.findByFeatureIdIn(featureIds).associateBy { it.featureId }

        if (featureIds.size != activeFeatures.size) {
            // TODO 비즈니스 예외처리 할것
            throw BusinessException(ErrorCode.FEATURE_NOT_FOUND)
        }

        // 해당기능으로 생성된 요금제에 대해서 기능 연결
        val newPlanFeatures = param.features.map { f ->
            val feature = activeFeatures[f.id] ?: throw BusinessException(ErrorCode.FEATURE_NOT_FOUND)
            f.toPlanFeature(newPlan, feature, f.customLimit)
        }

        planFeatureRepository.saveAll(newPlanFeatures)

        // 처리완료 후 리턴객체 생성
        return PlanDto.CreateResponse(newPlan.planId)
    }

}