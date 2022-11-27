package com.example.recyclerview.ui.listtarjetas

import com.example.recyclerview.domain.modelo.Tarjeta
import com.example.recyclerview.ui.common.ConstantesUI

data class ListTarjetaState(
    val mensaje: String? = ConstantesUI.NADA,
    val lista: List<Tarjeta>?,
)