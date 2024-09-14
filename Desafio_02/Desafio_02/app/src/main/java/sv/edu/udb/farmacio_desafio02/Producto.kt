package sv.edu.udb.farmacio_desafio02

import java.io.Serializable

// Clase modelo para representar un producto
data class Producto(
    val id: String,
    val name: String,
    val price: Double
) : Serializable
