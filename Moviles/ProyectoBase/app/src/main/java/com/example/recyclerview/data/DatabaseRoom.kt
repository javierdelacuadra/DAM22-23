package com.example.recyclerview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerview.data.modelo.ComponenteEntity
import com.example.recyclerview.data.modelo.EquipoEntity

@Database(entities = [EquipoEntity::class, ComponenteEntity::class], version = 2, exportSchema = true)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun daoEquipos(): DaoEquipos

}