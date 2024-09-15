package sv.edu.udb.farmacio_desafio02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AlertDialog

class Inicio : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var cartAdapter: InicioAdaptador
    private lateinit var databaseReference: DatabaseReference
    private var cartProducts: ArrayList<Producto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Inicializamos los elementos
        recyclerView = findViewById(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.totalTextView)
        val checkoutButton: Button = findViewById(R.id.checkoutButton)
        val backButton: Button = findViewById(R.id.backButton)

        // Configuramos Firebase Database
        databaseReference = FirebaseDatabase.getInstance().reference.child("pedidos")

        // Obtener la lista de productos seleccionados desde el intent
        val newProducts = intent.getSerializableExtra("cartProducts") as? ArrayList<Producto>
        if (newProducts != null) {
            cartProducts = newProducts
        }

        // Inicializamos el adaptador y mostramos los productos
        cartAdapter = InicioAdaptador(cartProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        // Calcular el total
        val total = cartProducts.sumOf { it.price }
        totalTextView.text = "Total a pagar: $${"%.2f".format(total)}"

        // Configurar el botón de checkout
        checkoutButton.setOnClickListener {
            enviarPedido(total)
        }

        // Botón para regresar al menú
        backButton.setOnClickListener {
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
            finish() // Cerrar la actividad actual
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun enviarPedido(total: Double) {
        val pedidoId = databaseReference.push().key // Generar un nuevo id para el pedido
        if (pedidoId != null && cartProducts.isNotEmpty()) {
            val pedidoData = hashMapOf<String, Any>()
            val productosData = hashMapOf<String, Any>()
            for (producto in cartProducts) {
                productosData[producto.id] = mapOf(
                    "name" to producto.name,
                    "price" to producto.price
                )
            }

            pedidoData["productos"] = productosData
            pedidoData["total"] = total

            // Enviar los datos a Firebase
            databaseReference.child(pedidoId).setValue(pedidoData)
                .addOnSuccessListener {
                    cartProducts.clear() // Limpiar el carrito después de un envío exitoso
                    cartAdapter.notifyDataSetChanged()
                    totalTextView.text = "Total a pagar: $0.00"
                    showAlertDialog("Éxito", "Pedido enviado con éxito.")
                }
                .addOnFailureListener { exception ->
                    showAlertDialog("Error", "No se pudo enviar el pedido: ${exception.localizedMessage}")
                }
        } else {
            showAlertDialog("Error", "No hay productos en el carrito.")
        }
    }
}
