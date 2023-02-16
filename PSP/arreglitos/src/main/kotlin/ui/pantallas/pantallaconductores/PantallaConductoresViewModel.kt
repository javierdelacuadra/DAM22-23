package ui.pantallas.pantallaconductores

import servicios.ServiciosConductores
import servicios.modelo.Conductor

class PantallaConductoresViewModel {
    private val servicios = ServiciosConductores()

    fun getAllConductores(): List<Conductor> {
        return servicios.getAllConductores()
    }

    fun agregarConductor(conductor: Conductor) {
        servicios.agregarConductor(conductor)
    }

    fun actualizarConductor(conductor: Conductor) {
        servicios.actualizarConductor(conductor)
    }

    fun eliminarConductor(id: String) {
        servicios.eliminarConductor(id)
    }
}