package domain.modelo

data class Mascota(var id: Int = 0, var name: String = "", val age: Int = 0, val type: String = "", val persona: Persona = Persona()){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Mascota

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}