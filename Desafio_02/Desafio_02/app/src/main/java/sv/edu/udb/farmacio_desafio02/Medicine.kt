package sv.edu.udb.farmacio_desafio02

import java.io.Serializable

data class Medicine(val name: String, val price: Double) : Serializable {
    override fun toString(): String {
        return "$name - $${price}"
    }
}
