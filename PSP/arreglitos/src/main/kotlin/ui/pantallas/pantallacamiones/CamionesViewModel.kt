package ui.pantallas.pantallacamiones

import servicios.ServiciosCamiones
import servicios.modelo.Camion

class CamionesViewModel {
    private val servicios = ServiciosCamiones()

    suspend fun getAllCamiones(): List<Camion> {
        return servicios.getAllCamiones()
    }

    suspend fun agregarCamion(camion: Camion) {
        servicios.agregarCamion(camion)
    }

    suspend fun actualizarCamion(camion: Camion) {
        servicios.actualizarCamion(camion)
    }

    suspend fun eliminarCamion(id: Int) {
        servicios.eliminarCamion(id)
    }
}