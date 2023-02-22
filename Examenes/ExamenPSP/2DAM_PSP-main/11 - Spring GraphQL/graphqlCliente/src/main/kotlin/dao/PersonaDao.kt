package dao

import dao.apollo.response.BaseApiResponse
import domain.modelo.Login
import domain.modelo.Mascota
import domain.modelo.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.javafx.JavaFx
import okhttp3.Credentials
import org.example.localhost.*
import utils.Trither

object PersonaDao : BaseApiResponse() {
    fun cargarPersonas(): Flow<Trither<List<Persona>>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.query(AllPersonsFullQuery())
                        .execute()
                }, transform =
                { data ->
                    data.allPersons.map { persona ->
                        persona.let {
                            Persona(
                                id = it?.id?:0,
                                name = it?.name ?: "",
                                surname = it?.surname ?: "",
                                mascotas = it?.mascotas?.map { Mascota(persona = Persona()) } ?: emptyList()
                            )
                        }
                    }
                })
            emit(result)


        }.flowOn(Dispatchers.JavaFx)
    }

    fun tryLogin(username: String, password: String): Flow<Trither<Persona>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.query(TryLoginQuery())
                        .addHttpHeader("Authorization", Credentials.basic(username, password))
                        .execute()
                }, transform =
                { data ->
                    data.getPersonaByUsernameLogged.let { persona ->
                        persona.let {
                            Persona(
                                id = it.id,
                                name = it.name,
                                surname = it.surname,
                                login = Login(role = it.login.role)
                            )
                        }
                    }
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun register(name: String, surname: String, username: String, password: String): Flow<Trither<Persona>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.mutation(
                        RegisterMutation(
                            name = name,
                            surname = surname,
                            username = username,
                            password = password
                        )
                    )
                        .execute()
                }, transform =
                { data ->
                    Persona(
                        id = data.createPersona.id
                    )
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun updatePersonaLogged(name: String, surname: String): Flow<Trither<Persona>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.mutation(UpdateUserLoggedMutation(name = name, surname = surname))
                        .execute()
                }, transform =
                { data ->
                    Persona(
                        id = data.updatePersonaLogueada.id,
                        name = data.updatePersonaLogueada.name,
                        surname = data.updatePersonaLogueada.surname
                    )
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun deletePersonaLogged(): Flow<Trither<Persona>> {
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.mutation(DeleteUserLoggedMutation())
                        .execute()
                }, transform =
                { data ->
                    Persona(
                        name = data.deletePersonaLogueada.name,
                    )
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun deletePersona(id: Int): Flow<Trither<Persona>>{
        return flow {
            emit(value = Trither.Loading())
            val result =
                safeApiCall(apiCall = {
                    apolloClient.mutation(DeletePersonaMutation(id = id))
                        .execute()
                }, transform =
                { data ->
                    Persona(
                        id = data.deletePersona.id,
                        name = data.deletePersona.name,
                        surname = data.deletePersona.surname
                    )
                })
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }
}