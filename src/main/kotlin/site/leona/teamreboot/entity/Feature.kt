package site.leona.teamreboot.entity

import jakarta.persistence.*
import site.leona.teamreboot.common.entity.BaseEntity
import site.leona.teamreboot.entity.enums.FeatureUnit
import site.leona.teamreboot.entity.enums.Status

@Entity
@Table(name = "feature")
data class Feature (

    @Column(name = "name")
    var name: String = "",

    @Column(name = "default_limit")
    var defaultLimit: Long = 0,

    @Column(name = "credit_per_use")
    var creditPerUse: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    var unit: FeatureUnit = FeatureUnit.CHARS,

    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val featureId: Long? = 0

//    fun toFeature(defaultLimit: Long, creditPerUse: Long, unit: Status): () -> Feature {
//        return { Feature(defaultLimit, defaultLimit, unit) }
//    }
}