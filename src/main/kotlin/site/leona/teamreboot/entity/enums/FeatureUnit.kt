package site.leona.teamreboot.entity.enums

enum class FeatureUnit(
    val description: String,
    val isFixedCredit: Boolean,
) {
    CHARS("글자수 기반 크레딧 계산", false) {
        override fun calculateCredit(unitUsed: Long, creditPerUse: Long): Long = unitUsed * creditPerUse

    },
    MONTHLY("월단위 회수 기반 계산", true) {
        override fun calculateCredit(unitUsed: Long, creditPerUse: Long): Long = creditPerUse
    }
    ;

    abstract fun calculateCredit(unitUsed: Long, creditPerUse: Long): Long
}