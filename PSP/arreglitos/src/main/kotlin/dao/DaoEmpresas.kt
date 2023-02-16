package dao

import com.apollographql.apollo3.ApolloClient
import server.AddEmpresaMutation
import server.GetAllEmpresasQuery
import server.UpdateEmpresaMutation
import servicios.modelo.Empresa

class DaoEmpresas {

    fun getAllEmpresas(): List<Empresa> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = GetAllEmpresasQuery.builder().build()

        val response = apolloClient.query(query).execute()

        val empresas = response.data()?.empresas()?.map {
            Empresa(it.id(), it.nombre(), it.direccion(), it.camiones())
        } ?: emptyList()

        return empresas
    }

    fun agregarEmpresa(empresa: Empresa) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query =
            AddEmpresaMutation.builder().nombre(empresa.nombre).direccion(empresa.direccion).camiones(empresa.camiones)
                .build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.addEmpresa()?.id())
    }

    fun actualizarEmpresa(empresa: Empresa) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = UpdateEmpresaMutation.builder().id(empresa.id).nombre(empresa.nombre).direccion(empresa.direccion)
            .camiones(empresa.camiones).build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.updateEmpresa()?.id())
    }

    fun eliminarEmpresa(id: String) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://localhost:8080/graphql")
            .build()

        val query = DeleteEmpresaQuery.builder().id(id).build()

        val response = apolloClient.mutate(query).execute()
        println(response.data()?.deleteEmpresa()?.id())
    }
}