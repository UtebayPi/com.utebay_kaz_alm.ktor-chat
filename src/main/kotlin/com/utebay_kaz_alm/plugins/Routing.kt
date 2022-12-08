package com.utebay_kaz_alm.plugins

import com.utebay_kaz_alm.room.RoomController
import com.utebay_kaz_alm.routes.chatSocket
import com.utebay_kaz_alm.routes.getAllMessages
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    install(Routing) {
        chatSocket(roomController)
        getAllMessages(roomController)
    }

//    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }
//    }
}
