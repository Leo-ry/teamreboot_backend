package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity

@Entity
@Table(name = "plan_feature")
class PlanFeature(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    var plan: Plan? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    var feature: Feature? = null,

    @Column(name = "custom_limit")
    var customLimit: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val planFeatureId: Long = 0

    companion object {
        fun toPlanFeature(plan: Plan, feature: Feature, customLimit: Long): PlanFeature {
            return PlanFeature(plan, feature, customLimit)
        }
    }
}
