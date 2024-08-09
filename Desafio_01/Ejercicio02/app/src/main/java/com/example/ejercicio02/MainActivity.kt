package com.example.ejercicio02

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencias a los elementos de la UI
        val txtNombre: EditText = findViewById(R.id.TxtNombre)
        val txtSalario: EditText = findViewById(R.id.TxtSalario)
        val btnEnviar: Button = findViewById(R.id.BtnEnviar)
        val llbResultado: TextView = findViewById(R.id.LlbResultado)
        val llbISSS: TextView = findViewById(R.id.LlbISSS)
        val llbAFP: TextView = findViewById(R.id.LlbAFP)
        val llbRenta: TextView = findViewById(R.id.LlbRenta)
        val llbSalarioTotal: TextView = findViewById(R.id.LlbSalarioTotal)

        // Configurar la acción del botón
        btnEnviar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val salarioBase = txtSalario.text.toString().toDoubleOrNull()

            if (salarioBase != null) {
                // Cálculos de descuentos
                val descuentoISSS = salarioBase * 0.03
                val descuentoAFP = salarioBase * 0.0725
                val descuentoRenta = calcularRenta(salarioBase)
                val salarioNeto = salarioBase - descuentoISSS - descuentoAFP - descuentoRenta

                // Mostrar los resultados
                llbResultado.text = "$nombre, tus descuentos de ley son:"
                llbISSS.text = getString(R.string.isss) + " $" + String.format("%.2f", descuentoISSS)
                llbAFP.text = getString(R.string.afp) + " $" + String.format("%.2f", descuentoAFP)
                llbRenta.text = getString(R.string.renta) + " $" + String.format("%.2f", descuentoRenta)
                llbSalarioTotal.text = getString(R.string.salariototal) + " $" + String.format("%.2f", salarioNeto)

                // Hacer visibles los TextViews
                llbResultado.visibility = TextView.VISIBLE
                llbISSS.visibility = TextView.VISIBLE
                llbAFP.visibility = TextView.VISIBLE
                llbRenta.visibility = TextView.VISIBLE
                llbSalarioTotal.visibility = TextView.VISIBLE
            } else {
                txtSalario.error = "Por favor ingresa un salario válido"
            }
        }
    }

    // Función para calcular el descuento de Renta según tramos
    private fun calcularRenta(salario: Double): Double {
        return when {
            salario <= 472.00 -> 0.0
            salario <= 895.24 -> (salario - 472) * 0.10 + 17.67
            salario <= 2038.10 -> (salario - 895.24) * 0.20 + 60.00
            else -> (salario - 2038.10) * 0.30 + 288.57
        }
    }
}
