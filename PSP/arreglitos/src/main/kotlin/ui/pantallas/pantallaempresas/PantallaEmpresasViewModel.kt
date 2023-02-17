package ui.pantallas.pantallaempresas

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import servicios.ServiciosEmpresas
import servicios.modelo.Empresa

class PantallaEmpresasViewModel {

    private val servicios = ServiciosEmpresas()

    suspend fun getAllEmpresas(): ObservableList<Empresa> {
        return FXCollections.observableArrayList(servicios.getAllEmpresas())
    }

    suspend fun agregarEmpresa(empresa: Empresa) {
        servicios.agregarEmpresa(empresa)
    }

    suspend fun actualizarEmpresa(empresa: Empresa) {
        servicios.actualizarEmpresa(empresa)
    }

    suspend fun eliminarEmpresa(id: Int) {
        servicios.eliminarEmpresa(id)
    }
}