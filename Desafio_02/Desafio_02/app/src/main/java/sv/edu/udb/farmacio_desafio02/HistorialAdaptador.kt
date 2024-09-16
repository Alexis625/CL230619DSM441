package sv.edu.udb.farmacio_desafio02

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.farmacio_desafio02.databinding.ItemHistorialBinding

class HistorialAdaptador(private val comprasList: List<Compra>) :
    RecyclerView.Adapter<HistorialAdaptador.HistorialViewHolder>() {

    class HistorialViewHolder(private val binding: ItemHistorialBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(compra: Compra) {
            binding.textViewFecha.text = "Fecha: ${compra.fecha}"
            binding.textViewTotal.text = "Total: $${compra.total}"

            // Mostrar productos comprados
            val productosInfo = compra.productos.joinToString("\n") { "${it.name}: $${it.price}" }
            binding.textViewProductos.text = productosInfo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val binding = ItemHistorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistorialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        holder.bind(comprasList[position])
    }

    override fun getItemCount() = comprasList.size
}
