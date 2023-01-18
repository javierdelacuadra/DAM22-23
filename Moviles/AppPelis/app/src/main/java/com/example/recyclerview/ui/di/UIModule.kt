package com.example.recyclerview.ui.di

import android.content.Context
import com.example.recyclerview.utils.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UIModule {
    @Provides
    fun provideStringProvider(@ApplicationContext context: Context) =
        StringProvider.instance(context)
}