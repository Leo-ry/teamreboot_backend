package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity
import site.leona.teamreboot.entity.enums.Type

@Entity
@Table(name = "credit_transaction")
class CreditTransaction(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,

    var amount: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: Type = Type.USAGE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_usage_id")
    var relatedCreditUsage: CreditUsage? = null,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val creditTransactionId: Long = 0
}
