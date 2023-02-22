package dao

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import server.AddCamionMutation
import server.DeleteCamionMutation
import server.GetAllCamionesQuery
import server.UpdateCamionMutation
import servicios.modelo.Camion


class DaoCamiones {
    suspend fun getAllCamiones(): List<Camion> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val getAllCamionesQuery = GetAllCamionesQuery()

        val response = apolloClient.query(getAllCamionesQuery).execute()
        var camionesList = emptyList<Camion>()
        if (!response.hasErrors()) {
            val camiones = response.data?.getAllCamiones?.map {
                it?.let { it1 ->
                    Camion(
                        id = it1.id,
                        modelo = it.modelo,
                        fechaConstruccion = it.fechaConstruccion
                    )
                }
            } ?: emptyList()
            camionesList = camiones.filterNotNull()
        } else {

        }

        return camionesList
    }

    suspend fun agregarCamion(camion: Camion): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = AddCamionMutation(camion.modelo, camion.fechaConstruccion, camion.id)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.createCamion?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun actualizarCamion(camion: Camion) : String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = UpdateCamionMutation(camion.id, Optional.present(camion.modelo), Optional.present(camion.fechaConstruccion))

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.updateCamion?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun eliminarCamion(id: Int) : String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
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