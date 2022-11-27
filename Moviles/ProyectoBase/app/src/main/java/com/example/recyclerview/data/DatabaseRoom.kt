package com.example.recyclerview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerview.data.modelo.PersonaEntity
import com.example.recyclerview.data.modelo.TarjetaEntity

@Database(entities = [PersonaEntity::class, TarjetaEntity::class], version = 1, exportSchema = true)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun daoPersonas(): DaoPersonas

}