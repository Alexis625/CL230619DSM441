package sv.edu.udb.farmacio_desafio02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InicioAdaptador(private val cartProducts: List<Producto>) :
    RecyclerView.Adapter<InicioAdaptador.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inicio_producto, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartProducts[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = cartProducts.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)

        fun bind(product: Producto) {
            nameTextView.text = product.name
            priceTextView.text = "$${product.price}"
        }
    }
}