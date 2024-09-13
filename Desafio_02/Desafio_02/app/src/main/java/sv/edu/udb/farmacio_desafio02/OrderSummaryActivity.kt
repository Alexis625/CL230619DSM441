package sv.edu.udb.farmacio_desafio02

import android.os.Bundle
import android.os.Parcelable
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class OrderSummaryActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var totalTextView: TextView
    private val selectedMedicines = mutableListOf<Medicine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        listView = findViewById(R.id.lvOrderSummary)
        totalTextView = findViewById(R.id.tvTotal)

        // Retrieve the list of medicines passed via Intent
        val extras = intent.extras
        if (extras != null) {
            // Get the Parcelable list from the extras
            val medicinesArray = extras.getParcelableArray("selectedMedicines")
            if (medicinesArray != null) {
                selectedMedicines.addAll(medicinesArray.filterIsInstance<Medicine>())
            }
        }

        // Convert list of Medicine objects to a list of strings for ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedMedicines.map { "${it.name} - $${it.price}" })
        listView.adapter = adapter

        // Calculate the total price
        val total = selectedMedicines.sumOf { it.price }
        totalTextView.text = "Total: $${total}"

        findViewById<Button>(R.id.btnPlaceOrder).setOnClickListener {
            saveOrderToFirebase()
        }
    }

    private fun saveOrderToFirebase() {
        val database = FirebaseDatabase.getInstance()
        val ordersRef = database.getReference("orders")
        val orderId = ordersRef.push().key ?: return

        val orderData = mapOf(
            "medicines" to selectedMedicines.map { mapOf("name" to it.name, "price" to it.price) },
            "total" to selectedMedicines.sumOf { it.price }
        )

        ordersRef.child(orderId).setValue(orderData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                finish() // Return to the previous screen
            } else {
                // Show an error message
                Toast.makeText(this, "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
