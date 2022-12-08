package com.utebay_kaz_alm.di

import com.utebay_kaz_alm.data.MessageDataSource
import com.utebay_kaz_alm.data.MessageDataSourceImpl
import com.utebay_kaz_alm.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase("message_db")
    }
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }
    single { RoomController(get()) }
}