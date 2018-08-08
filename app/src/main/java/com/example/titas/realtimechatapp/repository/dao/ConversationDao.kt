package com.example.titas.realtimechatapp.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.titas.realtimechatapp.repository.model.Message

/**
 * Created by Titas on 8/5/2018.
 */
@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversationList ORDER BY receivedTime")
    fun getConversationList(): LiveData<List<Message>>

    @Insert(onConflict = REPLACE)
    fun addMessage(message: Message)

    @Insert
    fun addBulkMessages(messageList: List<Message>)

    @Query("DELETE FROM conversationList")
    fun deleteAll()
}