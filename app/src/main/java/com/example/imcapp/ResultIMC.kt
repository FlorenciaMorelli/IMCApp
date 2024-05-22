package com.example.imcapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultIMC : AppCompatActivity() {

    private lateinit var tvResult:TextView
    private lateinit var tvImc:TextView
    private lateinit var tvDescription:TextView
    private lateinit var btnRecalculate:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result_imc)

        val result = intent.getDoubleExtra(MainActivity.IMC, 0.0)
        Log.i("imcResultado", "El IMC desde la otra activity es: $result")

        initComponents()
        initUi(result)
    }

    private fun initComponents(){
        tvResult = findViewById(R.id.tvResult)
        tvImc = findViewById(R.id.tvImc)
        tvDescription = findViewById(R.id.tvDescription)
        btnRecalculate = findViewById(R.id.btnRecalculate)
    }

    @SuppressLint("SetTextI18n")
    private fun initUi(result: Double){
        tvImc.text = result.toString()
        when(result){
            in 0.00..18.50 -> {
                tvResult.text = "Bajo"
                tvDescription.text = "Estás por debajo de tu peso"
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_bajo))
            }
            in 18.51..24.99 -> {
                tvResult.text = "Normal"
                tvDescription.text = "Estás en tu peso normal"
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_normal))
            }
            in 25.00..29.9 -> {
                tvResult.text = "Sobrepeso"
                tvDescription.text = "Estás sobre tu peso normal"
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_sobrepeso))
            }
            in 30.00..99.00 ->{
                tvResult.text = "Obesidad"
                tvDescription.text = "Tienes obesidad"
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_obesidad))
            }
            else -> {
                error("Error al calcular el IMC")
            }
        }
    }
}