package com.example.recyclerview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerview.data.dao.DaoPeliculas
import com.example.recyclerview.data.modelo.PeliculaEntity

@Database(
    entities = [PeliculaEntity::class],
    version = 4, exportSchema = true
)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun daoPeliculas(): DaoPeliculas

}