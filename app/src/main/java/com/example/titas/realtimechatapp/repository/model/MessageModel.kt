package com.example.titas.realtimechatapp.repository.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Titas on 8/5/2018.
 */

@Entity(tableName = "conversationList")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class Message constructor(@PrimaryKey(autoGenerate = true) @JsonProperty("id") var id: Long=0,
                               @ColumnInfo(name = "messageText") @JsonProperty("messageText") val messageText: String,
                               @ColumnInfo(name = "senderName") @JsonProperty("senderName") val senderName: String,
                               @ColumnInfo(name = "receivedTime") @JsonProperty("receivedTime") val receivedTimeStamp: String,
                               @JsonProperty("isIncoming") var isIncoming: Boolean=false)


data class MessageThread(val title:String, val lastMessage: String, val lastTimeStamp: String,
                         val conversationList: ArrayList<Message>)

