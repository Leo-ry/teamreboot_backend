package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity

@Entity
@Table(name = "plan")
class Plan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val planId: Long = 0,

    @Column(name = "name")
    val name: String = ""
) : BaseEntity()