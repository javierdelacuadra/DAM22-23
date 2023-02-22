package domain.modelo

data class Persona(val id: Int = 0, val name: String = "", val surname: String = "", val login: Login = Login(), val mascotas: List<Mascota> = listOf())