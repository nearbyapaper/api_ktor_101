package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import org.slf4j.event.Level

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(CallLogging) {
        level = Level.INFO
    }
    configureRouting() // handle REST API
    configureSerialization() // handle ContentNegotiation between Serialize + JSON
}