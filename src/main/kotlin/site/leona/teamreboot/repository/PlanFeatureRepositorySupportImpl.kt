package site.leona.teamreboot.repository

import org.springframework.stereotype.Repository
import site.leona.teamreboot.common.CommonRepositorySupport
import site.leona.teamreboot.entity.PlanFeature
import site.leona.teamreboot.entity.QCustomer
import site.leona.teamreboot.entity.QPlan
import site.leona.teamreboot.entity.QPlanFeature

@Repository
class PlanFeatureRepositorySupportImpl: PlanFeatureRepositorySupport, CommonRepositorySupport(
    PlanFeature::class.java,
) {
    override fun getCustomerPlanFeature(customerId: Long, featureId: Long): PlanFeature? {
        // Entity
        val planFeature = QPlanFeature.planFeature
        val customer = QCustomer.customer
        val plan = QPlan.plan

        return select(planFeature)
            .from(planFeature)
            .join(planFeature.plan, plan)
            .join(customer)
                .on(customer.plan.planId.eq(plan.planId))
            .where(customer.customerId.eq(customerId).and(planFeature.feature.featureId.eq(featureId)))
            .fetchOne()
    }
}