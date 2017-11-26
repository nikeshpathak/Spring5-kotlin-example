package com.example.demo

import com.example.demo.model.Customer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@RunWith(SpringRunner::class)
@SpringBootTest
class DemoApplicationTests {

    lateinit var client: WebTestClient

    @Autowired
    lateinit var routerFunction: RouterFunction<ServerResponse>

    @Before
    fun setUp() {
        client = WebTestClient.bindToRouterFunction(routerFunction).build()
    }

    @Test
    fun getCustomer() {

        var result = client.get().uri("/customer").exchange().expectStatus().isOk.returnResult<Customer>()
        var customerList = result.responseBody.toStream().collect(Collectors.toList())
        Assert.assertNotNull(customerList)
        Assert.assertTrue(customerList.size > 0)
    }

    @Test
    fun addCustomer() {

        var customer = Mono.just(Customer(UUID.randomUUID().toString(), "Ritesh", "ritesh@test.com", "Pune", "Maharastra", "India"))
        client.post().uri("/customer").body(customer, Customer::class.java).exchange().expectStatus().isCreated

    }

}
