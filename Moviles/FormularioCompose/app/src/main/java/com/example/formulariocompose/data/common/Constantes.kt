package com.example.formulariocompose.data.common

object Constantes {
    const val TABLE_NAME = "personas"
    const val NOMBRE = "nombre"
    const val PASSWORD = "password"
    const val EMAIL = "email"
    const val SELECT_PERSONAS = "SELECT * FROM $TABLE_NAME"
    const val SELECT_PERSONA = "SELECT * FROM $TABLE_NAME WHERE $EMAIL = :email"
    const val DATABASE_PATH = "database/personas.db"
    const val ITEM_DATABASE = "item_database"
    const val ASSET_DB = "assetDB"
}