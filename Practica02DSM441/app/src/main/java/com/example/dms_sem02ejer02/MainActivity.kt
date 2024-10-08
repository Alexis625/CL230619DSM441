package com.example.dms_sem02ejer02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
lateinit var n1 : EditText
lateinit var n2 : EditText
lateinit var enviar : Button
lateinit var resultado: TextView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        n1 = findViewById(R.id.TxtNumero1)
        n2 = findViewById(R.id.TxtNumero2)
        enviar = findViewById(R.id.BtnEnviar)
        resultado = findViewById(R.id.LlbResultado)
        enviar.setOnClickListener{
            var num2 = n2.text.toString().toFloat()
            var num1 = n1.text.toString().toFloat()
            resultado.setText((num1+num2).toString())
        }
    }
}
