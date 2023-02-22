package domain.servicios

import credentials.CachedCredentials
import dao.MascotaDao
import domain.modelo.Mascota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.javafx.JavaFx
import security.Encriptacion
import security.impl.EncriptacionAES
import utils.Trither

object ServiciosMascota {
    private val dao: MascotaDao = MascotaDao

    private val ca: CachedCredentials = CachedCredentials

    private val encriptacion: Encriptacion = EncriptacionAES

    fun addMascota(mascota: Mascota): kotlinx.coroutines.flow.Flow<Trither<Mascota>> {
        val mascotaCopia = mascota.copy(name = encriptacion.encriptar(mascota.name, ca.password!!) ?: mascota.name)
        return flow {
            dao.createMascota(mascotaCopia).collect {
                if (it is Trither.Success) {
                    it.data?.name = mascota.name
                }
                emit(it)
            }
        }.flowOn(Dispatchers.JavaFx)
    }


    fun updateMascota(mascota: Mascota): kotlinx.coroutines.flow.Flow<Trither<Mascota>> {
        val mascotaCopia = mascota.copy(name = encriptacion.encriptar(mascota.name, ca.password!!) ?: mascota.name)
        return flow {
            dao.updateMascota(mascotaCopia).collect {
                if (it is Trither.Success) {
                    it.data?.name = mascota.name
                }
                emit(it)
            }
        }.flowOn(Dispatchers.JavaFx)
    }

    fun deleteMascota(id: Int) =
        dao.deleteMascota(id)


    fun cargarMascotasUserLogged() : kotlinx.coroutines.flow.Flow<Trither<List<Mascota>>> {
        return flow {
             dao.cargarMascotasUserLogged()
            .collect{ trither ->
                if (trither is Trither.Success){
                    val mascotas = trither.data
                    mascotas?.forEach { mascota ->
                        mascota.name = encriptacion.desencriptar(mascota.name, ca.password!!) ?: mascota.name
                    }
                    emit(Trither.Success(mascotas?:emptyList()))
                }
                else{
                    emit(trither)
                }
            }
        }.flowOn(Dispatchers.JavaFx)
    }

}