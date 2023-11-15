package uz.geeks.stripe_project.util

import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Customer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import uz.geeks.stripe_project.model.CustomerData

@Component
class StripeUtil {

    @Value("\${stripe.key}")
    private val stripeKey: String? = null

    @Throws(StripeException::class)
    fun getCustomer(id: String): CustomerData {
        Stripe.apiKey = stripeKey

        val customer = Customer.retrieve(id)
        return setCustomerData(customer)
    }

    fun setCustomerData(customer: Customer): CustomerData {
        val customerData = CustomerData()
        customerData.customerId = customer.id
        customerData.name = customer.name
        customerData.email = customer.email

        return customerData
    }
}
