package site.leona.teamreboot.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository
import site.leona.teamreboot.common.CommonRepositorySupport
import site.leona.teamreboot.entity.CreditUsage
import site.leona.teamreboot.entity.QCreditUsage
import site.leona.teamreboot.entity.QCustomer
import site.leona.teamreboot.entity.QFeature
import site.leona.teamreboot.model.StatisticsDto
import java.time.LocalDate

@Repository
class CreditUsageRepositorySupportImpl(domainClass: Class<CreditUsage>) : CreditUsageRespositorySupport, CommonRepositorySupport(
    domainClass
) {
    override fun getCustomerCreditUsages(
        customerId: Long,
        from: LocalDate,
        to: LocalDate,
        featureId: Long?
    ): List<StatisticsDto.FeatureStat> {
        // Entity
        var customer: QCustomer = QCustomer.customer
        var creditUsage: QCreditUsage = QCreditUsage.creditUsage
        var feature: QFeature = QFeature.feature

        // Where Clause
        var bb: BooleanBuilder = BooleanBuilder()

        bb.and(customer.customerId.eq(customerId))
        bb.and(creditUsage.usedAt.between(from.atStartOfDay(), to.atTime(23,59,59)))

        // 기능별 검색을 사용시 빈값이 아닌 경우만 추가할 수 있도록
        if(featureId != null) {
            bb.and(feature.featureId.eq(featureId))
        }

        return select(Projections.constructor(StatisticsDto.FeatureStat::class.java,
            creditUsage.feature.name,
            creditUsage.count(),
            creditUsage.unitUsed.sum(),
            creditUsage.creditUsed.sum()
            ))
            .from(creditUsage)
            .join(creditUsage.feature, feature)
            .where(bb)
            .orderBy(creditUsage.usedAt.desc())
            .groupBy(feature.name)
            .fetch()
    }

}