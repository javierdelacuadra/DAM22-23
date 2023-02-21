package com.example.examenxml.data.di

import android.content.Context
import androidx.room.Room
import com.example.examenxml.data.DatabaseRoom
import com.example.examenxml.data.dao.PacientesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Named("assetDB")
    fun getAssetDB(): String = "database/personas.db"


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named("assetDB") ruta: String
    ): DatabaseRoom =
        Room.databaseBuilder(context, DatabaseRoom::class.java, "item_database")
            .createFromAsset(ruta)
            .fallbackToDestructiveMigrationFrom(1)
            .build()

    @Provides
    fun providesPacientesDao(personasDatabase: DatabaseRoom): PacientesDao =
        personasDatabase.daoPacientes()
}