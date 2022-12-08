package com.utebay_kaz_alm.room

import com.utebay_kaz_alm.data.MessageDataSource
import com.utebay_kaz_alm.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    //Чтобы выполнялось по очереди и не было багов
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username))
            throw MemberAlreadyExistsException()

        members[username] = Member(
            username = username,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(
        senderUsername: String,
        message: String
    ) {
        //изменил код вынув из лупа. Врятле нужно много раз это вызывать.
        val messageEntity = Message(
            text = message,
            username = senderUsername,
            timestamp = System.currentTimeMillis()
        )
        //Сохраняем Message в Базе данных
        messageDataSource.insertMessage(messageEntity)
        //парсируем его
        val parsedMessage = Json.encodeToString(messageEntity)
        members.values.forEach { member ->
            //Вот так можно отправить данные с помощью socket. Здесь мы отправляет текст, но можно отправить байты,
            // если нужно отправить файл
            member.socket.send(Frame.Text(parsedMessage))
            //Не кажется лучшим вариантом. Так две источника правды. Это и первоначальное.
            //Разве не лучше просто следить за обновлением в базе данных? Так только один источник правды.
        }
    }

    suspend fun getAllMessages(): List<Message> = messageDataSource.getAllMessages()

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username)) members.remove(username)
    }
}