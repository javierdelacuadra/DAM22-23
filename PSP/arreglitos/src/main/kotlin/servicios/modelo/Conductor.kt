package servicios.modelo

data class Conductor(
    val id: Int,
    val nombre: String,
    val telefono: String,
    val camion: Camion,
)