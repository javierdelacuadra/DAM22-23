package dao

import com.apollographql.apollo3.ApolloClient
import server.AddConductorMutation
import server.GetAllConductoresQuery
import server.UpdateConductorMutation
import servicios.modelo.Conductor

class DaoConductores {

    fun getAllConductores(): List<Conductor> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = GetAllConductoresQuery.builder().build()

        val response = apolloClient.query(query).execute()
        val conductores = response.data()?.conductores()?.map {
            Conductor(it.id(), it.nombre(), it.telefono(), it.idCamion)
        } ?: emptyList()
        return conductores
    }

    fun agregarConductor(conductor: Conductor) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = AddConductorMutation.builder().nombre(conductor.nombre).telefono(conductor.telefono)
            .camion(conductor.camion).build()
        val response = apolloClient.mutate(query).execute()
        println(response.data()?.addConductor()?.id())
    }

    fun actualizarConductor(conductor: Conductor) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query =
            UpdateConductorMutation.builder().id(conductor.id).nombre(conductor.nombre).telefono(conductor.telefono)
                .camion(conductor.camion).build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.updateConductor()?.id())
    }

    fun eliminarConductor(id: String) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = DeleteConductorMutation.builder().id(id).build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.deleteConductor()?.id())
    }
}