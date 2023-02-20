package ui.pantallas.pantallaconductores

import servicios.ServiciosConductores
import servicios.modelo.Conductor

class PantallaConductoresViewModel {
    private val servicios = ServiciosConductores()

    suspend fun getAllConductores(): List<Conductor> {
        return servicios.getAllConductores()
    }

    suspend fun agregarConductor(conductor: Conductor) {
        servicios.agregarConductor(conductor)
    }

    suspend fun actualizarConductor(conductor: Conductor) {
        servicios.actualizarConductor(conductor)
    }

    suspend fun eliminarConductor(id: Int) {
        servicios.eliminarConductor(id)
    }
}