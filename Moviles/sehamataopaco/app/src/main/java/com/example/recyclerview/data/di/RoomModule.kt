package com.example.recyclerview.data.di

import android.content.Context
import androidx.room.Room
import com.example.recyclerview.data.*
import com.example.recyclerview.data.common.Constantes
import com.example.recyclerview.data.dao.*
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
    @Named(Constantes.ASSET_DB)
    fun getAssetDB(): String = Constantes.DATABASE_PATH


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named(Constantes.ASSET_DB) ruta: String
    ): DatabaseRoom =
        Room.databaseBuilder(context, DatabaseRoom::class.java, Constantes.ITEM_DATABASE)
            .createFromAsset(ruta)
            .fallbackToDestructiveMigrationFrom(1)
            .build()

    @Provides
    fun providesDoctorDao(personasDatabase: DatabaseRoom): DaoDoctores =
        personasDatabase.daoDoctores()

    @Provides
    fun providesHospitalDao(personasDatabase: DatabaseRoom): DaoHospitales =
        personasDatabase.daoHospitales()

    @Provides
    fun providesCitaDao(personasDatabase: DatabaseRoom): DaoCitas =
        personasDatabase.daoCitas()

    @Provides
    fun providesUsuarioDao(personasDatabase: DatabaseRoom): DaoUsuarios =
        personasDatabase.daoUsuarios()

    @Provides
    fun providesPersonaDao(personasDatabase: DatabaseRoom): DaoPersonas =
        personasDatabase.daoPersonas()
}