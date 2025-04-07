package site.leona.teamreboot.model

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import site.leona.teamreboot.common.entity.enums.ResponseStatus
import site.leona.teamreboot.entity.Feature
import site.leona.teamreboot.entity.Plan
import site.leona.teamreboot.entity.PlanFeature


object PlanDto {
    data class CreateResponse(val planId: Long?, val status: ResponseStatus = ResponseStatus.SUCCESS)

    data class CreateParam(
        @field:NotBlank
        val name: String = "",

        @field:NotBlank
        @field:Size(min = 1, message = "최소한 1개 이상의 기능을 선택해주세요.")
        val features: List<FeatureModel> = emptyList()
    ) {
        // 요금제 생성
        fun toPlan(): () -> Plan {
            return { Plan.doCreate(name).invoke() }
        }
    }

    data class FeatureModel (
        @field:NotNull @field:Min(1)
        val id: Long = 0,

        @field:NotNull @field:Min(1)
        val customLimit: Long = 0
    ) {
        // 생성된 요금제에 기능 추가
        fun toPlanFeature(plan: Plan, feature: Feature, customLimit: Long): () -> PlanFeature {
            return { PlanFeature.toPlanFeature(plan, feature, customLimit).invoke() }
        }
    }
}
