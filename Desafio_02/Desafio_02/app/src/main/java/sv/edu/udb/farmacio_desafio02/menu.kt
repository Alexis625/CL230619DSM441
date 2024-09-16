package sv.edu.udb.farmacio_desafio02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class menu : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductoAdaptador
    private lateinit var viewCartButton: Button
    private lateinit var historialButton: Button
    private val products = listOf(
        Producto("1", "Ultrafoskrol solucion oral", 6.88),
        Producto("2", "Vita C+Zinc X 12 tabletas masticables", 2.63),
        Producto("3", "Neuro Campolon Energy", 13.25),
        Producto("4", "Griin Skin Hair & Nails", 13.95),
        Producto("5", "Aceite bebe noches tranquilas Jaloma", 2.15),
        Producto("6", "Agua Oxigenada - Oxigena Solucion Topica 120ml", 2.00),
        Producto("7", "Algodon 1 Libra MIGASA", 6.00),
        Producto("8", "Alcohol Etilico 70° Gamma 240ml", 4.25),
        Producto("9", "Acetaminofen 500mg Ecomed X 10 Tabletas", 0.60),
        Producto("10", "Ambroxol 15mg/5ml Glicitos jarabe", 5.32)
    )
    private val selectedProducts = arrayListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        recyclerView = findViewById(R.id.recyclerView)
        viewCartButton = findViewById(R.id.viewCartButton)
        historialButton = findViewById(R.id.historial) // Asegúrate de inicializar el botón

        recyclerView.layoutManager = LinearLayoutManager(this)

        productAdapter = ProductoAdaptador(products) { producto ->
            onProductSelected(producto)
        }
        recyclerView.adapter = productAdapter

        viewCartButton.setOnClickListener {
            goToCart()
        }

        historialButton.setOnClickListener {
            val intent = Intent(this, Historial::class.java)
            startActivity(intent)
        }
    }

    private fun onProductSelected(product: Producto) {
        if (!selectedProducts.contains(product)) {
            selectedProducts.add(product)
        }
        Toast.makeText(this, "${product.name} agregado al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun goToCart() {
        val intent = Intent(this, Inicio::class.java)
        intent.putExtra("cartProducts", ArrayList(selectedProducts)) // Pasar productos seleccionados
        startActivity(intent)
    }
}
