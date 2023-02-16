package servicios.modelo

data class Empresa(
    val id: Int,
    val nombre: String,
    val direccion: String,
    val camiones: List<Camion>
)