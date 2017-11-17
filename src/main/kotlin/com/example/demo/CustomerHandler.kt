package com.example.demo

import com.example.demo.model.Customer
import com.example.demo.repo.CustomerRepo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import reactor.core.publisher.Mono
import java.util.*


/* Copyright 2017 Nikesh Pathak
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */

@Component
class CustomerHandler {


     var customerRepo : CustomerRepo

    constructor(customerRepo: CustomerRepo)
    {
        this.customerRepo = customerRepo
    }

     fun add(request : ServerRequest) : Mono<ServerResponse>
    {
        var customerLocal = request.bodyToMono(Customer::class.java).block();
            customerLocal.id = UUID.randomUUID().toString()
        return status(HttpStatus.CREATED).body(customerRepo.save(customerLocal), Customer::class.java)
    }

    fun get(request : ServerRequest) : Mono<ServerResponse>
    {
        val customer = customerRepo.findById(request.bodyToMono(String::class.java))
        return ok().body(customer, Customer::class.java)
    }

     fun delete(request: ServerRequest) : Mono<ServerResponse>
    {
        customerRepo.deleteById(request.bodyToMono(String::class.java))
        return ok().build()
    }

     fun getAll(request : ServerRequest) : Mono<ServerResponse>
    {
        var customer = customerRepo.findAll()
        return ok().body(customer, Customer::class.java)
    }

}