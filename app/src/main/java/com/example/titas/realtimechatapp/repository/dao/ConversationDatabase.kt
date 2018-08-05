package com.example.titas.realtimechatapp.repository.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.titas.realtimechatapp.repository.model.Message

/**
 * Created by Titas on 8/5/2018.
 */
@Database(entities = arrayOf(Message::class), version = 1)
abstract class ConversationDatabase: RoomDatabase() {
    abstract fun getConversationDao(): ConversationDao
}