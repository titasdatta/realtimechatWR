package com.example.titas.realtimechatapp.view

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.titas.realtimechatapp.R
import com.example.titas.realtimechatapp.common.PREFS_NAME
import com.example.titas.realtimechatapp.common.PREFS_USERNAME
import com.example.titas.realtimechatapp.di.RealTimeChatApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoginFragment.OnLoginFragmentInteractionListener{

    private val LOGIN_FRAGMENT_TAG = "loginFragment"
    private val CONVERSATION_FRAGMENT_TAG = "conversationFragment"

    private lateinit var mSharedPrefs: SharedPreferences
    var mUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RealTimeChatApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if(!mSharedPrefs.contains(PREFS_USERNAME)){
            showLoginScreen()
        } else {
            mUserId = mSharedPrefs.getString(PREFS_USERNAME, "")
            showConversationScreen(false)
        }
    }

    override fun onSignInButtonClicked(userName: String) {
        mSharedPrefs.edit().putString(PREFS_USERNAME, userName).commit()
        mUserId = userName
        showConversationScreen(true)
    }

    fun showLoginScreen(){
        supportFragmentManager.beginTransaction().add(R.id.container, LoginFragment.newInstance(), LOGIN_FRAGMENT_TAG).commit()
    }

    fun showConversationScreen(isReplace: Boolean){
        if(!isReplace) {
            supportFragmentManager.beginTransaction().add(R.id.container, ConversationFragment.newInstance(), CONVERSATION_FRAGMENT_TAG).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, ConversationFragment.newInstance(), CONVERSATION_FRAGMENT_TAG).commit()
        }
    }
}
