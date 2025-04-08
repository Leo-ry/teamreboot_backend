package site.leona.teamreboot.service

import site.leona.teamreboot.model.StatisticsDto
import java.time.LocalDate

interface StatisticsService {
    fun getCustomerUsages(customerId: Long, featureId: Long, from: LocalDate, to: LocalDate): StatisticsDto.UsageStatResponse
}