package site.leona.teamreboot.repository

import site.leona.teamreboot.model.StatisticsDto
import java.time.LocalDate

interface CreditUsageRepositorySupport {
    fun getCustomerCreditUsages(customerId: Long, from: LocalDate, to: LocalDate, featureId: Long?): List<StatisticsDto.FeatureStat>
}