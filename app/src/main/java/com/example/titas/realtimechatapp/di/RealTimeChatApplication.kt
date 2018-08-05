package com.example.titas.realtimechatapp.di

import android.app.Activity
import android.app.Application
import com.example.titas.realtimechatapp.di.component.AppComponent
import com.example.titas.realtimechatapp.di.component.DaggerAppComponent
import com.example.titas.realtimechatapp.di.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Titas on 8/5/2018.
 */
class RealTimeChatApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
    }
}