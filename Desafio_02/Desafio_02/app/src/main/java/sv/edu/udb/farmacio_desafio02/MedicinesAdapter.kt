package sv.edu.udb.farmacio_desafio02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicinesAdapter(
    private val medicines: List<Medicine>,
    private val onMedicineClick: (Medicine) -> Unit
) : RecyclerView.Adapter<MedicinesAdapter.MedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.bind(medicine)
    }

    override fun getItemCount(): Int = medicines.size

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMedicineName: TextView = itemView.findViewById(R.id.tvMedicineName)
        private val tvMedicineDescription: TextView = itemView.findViewById(R.id.tvMedicineDescription) // Nueva vista para la descripción
        private val tvMedicinePrice: TextView = itemView.findViewById(R.id.tvMedicinePrice)
        private val btnSelect: Button = itemView.findViewById(R.id.btnSelect)

        fun bind(medicine: Medicine) {
            tvMedicineName.text = medicine.name
            tvMedicineDescription.text = medicine.description // Mostrar la descripción
            tvMedicinePrice.text = "$${medicine.price}"

            btnSelect.setOnClickListener {
                onMedicineClick(medicine)
            }
        }
    }
}
