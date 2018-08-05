package com.example.titas.realtimechatapp.di.modules

import com.example.titas.realtimechatapp.repository.service.chat.MessagingService
import com.example.titas.realtimechatapp.repository.service.chat.PubNubMessagingService
import dagger.Binds
import dagger.Module

/**
 * Created by Titas on 8/5/2018.
 */
@Module
abstract class MessagingServiceModule {
    @Binds
    abstract fun provideMessagingService(messagingService: PubNubMessagingService): MessagingService
}