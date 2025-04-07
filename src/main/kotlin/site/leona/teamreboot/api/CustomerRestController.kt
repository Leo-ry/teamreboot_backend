package site.leona.teamreboot.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import site.leona.teamreboot.model.CustomerDto
import site.leona.teamreboot.service.CustomerService

@Tag(name = "고객사 관련 기능", description = "")
@RequestMapping("/api/customers")
@RestController
class CustomerRestController(
    private val customerService: CustomerService
) {

    @Operation(summary = "고객사 요금제 등록", description = "고객사에 맞춤으로 작성된 요금제를 사용할 수 있도록 등록")
    @PostMapping("/{customerId}/plan")
    fun assignPlan(@PathVariable customerId: Long, @RequestBody @Valid param: CustomerDto.AssignPlanParam): CustomerDto.AssignPlanResponse {
        return customerService.assignPlan(customerId, param)
    }

    @Operation(summary = "고객사 기능사용 체크", description = "고객사가 실제 기능을 사용할 때에 정합성 체크")
    @PostMapping("/{customerId}/use-feature")
    fun useFeature(@PathVariable customerId: Long, @RequestBody @Valid param: CustomerDto.UseFeatureParam): CustomerDto.UseFeatureResponse {
        return customerService.useFeature(customerId, param)
    }
}