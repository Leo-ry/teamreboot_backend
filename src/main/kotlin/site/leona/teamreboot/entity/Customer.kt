package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity

@Entity
@Table(name = "customer")
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val customerId: Long = 0,

    @Column(name = "name")
    val name: String = "",

    @Column(name = "credit_balance")
    val creditBalance: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    val plan: Plan? = null
) : BaseEntity()