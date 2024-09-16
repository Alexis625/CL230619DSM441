package sv.edu.udb.farmacio_desafio02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import sv.edu.udb.farmacio_desafio02.databinding.ActivityHistorialBinding

class Historial : AppCompatActivity() {

    private lateinit var binding: ActivityHistorialBinding
    private lateinit var database: DatabaseReference
    private lateinit var historialAdapter: HistorialAdaptador
    private val comprasList = mutableListOf<Compra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar View Binding
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.historialRecyclerView.layoutManager = LinearLayoutManager(this)
        historialAdapter = HistorialAdaptador(comprasList)
        binding.historialRecyclerView.adapter = historialAdapter

        // Inicializar la base de datos de Firebase
        database = FirebaseDatabase.getInstance().getReference("pedidos")

        // Cargar historial de compras desde Firebase
        cargarHistorialCompras()
    }

    private fun cargarHistorialCompras() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comprasList.clear()
                val backButton: Button = findViewById(R.id.backButton)
                for (pedidoSnapshot in snapshot.children) {
                    val productosList = mutableListOf<Producto>()
                    var totalCompra = 0.0


                    // Obtener productos
                    val productosSnapshot = pedidoSnapshot.child("productos")
                    for (productoSnapshot in productosSnapshot.children) {
                        val name = productoSnapshot.child("name").getValue(String::class.java) ?: ""
                        val price = productoSnapshot.child("price").getValue(Double::class.java) ?: 0.0
                        productosList.add(Producto("", name, price))
                    }

                    // Obtener total de la compra
                    totalCompra = pedidoSnapshot.child("total").getValue(Double::class.java) ?: 0.0

                    // Crear objeto Compra y añadirlo a la lista
                    val compra = Compra(productosList, totalCompra, "16/09/2024")
                    comprasList.add(compra)
                }
                historialAdapter.notifyDataSetChanged()

                backButton.setOnClickListener {
                    val intent = Intent(this@Historial, menu::class.java)
                    startActivity(intent)
                    finish() // Cerrar la actividad actual
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores
            }
        })
    }
}
