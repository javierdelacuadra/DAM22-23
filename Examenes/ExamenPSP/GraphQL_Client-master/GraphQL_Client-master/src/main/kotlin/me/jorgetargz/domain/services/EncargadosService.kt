package me.jorgetargz.domain.services

import kotlinx.coroutines.flow.Flow
import me.jorgetargz.domain.modelo.Encargado
import me.jorgetargz.utils.NetworkResult

interface EncargadosService {

    fun getEncargadoByParadaId(paradaId: Int): Flow<NetworkResult<Encargado>>

    fun createEncargado(encargado: Encargado): Flow<NetworkResult<Encargado>>

    fun updateEncargado(encargado: Encargado): Flow<NetworkResult<Encargado>>

    fun deleteEncargado(encargado: Encargado): Flow<NetworkResult<Encargado>>
}