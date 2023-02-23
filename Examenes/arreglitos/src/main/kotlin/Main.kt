import dao.DaoAlumnos
import kotlinx.coroutines.runBlocking
import servicios.modelo.Alumno

fun main(args: Array<String>) {
    val daoAlumnos = DaoAlumnos()

    runBlocking {
        val allAlumnos = daoAlumnos.getAllAlumnos()
        println("Todos los alumnos: $allAlumnos")

        val nombresAlumnos = daoAlumnos.getNombresAlumnos()
        println("Nombres de los alumnos: $nombresAlumnos")

        val alumnotoAdd = Alumno(id = null, nombre = "Alumno 1", asignaturas = null)
        val resultado = daoAlumnos.addAlumno(alumnotoAdd)
        println("ID del alumno añadido: $resultado")
    }
}