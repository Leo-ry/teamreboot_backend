package site.leona.teamreboot.service

import site.leona.teamreboot.model.CustomerDto

interface CustomerService {
    fun assignPlan(customerId: Long, param: CustomerDto.AssignPlanParam): CustomerDto.AssignPlanResponse
    fun useFeature(customerId: Long, param: CustomerDto.UseFeatureParam): CustomerDto.UseFeatureResponse
}