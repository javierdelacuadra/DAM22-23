package servicios

import dao.DaoConductores
import servicios.modelo.Conductor

class ServiciosConductores {
    private val dao = DaoConductores()

    suspend fun getAllConductores(): List<Conductor> {
        return dao.getAllConductores()
    }

    suspend fun agregarConductor(conductor: Conductor) {
        dao.agregarConductor(conductor)
    }

    suspend fun actualizarConductor(conductor: Conductor) {
        dao.actualizarConductor(conductor)
    }

    suspend fun eliminarConductor(id: Int) {
        dao.eliminarConductor(id)
    }
}