package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity
import site.leona.teamreboot.entity.enums.CreditUsageStatus
import java.time.LocalDateTime

@Entity
@Table(name = "credit_usage")
class CreditUsage(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    var feature: Feature? = null,

    @Column(name = "used_at")
    var usedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "unit_used")
    var unitUsed: Long = 0,

    @Column(name = "credit_used")
    var creditUsed: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: CreditUsageStatus = CreditUsageStatus.USAGE,

    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val creditUsageId: Long = 0

    companion object {
        fun doCreate(customer: Customer, feature: Feature, usedAt: LocalDateTime, creditUsed: Long, status: CreditUsageStatus): CreditUsage {
            return CreditUsage(customer = customer
                , feature = feature
                , usedAt = usedAt
                , unitUsed = creditUsed
                , status = status)
        }
    }
}
