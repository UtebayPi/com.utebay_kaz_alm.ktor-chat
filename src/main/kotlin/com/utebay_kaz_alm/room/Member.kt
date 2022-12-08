package com.utebay_kaz_alm.room

import io.ktor.websocket.*

data class Member(
    val username: String,
    //Идентификатор для отдельной сессий, для отдельных пользователей
    val sessionId: String,
    val socket: WebSocketSession
    )