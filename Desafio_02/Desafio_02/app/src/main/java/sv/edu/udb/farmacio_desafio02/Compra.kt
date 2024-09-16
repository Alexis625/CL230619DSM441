package sv.edu.udb.farmacio_desafio02

data class Compra(
    val productos: List<Producto>,
    val total: Double,
    val fecha: String
)
