package site.leona.teamreboot.model

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import site.leona.teamreboot.common.entity.enums.ResponseStatus
import site.leona.teamreboot.entity.Customer
import site.leona.teamreboot.entity.Plan

object CustomerDto {
    data class AssignPlanResponse(val customerId: Long, val status: ResponseStatus = ResponseStatus.SUCCESS)

    data class AssignPlanParam(val planId: Long) {
        fun doChangeCustomer(customer: Customer, plan: Plan): Customer {
            customer.changePlan(plan)
            return customer
        }
    }

    data class UseFeatureResponse(
        val customerId: Long,
        val status: ResponseStatus = ResponseStatus.SUCCESS,
        val creditUsage: Long,
        val creditLimit: Long
    )

    data class UseFeatureParam(
        @field: NotNull @field:Size(min = 1)
        val planId: Long,

        @field: NotNull @field:Size(min = 1)
        val featureId: Long,

        @field: NotNull @field:Size(min = 1)
        val unitUsed: Long
    ) {

    }


}