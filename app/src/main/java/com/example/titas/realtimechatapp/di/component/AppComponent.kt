package com.example.titas.realtimechatapp.di.component

import android.app.Activity
import android.app.Application
import com.example.titas.realtimechatapp.di.modules.AppModule
import com.example.titas.realtimechatapp.di.modules.MessagingServiceModule
import com.example.titas.realtimechatapp.di.modules.ViewModelModule
import com.example.titas.realtimechatapp.view.ConversationFragment
import com.example.titas.realtimechatapp.view.MainActivity
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Titas on 8/5/2018.
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, MessagingServiceModule::class,
        ViewModelModule::class))
interface AppComponent {
    fun inject(app: Application)
    fun inject(activity: MainActivity)
    fun inject(fragment: ConversationFragment)
}