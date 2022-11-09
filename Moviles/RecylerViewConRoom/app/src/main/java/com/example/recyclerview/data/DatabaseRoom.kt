package com.example.recyclerview.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recyclerview.data.modelo.PersonaEntity

@Database(entities = [PersonaEntity::class], version =1, exportSchema = true)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun daoPersonas(): DaoPersonas

    companion object {
        @Volatile
        private var INSTANCE: DatabaseRoom? = null

        fun getDatabase(context: Context): DatabaseRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRoom::class.java,
                    "item_database"
                )
                    .createFromAsset("database/personas.db")
                    //.fallbackToDestructiveMigrationFrom(4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}