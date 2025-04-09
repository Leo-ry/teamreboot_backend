package site.leona.teamreboot.repository

import site.leona.teamreboot.model.StatisticsDto
import java.time.LocalDate

interface CreditUsageRepositorySupport {
    fun getCustomerCreditUsages(customerId: Long, from: LocalDate, to: LocalDate, featureId: Long?): List<StatisticsDto.FeatureStat>
    fun getCountCustomerFeatureUsage(customerId:Long, featureId: Long?, startDate: LocalDate, endDate: LocalDate): Long
}