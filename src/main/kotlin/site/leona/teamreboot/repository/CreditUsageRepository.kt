package site.leona.teamreboot.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.leona.teamreboot.entity.CreditUsage

interface CreditUsageRepository : JpaRepository<CreditUsage, Long>