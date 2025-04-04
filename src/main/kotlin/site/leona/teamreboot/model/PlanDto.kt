package site.leona.teamreboot.model

import lombok.Getter
import lombok.Setter

class PlanDto {

    @Getter
    @Setter
    class CreateResponse {
       val planId: Long = 0
    }

    @Getter
    @Setter
    class CreateParam {
        val name: String = ""
        val features: List<Feature>? = null
    }

    class Feature {
        val id: Long = 0
        val customLimit: Long = 0
    }



}
