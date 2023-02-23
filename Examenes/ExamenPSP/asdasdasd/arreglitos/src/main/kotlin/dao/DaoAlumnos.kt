package dao

import com.apollographql.apollo3.ApolloClient
import server.AddAlumnoMutation
import server.GetAllAlumnosQuery
import server.GetAllNombresAlumnosQuery
import servicios.modelo.Alumno
import servicios.modelo.Asignatura

class DaoAlumnos {
    suspend fun getAllAlumnos(): List<Alumno> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val query = GetAllAlumnosQuery()

        val response = apolloClient.query(query).execute()
        val alumnosList: List<Alumno>
        if (!response.hasErrors()) {
            val alumnos = response.data?.alumnos?.map {
                it?.let { it1 ->
                    Alumno(
                        id = it1.id,
                        nombre = it.nombre,
                        asignaturas = it.asignaturas?.map { it2 ->
                            it2?.let { it3 ->
                                Asignatura(
                                    id = it3.id,
                                    nombre = it3.nombre,
                                    nota = it3.nota!!
                                )
                            }
                        } ?: emptyList()
                    )
                }
            } ?: emptyList()
            alumnosList = alumnos.filterNotNull()
        } else {
            return emptyList()
        }
        return alumnosList
    }

    suspend fun getNombresAlumnos(): List<Alumno> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val query = GetAllNombresAlumnosQuery()

        val response = apolloClient.query(query).execute()
        val alumnosList: List<Alumno>
        if (!response.hasErrors()) {
            val alumnos = response.data?.alumnos?.map {
                it?.let { _ ->
                    Alumno(
                        id = null,
                        nombre = it.nombre,
                        asignaturas = null
                    )
                }
            } ?: emptyList()
            alumnosList = alumnos.filterNotNull()
        } else {
            return emptyList()
        }

        return alumnosList
    }

    suspend fun addAlumno(alumno: Alumno): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = AddAlumnoMutation(alumno.nombre)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.addAlumno?.id.toString()
        } else {
            "Error"
        }
    }
}