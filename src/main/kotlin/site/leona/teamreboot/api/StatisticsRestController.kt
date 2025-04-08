package site.leona.teamreboot.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import site.leona.teamreboot.model.StatisticsDto
import site.leona.teamreboot.service.StatisticsService
import java.time.LocalDate

@Tag(name = "고객의 통계 관련")
@RequestMapping("/api/stats")
@RestController
class StatisticsRestController(
    private val statisticsService: StatisticsService
) {

    @Operation(summary = "고객의 기능별 사용 내역 조회")
    @GetMapping("/{customerId}/usages")
    fun getCustomerUsages(
        @PathVariable customerId: Long,
        @RequestParam featureId: Long,
        @RequestParam from: LocalDate,
        @RequestParam to: LocalDate
    ): StatisticsDto.UsageStatResponse {
        return statisticsService.getCustomerUsages(customerId, featureId, from, to);
    }
}