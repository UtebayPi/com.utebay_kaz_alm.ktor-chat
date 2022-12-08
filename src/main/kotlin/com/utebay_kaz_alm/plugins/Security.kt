package com.utebay_kaz_alm.plugins

import com.utebay_kaz_alm.session.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSecurity() {
    install(Sessions) {
        //Сохраняет данные между разными HTTP запросами. Как в Сайте.
        //Вот так в cookie мы сохраняем объект ChatSession с ключом "SESSION"
        cookie<ChatSession>("SESSION")
    }
    //intercept смотрит на каждый сделанный запрос. На все.
    intercept(ApplicationCallPipeline.Features) {
        //call это запрос который отправило приложение. Из него можно получить данные.
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"] ?: "Guest"
            //Есть же щанс что может сгенерировать два одинаковых, даже если мал?
            //Есть generateSessionId(). Может его использовать? А оно как не допускает дубликаты?
            call.sessions.set(ChatSession(username, generateNonce()))
        }

    }
}
