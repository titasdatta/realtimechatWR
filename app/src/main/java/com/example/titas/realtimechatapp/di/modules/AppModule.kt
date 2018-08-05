package com.example.titas.realtimechatapp.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.example.titas.realtimechatapp.repository.dao.ConversationDao
import com.example.titas.realtimechatapp.repository.dao.ConversationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Titas on 8/5/2018.
 */
@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = app

    @Provides
    @Singleton
    fun providesConversationDatabase(app: Application): ConversationDatabase = Room.databaseBuilder(app,
            ConversationDatabase::class.java, "conversation_list").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesConversationDao(database: ConversationDatabase): ConversationDao = database.getConversationDao()


}