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
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val query = GetAllEmpresasQuery()

        val response = apolloClient.query(query).execute()
        var empresasList = emptyList<Empresa>()
        if (!response.hasErrors()) {
            val empresas = response.data?.getAllEmpresas?.map {
                it.camiones?.let { it1 ->
                    it1.map { camion ->
                        camion?.let { it2 ->
                            Camion(
                                id = it2.id,
                                modelo = camion.modelo,
                                fechaConstruccion = camion.fechaConstruccion
                            )
                        }
                    }.let { it3 ->
                        Empresa(
                            id = it.id,
                            nombre = it.nombre,
                            direccion = it.direccion,
                            camiones = it3
                        )
                    }
                }
            } ?: emptyList()
            empresasList = empresas.filterNotNull()
        } else {

        }
        return empresasList
    }

    suspend fun agregarEmpresa(empresa: Empresa): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = AddEmpresaMutation(empresa.nombre, empresa.direccion)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.createEmpresa?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun actualizarEmpresa(empresa: Empresa): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
            .build()

        val mutation = UpdateEmpresaMutation(empresa.id, empresa.nombre, empresa.direccion)

        val response = apolloClient.mutation(mutation).execute()
        return if (!response.hasErrors()) {
            response.data?.updateEmpresa?.id.toString()
        } else {
            "Error"
        }
    }

    suspend fun eliminarEmpresa(id: Int): String {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/graphql")
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