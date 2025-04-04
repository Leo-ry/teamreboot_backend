package site.leona.teamreboot.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.leona.teamreboot.entity.CreditTransaction

interface CreditTransactionRepository : JpaRepository<CreditTransaction, Long>