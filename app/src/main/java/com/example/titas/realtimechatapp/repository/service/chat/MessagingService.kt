package com.example.titas.realtimechatapp.repository.service.chat

import com.example.titas.realtimechatapp.repository.model.Message

/**
 * Created by Titas on 8/5/2018.
 */
interface MessagingService {
    interface OnMessageReceivedListener {
        fun onMessageReceived(message: Message)
        fun onHistoryMessagesReceived(messageList: List<Message>)
    }

    fun sendMessage(message: Message)
    fun getMessages()
    fun setOnMessageReceivedListener(listener: OnMessageReceivedListener?)
}
