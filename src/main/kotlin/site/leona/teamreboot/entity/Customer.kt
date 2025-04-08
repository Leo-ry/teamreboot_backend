package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity

@Entity
@Table(name = "customer")
class Customer(
    @Column(name = "name")
    var name: String = "",

    @Column(name = "credit_balance")
    var creditBalance: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    var plan: Plan? = null

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val customerId: Long = 0

    fun modifyPlan(newPlan: Plan) {
        this.plan = newPlan
    }

    fun modifyCreditBalance(newCreditBalance: Long) {
        this.creditBalance = newCreditBalance
    }
}