package sv.edu.udb.farmacio_desafio02

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
    private lateinit var cartProducts: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Inicializamos los elementos
        recyclerView = findViewById(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.totalTextView)
        val checkoutButton: Button = findViewById(R.id.totalTextView) // Aquí usamos el botón con id totalTextView

        // Configuramos Firebase Database
        databaseReference = FirebaseDatabase.getInstance().reference.child("pedidos")

        // Obtener la lista de productos seleccionados desde el intent
        cartProducts = intent.getSerializableExtra("cartProducts") as ArrayList<Producto>

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
        if (pedidoId != null) {
            val pedidoData = hashMapOf<String, Any>()
            val productosData = hashMapOf<String, Any>()
            for (producto in cartProducts) {
                productosData[producto.id] = producto // Suponiendo que Producto tiene un id único
            }

            pedidoData["productos"] = productosData
            pedidoData["total"] = total // Agregar el total

            // Enviar los datos a Firebase
            databaseReference.child(pedidoId).setValue(pedidoData)
                .addOnSuccessListener {
                    // Limpiar la lista de productos después de un envío exitoso
                    cartProducts.clear()
                    cartAdapter.notifyDataSetChanged()
                    showAlertDialog("Éxito", "Pedido enviado con éxito.")
                }
                .addOnFailureListener { exception ->
                    showAlertDialog("Error", "No se pudo enviar el pedido: ${exception.localizedMessage}")
                }
        } else {
            showAlertDialog("Error", "No se pudo generar un ID para el pedido.")
        }
    }
}
