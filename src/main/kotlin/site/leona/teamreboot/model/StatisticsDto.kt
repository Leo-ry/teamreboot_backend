package site.leona.teamreboot.model

import java.time.LocalDate

object StatisticsDto {

    data class UsageStatResponse (
        val customerId: Long,
        val from: LocalDate,
        val to: LocalDate,
        val creditBalance: Long,
        val featureUsages: List<FeatureStat>,
    ) {

    }

    data class FeatureStat(
        val featureName: String,
        val totalUsageCount: Long,
        val totalUnitUsed: Long,
        val totalCreditUsed: Long
    ) {

    }

}