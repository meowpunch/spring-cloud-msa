package com.antock.bootiful

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes {
    @Value("\${custom.message:Hello Nothing}")
    private lateinit var message: String

    @Bean
    fun route(): RouterFunction<ServerResponse> = router {
        GET("/message") { ok().bodyValue(message) }
    }
}
