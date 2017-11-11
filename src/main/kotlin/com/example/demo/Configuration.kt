package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RouterFunctions.route


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

@Configuration
@EnableReactiveMongoRepositories
class Configuration {

    @Bean
    fun router(customerService : CustomerHandler) : RouterFunction<ServerResponse>
    {
        return route(GET("/customer/{id}"), HandlerFunction(customerService::get))
                .and(route(POST("/customer"), HandlerFunction(customerService::add)))
                .and(route(POST("/customer/{id}"), HandlerFunction(customerService::delete)))
                .and(route(POST("/customer/getAll"), HandlerFunction {customerService.getAll()}))
    }

}