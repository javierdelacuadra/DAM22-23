package com.example.formulariocompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.formulariocompose.data.modelo.PersonaEntity

@Database(entities = [PersonaEntity::class], version = 1, exportSchema = true)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun daoPersonas(): DaoPersonas
}