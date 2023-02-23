package servicios.modelo

data class Alumno(
    val id: Int?,
    val nombre: String,
    val asignaturas: List<Asignatura?>?
)