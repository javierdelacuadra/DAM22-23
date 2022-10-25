package com.example.recyclerview.domain.modelo

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Personaje(
    @Json(name = "nombre")
    val nombre : String,
    @Json(name = "rareza")
    val rareza : Int,
    @Json(name = "precio")
    val precio : Int) : Parcelable