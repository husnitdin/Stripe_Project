package uz.geeks.stripe_project.controller

import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import uz.geeks.stripe_project.model.CustomerData
import uz.geeks.stripe_project.util.StripeUtil

@RestController
@RequestMapping("api/v1")
class StripeController(private var stripeUtil: StripeUtil) {

    @Value("\${stripe.key}")
    private val stripeKey: String? = null

    @PostMapping("/createCustomer")
    @Throws(StripeException::class)
    fun striping(@RequestBody data: CustomerData): CustomerData{

        Stripe.apiKey = stripeKey
        val params = mapOf(
            "name" to data.name,
            "email" to data.email,
        )
        val customer = Customer.create(params)
        data.customerId = customer.id

        return data
    }

    @GetMapping("/getAllCustomer")
    @Throws(StripeException::class)
    fun getAllCustomer(): List<CustomerData> {
        Stripe.apiKey = stripeKey

        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3

        val customers = Customer.list(params)
        val customerList: MutableList<CustomerData> = ArrayList()

        for (i in 0 until customers.data.size) {
            val customerData = CustomerData()
            customerData.customerId = customers.data[i].id
            customerData.name = customers.data[i].name
            customerData.email = customers.data[i].email
            customerList.add(customerData)
        }
        return customerList
    }

    @DeleteMapping("/deleteCustomer/{id}")
    @Throws(StripeException::class)
    fun deleteCustomer(@PathVariable("id") id: String): String {
        Stripe.apiKey = stripeKey
        val customer = Customer.retrieve(id)
        customer.delete()

        return "successfully deleted"
    }

    @GetMapping("/getCustomer/{id}")
    @Throws(StripeException::class)
    fun getCustomer(@PathVariable("id") id: String): CustomerData {
        return stripeUtil.getCustomer(id)
    }

}