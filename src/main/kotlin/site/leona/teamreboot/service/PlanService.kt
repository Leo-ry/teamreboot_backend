package site.leona.teamreboot.service

import site.leona.teamreboot.model.PlanDto

interface PlanService {

    fun createPlan(param: PlanDto.CreateParam): PlanDto.CreateResponse
}