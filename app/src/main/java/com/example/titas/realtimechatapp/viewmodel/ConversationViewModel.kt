package com.example.titas.realtimechatapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.titas.realtimechatapp.repository.ConversationRepository
import com.example.titas.realtimechatapp.repository.model.Message
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Titas on 8/5/2018.
 */
class ConversationViewModel @Inject constructor(private val repository: ConversationRepository) : ViewModel() {

    fun getConversationListObservable(): LiveData<List<Message>> = repository.getConversationList()

    fun getHistoryConversationObservable(): LiveData<List<Message>> = repository.mutableListOfMessages

    fun sendMessage(messageText: String, memberId: String){
        val timeStamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(Date())
        val message = Message(messageText = messageText, senderName = memberId, receivedTimeStamp = timeStamp, isIncoming = false)
        repository.sendMessage(message)
    }
}