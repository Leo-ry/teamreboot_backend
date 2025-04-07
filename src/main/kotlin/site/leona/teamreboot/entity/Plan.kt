package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity
import java.util.function.Supplier

@Entity
@Table(name = "plan")
class Plan(
    @Column(name = "name")
    var name: String = ""
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val planId: Long? = 0L

    companion object {
        fun doCreate(name: String): () -> Plan {
            return { Plan(name) }
        }

        fun testPlan(id: Long = 1L, name: String = "기본 요금제") = Plan(name).apply {
            val idField = this::class.java.getDeclaredField("planId")
            idField.isAccessible = true
            idField.set(this, id)
        }
    }
}