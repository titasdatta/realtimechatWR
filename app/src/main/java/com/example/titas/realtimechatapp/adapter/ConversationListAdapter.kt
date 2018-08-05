package com.example.titas.realtimechatapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.titas.realtimechatapp.R
import com.example.titas.realtimechatapp.repository.model.Message
import kotlinx.android.synthetic.main.incoming_msg.view.*
import java.text.SimpleDateFormat
import java.util.logging.Handler


/**
 * Created by Titas on 8/5/2018.
 */
class ConversationListAdapter constructor(private var conversationList: List<Message>,
                                          private val memberId: String):
        RecyclerView.Adapter<ConversationListAdapter.ConversationViewHolder>() {

    var mRecyclerView: RecyclerView? = null

    override fun onBindViewHolder(holder: ConversationViewHolder?, position: Int) {
        holder?.let {
            it.bind(conversationList[position])
        }
    }

    override fun getItemCount(): Int = conversationList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ConversationViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ConversationViewHolder(inflater.inflate(viewType, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        val message = conversationList[position]
        if(message.senderName.equals(memberId)){
            message.isIncoming = false
            return R.layout.outgoing_msg
        } else {
            message.isIncoming = true
            return R.layout.incoming_msg
        }
    }

    class ConversationViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(message: Message){
            if(message.isIncoming){
                view.sender_name.text = message.senderName
            }
            view.message_text.text = message.messageText
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").parse(message.receivedTimeStamp)
            view.receivedTime.text = SimpleDateFormat("hh:mm a").format(date)
        }
    }

    fun updateConversationList(messageList: List<Message>){
        this.conversationList = messageList
        //TODO: Needs to optimized by using notifyItemInserted
        notifyDataSetChanged()
        android.os.Handler().postDelayed(Runnable {
            mRecyclerView?.smoothScrollToPosition(itemCount)
        }, 500)

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }
}