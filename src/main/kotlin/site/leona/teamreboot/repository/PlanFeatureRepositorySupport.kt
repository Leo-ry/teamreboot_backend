package site.leona.teamreboot.repository

import site.leona.teamreboot.entity.PlanFeature

interface PlanFeatureRepositorySupport {
    fun getCustomerPlanFeature(customerId: Long, featureId: Long): PlanFeature?
}