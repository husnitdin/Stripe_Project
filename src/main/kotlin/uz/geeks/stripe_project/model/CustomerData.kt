package uz.geeks.stripe_project.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CustomerData(

    var customerId: String? = null,
    var name: String? = null,
    var email: String? = null

)