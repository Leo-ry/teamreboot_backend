package site.leona.teamreboot.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.leona.teamreboot.entity.CreditUsage
import site.leona.teamreboot.entity.Customer
import java.time.LocalDateTime

interface CreditUsageRepository : JpaRepository<CreditUsage, Long> {
    fun findAllByCustomerAndUsedAtBetween(customer: Customer?, atStartOfDay: LocalDateTime?, atTime: LocalDateTime?)
}