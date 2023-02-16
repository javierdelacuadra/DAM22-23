package dao

import com.apollographql.apollo3.ApolloClient
import server.AddCamionMutation
import server.GetAllCamionesQuery
import server.UpdateCamionMutation
import servicios.modelo.Camion

class DaoCamiones {
    fun getAllCamiones(): List<Camion> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = GetAllCamionesQuery.builder().build()

        val response = apolloClient.query(query).execute()
        val camiones = response.data()?.camiones()?.map {
            Camion(it.id(), it.modelo(), it.fechaFabricacion())
        } ?: emptyList()
        return camiones
    }

    fun agregarCamion(camion: Camion) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = AddCamionMutation.builder().modelo(camion.modelo).fechaFabricacion(camion.fechaConstruccion).build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.addCamion()?.id())
    }

    fun actualizarCamion(camion: Camion) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = UpdateCamionMutation.builder().id(camion.id).modelo(camion.modelo).fechaFabricacion(camion.fechaConstruccion).build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.updateCamion()?.id())
    }

    fun eliminarCamion(id: String) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = DeleteCamion.builder().id(id).build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.deleteCamion()?.id())
    }
}