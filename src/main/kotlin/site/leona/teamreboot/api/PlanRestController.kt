package site.leona.teamreboot.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.leona.teamreboot.model.PlanDto
import site.leona.teamreboot.service.PlanService

@Tag(name = "요금제 관련 기능")
@RequestMapping("/api/plans")
@RestController
class PlanRestController(
    private val planService: PlanService
) {

    // 신규 요금제 생성 -> 요금이름 + 요금기능 + 요금제한 설정
    @Operation(summary = "신규 요금제 생성", description = "요금명 + 요금제 기능 + 요금제 제한설정 기능을 조합하여 요금제를 생성합니다.")
    @PostMapping
    fun createPlan(@RequestBody @Valid param: PlanDto.CreateParam): PlanDto.CreateResponse {
        return planService.createPlan(param)
    }
}