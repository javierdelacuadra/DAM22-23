package ui.pantallas.pantallaempresas

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import servicios.ServiciosEmpresas
import servicios.modelo.Empresa

class PantallaEmpresasViewModel {

    private val servicios = ServiciosEmpresas()

    fun getAllEmpresas(): ObservableList<Empresa> {
        return FXCollections.observableArrayList(servicios.getAllEmpresas())
    }

    fun agregarEmpresa(empresa: Empresa) {
        servicios.agregarEmpresa(empresa)
    }

    fun actualizarEmpresa(empresa: Empresa) {
        servicios.actualizarEmpresa(empresa)
    }

    fun eliminarEmpresa(id: String) {
        servicios.eliminarEmpresa(id)
    }
}