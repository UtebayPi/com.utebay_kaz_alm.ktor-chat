package com.utebay_kaz_alm.data

import com.utebay_kaz_alm.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageDataSourceImpl(private val db: CoroutineDatabase) : MessageDataSource {
    //Похоже на firebase.
    //Вот так просто получаем нужную таблицу смотря на тип,
    // и если его нету то оно автоматом создается.
    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages.find()
            .descendingSort(Message::timestamp)
            .toList()
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}