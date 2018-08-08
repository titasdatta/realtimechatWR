package com.example.titas.realtimechatapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.titas.realtimechatapp.common.runOnIoThread
import com.example.titas.realtimechatapp.repository.dao.ConversationDao
import com.example.titas.realtimechatapp.repository.model.Message
import com.example.titas.realtimechatapp.repository.service.chat.MessagingService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Titas on 8/5/2018.
 */
@Singleton
class ConversationRepository @Inject constructor(val conversationDao: ConversationDao,
                                                 val messagingService: MessagingService)
    : MessagingService.OnMessageReceivedListener {

    val mutableListOfMessages: MutableLiveData<List<Message>> = MutableLiveData()

    init {
        messagingService.setOnMessageReceivedListener(this)
        messagingService.getMessages()
    }

    override fun onMessageReceived(message: Message) {
        insertMessage(message)
    }

    fun getConversationList(): LiveData<List<Message>>{
        return conversationDao.getConversationList()
    }

    fun sendMessage(message: Message){
        messagingService.sendMessage(message)
    }

    override fun onHistoryMessagesReceived(messageList: List<Message>) {
//        mutableListOfMessages.postValue(messageList)
        runOnIoThread {
            conversationDao.addBulkMessages(messageList)
        }
    }


    private fun insertMessage(message: Message){
        runOnIoThread {
            conversationDao.addMessage(message)
        }
    }
}