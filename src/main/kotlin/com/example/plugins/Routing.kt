package com.example.plugins

import com.example.model.FishingNetBusiness
import com.example.model.Priority
import com.example.model.Task
import com.example.repository.FishingNetBusinessRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

// install and configure it in your Ktor Application
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

fun Application.configureRouting() {
//    // install and configure it in your Ktor Application
//    install(ContentNegotiation) {
//        json(Json {
//            prettyPrint = true
//            isLenient = true
//            ignoreUnknownKeys = true
//        })
//    }

    install(StatusPages) { // for handle error
        exception<IllegalStateException> { call, cause ->
            call.respondText("App in illegal state as ${cause.message}")
        }

        exception<Throwable> { call, cause ->
            call.respondText("Internal server error: ${cause.localizedMessage}", status = HttpStatusCode.InternalServerError)
            throw cause  // Re-throw to log the actual error in the console
        }
    }

    routing {
        staticResources("static", "static")

        // ****** GET API ******
        get("/") {
            call.respondText("Hello World!")
        }
        get("/test1") {
            val text = "<h1>Hello From Ktor</h1>"
            val type = ContentType.parse("text/html")
            call.respondText(text, type)
        }
        get("/error-test") {
            throw IllegalStateException("Too Busy")
        }
        get("/tasks") {
            val tasks = Task("1","test",Priority.Low)
            call.respondText(
                contentType = ContentType.parse("text/html"),
                text = tasks.name
            )
        }
        get("/allFocusBusiness") {
            val res = FishingNetBusinessRepository.getAllBusinesses()
            call.respond(res)
//            call.respondText("Test")
        }
        // ****** POST APT ******
        route("/saveBusiness") {
            post {
                try {
                    val business = call.receive<FishingNetBusiness>()
                    FishingNetBusinessRepository.addNewBusiness(business)

                    // Respond with 201 Created and return the business as a confirmation
                    call.respond(HttpStatusCode.Created, business)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid business data")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Error in JSON conversion")
                }
            }
        }
        // ****** DELETE APT ******
        delete("removeBusiness/{businessName}") {
            val name = call.parameters["businessName"]
            // check null param
            if (name == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing Business Name")
                return@delete
            }
            // call removeTask method
            if (FishingNetBusinessRepository.removeTask(name)) {
                call.respond(HttpStatusCode.NoContent, "Delete Success")
            } else {
                call.respond(HttpStatusCode.NotFound, "Business Not Found")
            }
        }
    }
}
