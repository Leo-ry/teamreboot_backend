package site.leona.teamreboot.model

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import site.leona.teamreboot.common.entity.enums.ResponseStatus
import site.leona.teamreboot.entity.CreditUsage
import site.leona.teamreboot.entity.Customer
import site.leona.teamreboot.entity.Feature
import site.leona.teamreboot.entity.Plan
import site.leona.teamreboot.entity.enums.FeatureUnit
import site.leona.teamreboot.entity.enums.Status

object CustomerDto {
    data class AssignPlanResponse(val customerId: Long, val status: ResponseStatus = ResponseStatus.SUCCESS)

    data class AssignPlanParam(val planId: Long) {
        fun doChangeCustomer(customer: Customer, plan: Plan) {
            customer.modifyPlan(plan)
        }
    }

    data class UseFeatureResponse(
        val customerId: Long,
        val creditUsage: Long,
        val creditLimit: Long,
        val status: ResponseStatus = ResponseStatus.SUCCESS,
    )

    data class UseFeatureParam(
        @field: NotNull
        @field:Min(1)
        val featureId: Long,

        @field: NotNull
        @field:Min(1)
        val unitUsed: Long
    ) {
        fun doChangeCustomer(customer: Customer, newCreditBalance: Long) {
            customer.modifyCreditBalance(newCreditBalance)
        }
    }


}