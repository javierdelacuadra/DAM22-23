package dao

import com.apollographql.apollo3.ApolloClient
import server.AddConductorMutation
import server.DeleteConductorMutation
import server.GetAllConductoresQuery
import server.UpdateConductorMutation
import servicios.modelo.Camion
import servicios.modelo.Conductor

class DaoConductores {

    suspend fun getAllConductores(): List<Conductor> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val query = GetAllConductoresQuery()

        val response = apolloClient.query(query).execute()
        var conductoresList = emptyList<Conductor>()
        if (!response.hasErrors()) {
            val conductores = response.data?.getAllConductores?.map {
                it.camion?.let { it1 ->
                    Camion(
                        id = it1.id,
                        modelo = it.camion.modelo,
                        fechaConstruccion = it.camion.fechaConstruccion
                    )
                }?.let { it2 ->
                    Conductor(
                        id = it.id,
                        nombre = it.nombre,
                        telefono = it.telefono,
                        camion = it2
                    )
                }
            } ?: emptyList()
            conductoresList = conductores.filterNotNull()
        } else {

        }
        return conductoresList
    }

    suspend fun agregarConductor(conductor: Conductor): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = AddConductorMutation(conductor.nombre, conductor.telefono, conductor.camion.id)

        val response = apolloClient.mutation(mutation).execute()

        return if (!response.hasErrors()) {
            response.data?.createConductor?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun actualizarConductor(conductor: Conductor) : String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = UpdateConductorMutation(conductor.id, conductor.nombre, conductor.telefono)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.updateConductor?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun eliminarConductor(id: Int) : String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = DeleteConductorMutation(id)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            "Conductor eliminado"
        } else {
            "Error"
        }
    }
}