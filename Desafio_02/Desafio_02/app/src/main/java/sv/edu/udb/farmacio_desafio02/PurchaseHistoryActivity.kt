package sv.edu.udb.farmacio_desafio02

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue

class PurchaseHistoryActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val orders = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)

        listView = findViewById(R.id.lvPurchaseHistory)

        loadPurchaseHistory()
    }

    private fun loadPurchaseHistory() {
        val database = FirebaseDatabase.getInstance()
        val ordersRef = database.getReference("orders")

        ordersRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                orders.clear()
                snapshot.children.forEach { orderSnapshot ->
                    val order = orderSnapshot.getValue<Map<String, Any>>()
                    val total = order?.get("total") as? Double ?: 0.0
                    orders.add("Order ID: ${orderSnapshot.key}, Total: $${total}")
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, orders)
                listView.adapter = adapter
            }
        }
    }
}
