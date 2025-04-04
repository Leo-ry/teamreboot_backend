package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity
import site.leona.teamreboot.entity.enums.Type

@Entity
@Table(name = "credit_transaction")
class CreditTransaction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val creditTransactionId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    val customer: Customer? = null,

    val amount: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: Type = Type.USAGE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_usage_id")
    val relatedCreditUsage: CreditUsage? = null,
) : BaseEntity()
