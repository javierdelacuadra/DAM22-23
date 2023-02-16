package servicios

import dao.DaoConductores
import servicios.modelo.Conductor

class ServiciosConductores {
    private val dao = DaoConductores()

    fun getAllConductores(): List<Conductor> {
        return dao.getAllConductores()
    }

    fun agregarConductor(conductor: Conductor) {
        dao.agregarConductor(conductor)
    }

    fun actualizarConductor(conductor: Conductor) {
        dao.actualizarConductor(conductor)
    }

    fun eliminarConductor(id: String) {
        dao.eliminarConductor(id)
    }
}