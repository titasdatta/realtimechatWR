package com.example.titas.realtimechatapp.repository.service.chat

import android.app.Application
import android.provider.Settings
import android.util.Log
import com.example.titas.realtimechatapp.common.*
import com.example.titas.realtimechatapp.repository.model.Message
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.history.PNHistoryResult
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * Created by Titas on 8/5/2018.
 */
class PubNubMessagingService @Inject constructor(): MessagingService, SubscribeCallback() {

    val PUBSUB_CHANNEL = Arrays.asList(PUB_SUB_CHANNEL_NAME)
    val TAG = PubNubMessagingService::class.java.simpleName
    private var mPubNubDataStream: PubNub
    private var mMessageReceivedListener: MessagingService.OnMessageReceivedListener? = null

    init {
        mPubNubDataStream = PubNub(PNConfiguration().setPublishKey(PUBNUB_PUBLISH_KEY)
                .setSubscribeKey(PUBNUB_SUBSCRIBE_KEY)
                .setUuid(Settings.Secure.ANDROID_ID).setSecure(false))
        mPubNubDataStream.addListener(this)
        mPubNubDataStream.subscribe().channels(PUBSUB_CHANNEL).withPresence().execute()

    }

    override fun sendMessage(message: Message) {
//        val msgJson = JSONObject()
//        msgJson.put("messageText", message.messageText)
//        msgJson.put("senderName", message.senderName)
//        msgJson.put("receivedTime", message.receivedTimeStamp)

        val message = mapOf(Pair<String, String>("senderName", message.senderName), Pair<String, String>("messageText", message.messageText),
                Pair<String, String>("receivedTime", message.receivedTimeStamp))

        mPubNubDataStream.publish().channel(PUB_SUB_CHANNEL_NAME)
                .shouldStore(true)
                .message(message)
                .async(object : PNCallback<PNPublishResult>() {
                    override fun onResponse(result: PNPublishResult?, status: PNStatus?) {
                        if(status?.isError!!){
                            Log.v(TAG, "publishErr(${JsonUtil.asJson(result as Any)})")
                        } else {
                            Log.v(TAG, "publish(${JsonUtil.asJson(result as Any)})")
                        }
                    }

                })
    }

    override fun getMessages() {
        mPubNubDataStream.history().channel(PUB_SUB_CHANNEL_NAME)
                .count(100)
                .reverse(true)
                .async(object : PNCallback<PNHistoryResult>() {
                    override fun onResponse(result: PNHistoryResult?, status: PNStatus?) {
                        val messages = result?.messages
                        val messageList = mutableListOf<Message>()
                        messages?.forEach {
                            messageList.add(JsonUtil.convert(it.entry, Message::class.java))
                        }
                        mMessageReceivedListener?.let {
                            it.onHistoryMessagesReceived(messageList)
                        }
                    }

                })
    }

    override fun setOnMessageReceivedListener(listener: MessagingService.OnMessageReceivedListener?) {
        mMessageReceivedListener = listener
    }

    /**
     * PubNub Subscribe CallBacks
     */
    override fun status(pubnub: PubNub?, status: PNStatus?) {

    }

    override fun presence(pubnub: PubNub?, presence: PNPresenceEventResult?) {

    }

    override fun message(pubnub: PubNub?, message: PNMessageResult?) {
        Log.v(TAG, "messageReceived(${JsonUtil.asJson(message as Any)})")
        val jsonMsg: JsonNode = message.message
        val messageModel = JsonUtil.convert(jsonMsg, Message::class.java)
        mMessageReceivedListener?.let {
            it.onMessageReceived(messageModel)
        }
    }
    /**
     * PubNub Subscribe CallBacks
     */

    fun disconnectAndCleanUp(){
        if (mPubNubDataStream != null) {
            mPubNubDataStream.unsubscribe().channels(PUBSUB_CHANNEL).execute()
            mPubNubDataStream.removeListener(this)
            mPubNubDataStream.removeListener(this)
            mPubNubDataStream.stop()
        }
    }

}