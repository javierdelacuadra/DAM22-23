package servicios

import dao.DaoCamiones
import servicios.modelo.Camion

class ServiciosCamiones {

    private val dao = DaoCamiones()

    suspend fun getAllCamiones(): List<Camion> {
        return dao.getAllCamiones()
    }

    suspend fun agregarCamion(camion: Camion) {
        dao.agregarCamion(camion)
    }

    suspend fun actualizarCamion(camion: Camion) {
        dao.actualizarCamion(camion)
    }

    suspend fun eliminarCamion(id: Int) {
        dao.eliminarCamion(id)
    }
}