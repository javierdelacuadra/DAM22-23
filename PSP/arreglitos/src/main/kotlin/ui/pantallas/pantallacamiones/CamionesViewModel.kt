package ui.pantallas.pantallacamiones

import servicios.ServiciosCamiones
import servicios.modelo.Camion

class CamionesViewModel {
    private val servicios = ServiciosCamiones()

    fun getAllCamiones(): List<Camion> {
        return servicios.getAllCamiones()
    }

    fun agregarCamion(camion: Camion) {
        servicios.agregarCamion(camion)
    }

    fun actualizarCamion(camion: Camion) {
        servicios.actualizarCamion(camion)
    }

    fun eliminarCamion(id: String) {
        servicios.eliminarCamion(id)
    }
}