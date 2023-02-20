package servicios

import dao.DaoEmpresas
import servicios.modelo.Empresa

class ServiciosEmpresas {

    private val dao = DaoEmpresas()

    suspend fun getAllEmpresas(): List<Empresa> {
        return dao.getAllEmpresas()
    }

    suspend fun agregarEmpresa(empresa: Empresa) {
        dao.agregarEmpresa(empresa)
    }

    suspend fun actualizarEmpresa(empresa: Empresa) {
        dao.actualizarEmpresa(empresa)
    }

    suspend fun eliminarEmpresa(id: Int) {
        dao.eliminarEmpresa(id)
    }

}