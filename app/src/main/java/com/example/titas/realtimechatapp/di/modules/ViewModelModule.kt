package com.example.titas.realtimechatapp.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.titas.realtimechatapp.di.ViewModelKey
import com.example.titas.realtimechatapp.viewmodel.ConversationViewModel
import com.example.titas.realtimechatapp.viewmodel.CustomViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Titas on 8/5/2018.
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ConversationViewModel::class)
    internal abstract fun bindConversationViewModel(conversationViewModel: ConversationViewModel): ViewModel

    @Binds
    internal abstract fun bindConversationViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}