package sv.edu.udb.farmacio_desafio02

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class SelectMedicinesActivity : AppCompatActivity() {

    private val medicines = mutableListOf<Medicine>()
    private val selectedMedicines = mutableListOf<Medicine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_medicines)

        val listViewMedicines: ListView = findViewById(R.id.listViewMedicines)
        loadMedicines()

        val medicinesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, medicines.map { it.name })
        listViewMedicines.adapter = medicinesAdapter
        listViewMedicines.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listViewMedicines.setOnItemClickListener { _, _, position, _ ->
            val selectedMedicine = medicines[position]
            if (selectedMedicines.contains(selectedMedicine)) {
                selectedMedicines.remove(selectedMedicine)
            } else {
                selectedMedicines.add(selectedMedicine)
            }
        }

        val btnViewOrder: Button = findViewById(R.id.btnViewOrder)
        btnViewOrder.setOnClickListener {
            val intent = Intent(this, OrderSummaryActivity::class.java)
            intent.putExtra("selectedMedicines", ArrayList(selectedMedicines))
            startActivity(intent)
        }
    }

    private fun loadMedicines() {
        medicines.addAll(listOf(
            Medicine("Paracetamol","dolor panza", 3.5),
            Medicine("Ibuprofeno", "dolor panza",5.0),
            Medicine("Amoxicilina", "dolor panza",7.5),
            Medicine("Cetirizina", "dolor panza",4.0),
            Medicine("Aspirina", "dolor panza",2.5),
            Medicine("Metformina", "dolor panza",6.0),
            Medicine("Loratadina", "dolor panza",4.5),
            Medicine("Omeprazol", "dolor panza",8.0),
            Medicine("Azitromicina", "dolor panza",10.0),
            Medicine("Simvastatina", "dolor panza",9.0)
        ))
    }
}
