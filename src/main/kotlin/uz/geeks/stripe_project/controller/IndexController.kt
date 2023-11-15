package uz.geeks.stripe_project.controller

import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import uz.geeks.stripe_project.model.CustomerData
import uz.geeks.stripe_project.util.StripeUtil

@Controller
@RequestMapping("api/v1")
class IndexController(private var stripeUtil: StripeUtil) {

    @Value("\${stripe.key}")
    private val stripeKey: String? = null


    @RequestMapping("/customer")
    @Throws(StripeException::class)
    fun customer(model: Model): String {
        Stripe.apiKey = stripeKey

        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3

        val customers = Customer.list(params)
        val allCustomer: MutableList<CustomerData> = ArrayList()
        for (i in 0 until customers.data.size) {
            val customerData = CustomerData()
            customerData.customerId = customers.data[i].id
            customerData.name = customers.data[i].name
            customerData.email = customers.data[i].email
            allCustomer.add(customerData)
        }
        model.addAttribute("customers", allCustomer)
        return "customer"
    }

    @RequestMapping("/index")
    @Throws(StripeException::class)
    fun index(model: Model): String {
        Stripe.apiKey = stripeKey

        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3

        val customers = Customer.list(params)
        val allCustomer: MutableList<CustomerData> = ArrayList()
        for (i in 0 until customers.data.size) {
            val customerData = CustomerData()
            customerData.customerId = customers.data[i].id
            customerData.name = customers.data[i].name
            customerData.email = customers.data[i].email
            allCustomer.add(customerData)
        }
        model.addAttribute("customers", allCustomer)
        return "index"
    }

    @RequestMapping("/createCustomer")
    fun createCustomer(customerData: CustomerData): String {
        return "create-customer"
    }

    @RequestMapping("/addCustomer")
    @Throws(StripeException::class)
    fun addCustomer(customerData: CustomerData): String {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        customerData.name?.let { params["name"] = it }
        customerData.email?.let { params["email"] = it }
        Customer.create(params)

        return "success"
    }

    @RequestMapping("/deleteCustomer/{id}")
    @Throws(StripeException::class)
    fun deleteCustomer(@PathVariable("id") id: String): String {
        Stripe.apiKey = stripeKey
        val customer = Customer.retrieve(id)
        customer.delete()
        return "success"
    }

    @RequestMapping("/getCustomer/{id}")
    @Throws(StripeException::class)
    fun getCustomer(@PathVariable("id") id: String, model: Model): String {
        Stripe.apiKey = stripeKey
        val output: CustomerData = stripeUtil.getCustomer(id)
        model.addAttribute("customerData", output)
        return "update-customer"
    }
}


