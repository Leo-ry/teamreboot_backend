package site.leona.teamreboot.repository

import site.leona.teamreboot.model.StatisticsDto
import java.time.LocalDate

interface CreditUsageRespositorySupport {
    fun getCustomerCreditUsages(customerId: Long, from: LocalDate, to: LocalDate, featureId: Long?): List<StatisticsDto.FeatureStat>
}