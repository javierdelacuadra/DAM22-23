package servicios

import dao.DaoEmpresas
import servicios.modelo.Empresa

class ServiciosEmpresas {

    private val dao = DaoEmpresas()

    fun getAllEmpresas(): List<Empresa> {
        return dao.getAllEmpresas()
    }

    fun agregarEmpresa(empresa: Empresa) {
        dao.agregarEmpresa(empresa)
    }

    fun actualizarEmpresa(empresa: Empresa) {
        dao.actualizarEmpresa(empresa)
    }

    fun eliminarEmpresa(id: String) {
        dao.eliminarEmpresa(id)
    }

}