package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse


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
//@EnableReactiveMongoRepositories
class Configuration {

    @Bean
    fun router(customerHandler: CustomerHandler): RouterFunction<ServerResponse> {
        return route(GET("/customer/{id}"), HandlerFunction(customerHandler::get))
                .andRoute(POST("/customer"), HandlerFunction(customerHandler::add))
                .andRoute(DELETE("/customer/{id}"), HandlerFunction(customerHandler::delete))
                .andRoute(GET("/customer").and(accept(MediaType.TEXT_EVENT_STREAM)), HandlerFunction(customerHandler::getAll))
    }

}