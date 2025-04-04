package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity

@Entity
@Table(name = "plan_feature")
data class PlanFeature(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val planFeatureId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    val plan: Plan? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    val feature: Feature? = null,

    @Column(name = "custom_limit")
    val customLimit: Long = 0,

    ) : BaseEntity()
