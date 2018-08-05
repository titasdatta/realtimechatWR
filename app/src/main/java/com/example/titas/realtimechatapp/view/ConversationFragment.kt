package com.example.titas.realtimechatapp.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.titas.realtimechatapp.R
import com.example.titas.realtimechatapp.adapter.ConversationListAdapter
import com.example.titas.realtimechatapp.di.RealTimeChatApplication
import com.example.titas.realtimechatapp.repository.model.Message
import com.example.titas.realtimechatapp.viewmodel.ConversationViewModel
import kotlinx.android.synthetic.main.fragment_conversation.*
import javax.inject.Inject



class ConversationFragment : Fragment() {

    @Inject lateinit var conversationViewModelFactory: ViewModelProvider.Factory
    private lateinit var conversationViewModel: ConversationViewModel
    private var conversationAdapter: ConversationListAdapter? = null
    private lateinit var conversationLiveData: LiveData<List<Message>>


    override fun onCreate(savedInstanceState: Bundle?) {
        (activity.application as RealTimeChatApplication).component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        conversationViewModel = ViewModelProviders.of(this, conversationViewModelFactory)[ConversationViewModel::class.java]
        conversationLiveData = conversationViewModel.getConversationListObservable()
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_conversation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.stackFromEnd = true
        conversation_list.layoutManager = layoutManager
        conversation_list.itemAnimator = DefaultItemAnimator()

        conversation_list.addOnLayoutChangeListener(View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if(conversation_list.adapter != null && conversation_list.adapter.itemCount > 0)
                conversation_list.scrollToPosition(conversation_list.adapter.itemCount - 1) })

        send_message.setOnClickListener {
            if(msg_editText.text.isNotEmpty()){
                conversationViewModel.sendMessage(msg_editText.text.toString(), (activity as MainActivity).mUserId!!)
                msg_editText.setText("")
            }
        }

        conversationViewModel.getHistoryConversationObservable().observe(this, object : Observer<List<Message>>{
            override fun onChanged(t: List<Message>?) {
                if(t != null && t.isNotEmpty()) {
                    updateConversationList(t)
                    conversationViewModel.getHistoryConversationObservable().removeObservers(this@ConversationFragment)
                }
            }

        })

        conversationLiveData.observe(this, object : Observer<List<Message>>{
            override fun onChanged(t: List<Message>?) {
                if(t != null && t.isNotEmpty()) {
                    updateConversationList(t)
                }
            }

        })
    }

    private fun updateConversationList(list: List<Message>){
        if(conversationAdapter == null){
            conversationAdapter = ConversationListAdapter(list, (activity as MainActivity).mUserId!!)
            conversation_list.adapter = conversationAdapter
        } else {
            conversationAdapter?.updateConversationList(list)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(): ConversationFragment {
            val fragment = ConversationFragment()
            return fragment
        }
    }
}// Required empty public constructor
