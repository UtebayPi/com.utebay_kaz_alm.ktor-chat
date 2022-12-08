package com.utebay_kaz_alm.data

import com.utebay_kaz_alm.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages(): List<Message>

    suspend fun insertMessage(message: Message)
}