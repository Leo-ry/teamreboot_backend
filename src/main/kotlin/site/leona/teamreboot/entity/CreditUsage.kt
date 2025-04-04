package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity
import site.leona.teamreboot.entity.enums.Status
import java.time.LocalDateTime

@Entity
@Table(name = "credit_usage")
class CreditUsage(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val creditUsageId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    val customer: Customer? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    val feature: Feature? = null,

    @Column(name = "used_at")
    val usedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "unit_used")
    val unitUsed: Long = 0,

    @Column(name = "credit_used")
    val creditUsed: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: Status = Status.CHARS,
) : BaseEntity()
