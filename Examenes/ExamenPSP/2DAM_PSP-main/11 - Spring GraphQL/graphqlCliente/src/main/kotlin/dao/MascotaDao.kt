package dao

import dao.apollo.response.BaseApiResponse
import domain.modelo.Mascota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.javafx.JavaFx
import org.example.localhost.AllMascotasUserLoggedQuery
import org.example.localhost.CreateMascotaMutation
import org.example.localhost.DeleteMascotaMutation
import org.example.localhost.UpdateMascotaMutation
import utils.Trither

object MascotaDao: BaseApiResponse() {
    fun cargarMascotasUserLogged(): Flow<Trither<List<Mascota>>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.query(AllMascotasUserLoggedQuery())
                        .execute()
                }, transform =
                { data ->
                    data.allMascotasUserLogged.map { mascota ->
                        mascota.let {
                            Mascota(
                                id = it?.id?:0,
                                name = it?.name ?: "",
                                age = it?.age ?: 0,
                                type = it?.type ?: "",
                            )
                        }
                    }
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun createMascota(mascota: Mascota): Flow<Trither<Mascota>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.mutation(CreateMascotaMutation(mascota.name, mascota.type, mascota.age, mascota.persona.id))
                        .execute()
                }, transform =
                { data ->
                    data.createMascota.let { mascota ->
                        mascota.let {
                            Mascota(
                                id = it.id,
                                name = it.name,
                                age = it.age,
                                type = it.type
                            )
                        }
                    }
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun updateMascota(mascota: Mascota): Flow<Trither<Mascota>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.mutation(UpdateMascotaMutation(mascota.id, mascota.name, mascota.type, mascota.age))
                        .execute()
                }, transform =
                { data ->
                    data.updateMascota.let { mascota ->
                        mascota.let {
                            Mascota(
                                id = it.id,
                                name = it.name,
                                age = it.age,
                                type = it.type
                            )
                        }
                    }
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun deleteMascota(idPetSelected: Int): Flow<Trither<Mascota>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.mutation(DeleteMascotaMutation(idPetSelected))
                        .execute()
                }, transform =
                { data ->
                    Mascota(
                        id = data.deleteMascota.id,
                        name = data.deleteMascota.name,
                    )
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }
}
