package dao

import com.apollographql.apollo3.ApolloClient
import server.AddEmpresaMutation
import server.DeleteEmpresaMutation
import server.GetAllEmpresasQuery
import server.UpdateEmpresaMutation
import servicios.modelo.Camion
import servicios.modelo.Empresa

class DaoEmpresas {

    suspend fun getAllEmpresas(): List<Empresa> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = GetAllEmpresasQuery()

        val response = apolloClient.query(query).execute()
        var empresasList = emptyList<Empresa>()
        if (!response.hasErrors()) {
            val empresas = response.data?.getallempresas?.map {
                Empresa(
                    id = it.id,
                    nombre = it.nombre,
                    direccion = it.direccion,
                    camiones = it.camiones.map { camion ->
                        Camion(
                            id = camion.id,
                            modelo = camion.modelo,
                            fechaConstruccion = camion.fechaConstruccion
                        )
                    }
                )
            } ?: emptyList()
            empresasList = empresas
        } else {

        }
        return empresasList
    }

    suspend fun agregarEmpresa(empresa: Empresa): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val mutation = AddEmpresaMutation(empresa.nombre, empresa.direccion)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.crearEmpresa?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun actualizarEmpresa(empresa: Empresa): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val mutation = UpdateEmpresaMutation(empresa.id, empresa.nombre, empresa.direccion)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.actualizarEmpresa?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun eliminarEmpresa(id: Int): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val mutation = DeleteEmpresaMutation(id)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            "Empresa eliminada"
        } else {
            "Error"
        }
    }
}