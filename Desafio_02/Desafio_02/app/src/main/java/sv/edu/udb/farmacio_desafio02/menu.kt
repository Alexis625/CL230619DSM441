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
        Producto("1", "Paracetamol", 1.50),
        Producto("2", "Ibuprofeno", 3.00),
        Producto("3", "Aspirina", 2.50),
        Producto("4", "Jarabe para la tos", 4.00),
        Producto("5", "Vitamina C", 3.50),
        Producto("6", "Jarabe para la Tos", 6.00),
        Producto("7", "Omeprazol", 8.00),
        Producto("8", "Alcohol Gel", 3.00),
        Producto("9", "Gotas para los ojos", 7.00),
        Producto("10", "Anticonceptivos", 15.00)
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
