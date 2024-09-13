package sv.edu.udb.farmacio_desafio02

import java.io.Serializable

data class Medicine(
    val name: String,
    val description: String, // Nueva propiedad para descripción
    val price: Double
) : Serializable {
    override fun toString(): String {
        return "$name - $description - $${price}" // Mostrar la descripción en el string
    }
}
