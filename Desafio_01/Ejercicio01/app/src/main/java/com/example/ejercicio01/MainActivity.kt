package com.example.ejercicio01

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var input3: EditText
    private lateinit var input4: EditText
    private lateinit var input5: EditText
    private lateinit var Nombretext: EditText
    private lateinit var Calcular: Button
    private lateinit var Nombre: TextView
    private lateinit var Nota1: TextView
    private lateinit var Nota2: TextView
    private lateinit var Nota3: TextView
    private lateinit var Nota4: TextView
    private lateinit var Nota5: TextView
    private lateinit var Final: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de vistas
        Nombretext = findViewById(R.id.Nombre)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        input4 = findViewById(R.id.input4)
        input5 = findViewById(R.id.input5)
        Calcular = findViewById(R.id.Calcular)
        Nombre = findViewById(R.id.Nombreresult)
        Nota1 = findViewById(R.id.Resultado1)
        Nota2 = findViewById(R.id.Resultado2)
        Nota3 = findViewById(R.id.Resultado3)
        Nota4 = findViewById(R.id.Resultado4)
        Nota5 = findViewById(R.id.Resultado5)
        Final = findViewById(R.id.Final)

        // Configuración del listener para el botón Calcular
        Calcular.setOnClickListener {
            val name = Nombretext.text.toString()
            val Ingreso1 = input1.text.toString()
            val Ingreso2 = input2.text.toString()
            val Ingreso3 = input3.text.toString()
            val Ingreso4 = input4.text.toString()
            val Ingreso5 = input5.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese su nombre y apellido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (Ingreso1.isEmpty() || Ingreso2.isEmpty() || Ingreso3.isEmpty() || Ingreso4.isEmpty() || Ingreso5.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos de notas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val num1 = Ingreso1.toDoubleOrNull()
            val num2 = Ingreso2.toDoubleOrNull()
            val num3 = Ingreso3.toDoubleOrNull()
            val num4 = Ingreso4.toDoubleOrNull()
            val num5 = Ingreso5.toDoubleOrNull()

            if (num1 == null || num2 == null || num3 == null || num4 == null || num5 == null) {
                Toast.makeText(this, "Por favor, ingrese valores válidos para todas las notas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de que las notas no sean mayores a 10
            if (num1 > 10 || num2 > 10 || num3 > 10 || num4 > 10 || num5 > 10) {
                Toast.makeText(this, "Las notas no pueden ser mayores a 10", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nota1 = num1 * 0.15
            val nota2 = num2 * 0.15
            val nota3 = num3 * 0.20
            val nota4 = num4 * 0.25
            val nota5 = num5 * 0.25
            val finalNota = nota1 + nota2 + nota3 + nota4 + nota5

            Nombre.text = ": ${name}"
            Nota1.text = "Nota 1: ${String.format("%.2f", nota1)}"
            Nota2.text = "Nota 2: ${String.format("%.2f", nota2)}"
            Nota3.text = "Nota 3: ${String.format("%.2f", nota3)}"
            Nota4.text = "Nota 4: ${String.format("%.2f", nota4)}"
            Nota5.text = "Nota 5: ${String.format("%.2f", nota5)}"
            Final.text = "Nota Final: ${String.format("%.2f", finalNota)}"
        }
    }
}
