package servicios

import dao.DaoCamiones
import servicios.modelo.Camion

class ServiciosCamiones {

    private val dao = DaoCamiones()

    fun getAllCamiones(): List<Camion> {
        return dao.getAllCamiones()
    }

    fun agregarCamion(camion: Camion) {
        dao.agregarCamion(camion)
    }

    fun actualizarCamion(camion: Camion) {
        dao.actualizarCamion(camion)
    }

    fun eliminarCamion(id: String) {
        dao.eliminarCamion(id)
    }
}