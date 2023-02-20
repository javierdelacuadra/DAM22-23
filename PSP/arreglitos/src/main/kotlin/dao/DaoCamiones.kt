package dao

import com.apollographql.apollo3.ApolloClient
import server.AddCamionMutation
import server.DeleteCamionMutation
import server.GetAllCamionesQuery
import server.UpdateCamionMutation
import servicios.modelo.Camion


class DaoCamiones {
    suspend fun getAllCamiones(): List<Camion> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val getAllCamionesQuery = GetAllCamionesQuery()

        val response = apolloClient.query(getAllCamionesQuery).execute()
        var camionesList = emptyList<Camion>()
        if (!response.hasErrors()) {
            val camiones = response.data?.getallcamiones?.map {
                Camion(
                    id = it.id,
                    modelo = it.modelo,
                    fechaConstruccion = it.fechaConstruccion
                )
            } ?: emptyList()
            camionesList = camiones
        } else {

        }

        return camionesList
    }

    suspend fun agregarCamion(camion: Camion): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val mutation = AddCamionMutation(camion.modelo, camion.fechaConstruccion)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.crearCamion?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun actualizarCamion(camion: Camion) : String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val mutation = UpdateCamionMutation(camion.id, camion.modelo, camion.fechaConstruccion)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.actualizarCamion?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun eliminarCamion(id: Int) : String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val mutation = DeleteCamionMutation(id)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            "Camion eliminado"
        } else {
            "Error"
        }
    }
}