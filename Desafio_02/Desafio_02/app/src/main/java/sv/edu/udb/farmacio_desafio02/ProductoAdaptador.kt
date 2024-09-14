package sv.edu.udb.farmacio_desafio02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// El adaptador que maneja la lista de productos
class ProductoAdaptador(
    private val productList: List<Producto>,
    private val onProductSelected: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdaptador.ProductViewHolder>() {

    // ViewHolder que representa cada elemento (producto) en la lista
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        private val addButton: Button = itemView.findViewById(R.id.addButton)

        // Asigna los datos del producto a las vistas correspondientes
        fun bind(product: Producto, onProductSelected: (Producto) -> Unit) {
            productNameTextView.text = product.name
            productPriceTextView.text = "$${product.price}"

            // Configuramos el botón "Agregar" para seleccionar el producto
            addButton.setOnClickListener {
                onProductSelected(product)
            }
        }
    }

    // Crea el ViewHolder para cada producto
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductViewHolder(view)
    }

    // Vincula los datos de un producto específico a su ViewHolder
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product, onProductSelected)
    }

    // Devuelve el número de productos en la lista
    override fun getItemCount(): Int = productList.size
}
