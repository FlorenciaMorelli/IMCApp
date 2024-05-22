package com.example.imcapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    companion object{
        const val IMC = "imc"
    }

//   Inicializar las card view de genero
    // Lateinit lo inicializa después. Rendimiento: se recomienda instanciar los elementos visuales al inicializarlo
    private lateinit var viewMale: CardView
    private lateinit var viewFemale:CardView
    private var isMaleSelected:Boolean = true
    private var isFemaleSelected:Boolean = true

//    Inicializar slider
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider

    private var currentHeight:Int = 0

//    FABs
    private lateinit var fabPlusWeight:FloatingActionButton
    private lateinit var fabMinusWeight:FloatingActionButton
    private lateinit var tvWeight:TextView
    private var currentWeight:Int = 0

    private lateinit var fabPlusAge:FloatingActionButton
    private lateinit var fabMinusAge:FloatingActionButton
    private lateinit var tvAge:TextView
    private var currentAge:Int = 0

    private lateinit var btnCalculate:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        viewMale = findViewById(R.id.view_male)
        viewFemale = findViewById(R.id.view_female)

        tvHeight = findViewById(R.id.tv_height)
        rsHeight = findViewById(R.id.rs_height)

        fabMinusWeight = findViewById(R.id.btn_minus_weight)
        fabPlusWeight = findViewById(R.id.btn_plus_weight)
        tvWeight = findViewById(R.id.tv_weight)

        fabMinusAge = findViewById(R.id.btn_minus_age)
        fabPlusAge = findViewById(R.id.btn_plus_age)
        tvAge = findViewById(R.id.tv_age)

        btnCalculate = findViewById(R.id.btn_calculate)
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners(){
        viewMale.setOnClickListener{
            changeGender()
            setGenderColor()
        }
        viewFemale.setOnClickListener{
            changeGender()
            setGenderColor()
        }

        rsHeight.addOnChangeListener{ _ , value , _ ->
            val formatNumber = DecimalFormat("#.##")
            val result  = formatNumber.format(value)
            currentHeight = value.toInt()
            tvHeight.text = "$result cm"
        }

        fabMinusWeight.setOnClickListener{
            currentWeight -= 1
            setCurrentWeight()
        }
        fabPlusWeight.setOnClickListener{
            currentWeight += 1
            setCurrentWeight()
        }

        fabMinusAge.setOnClickListener{
            currentAge -= 1
            setCurrentAge()
        }
        fabPlusAge.setOnClickListener{
            currentAge += 1
            setCurrentAge()
        }

        btnCalculate.setOnClickListener{
            val imc = calculateIMC()
            navigateToResult(imc)
        }
    }

//    Cálculo de IMC
    private fun calculateIMC():Double{
        val heightInMts = currentHeight.toDouble() / 100
        val imc = currentWeight / (heightInMts * heightInMts)
        Log.i("IMC", "El imc es $imc")
        val formatIMC = DecimalFormat("#.##")
        val result = formatIMC.format(imc)

        return result.toDouble()
    }

    private fun navigateToResult(imc: Double){
        val intent = Intent(this, ResultIMC::class.java)
        intent.putExtra(IMC, imc)
        startActivity(intent)
    }

    private fun setGenderColor(){
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }
    private fun setCurrentWeight(){
        tvWeight.text = currentWeight.toString()
    }
    private fun setCurrentAge(){
        tvAge.text = currentAge.toString()
    }

    private fun getBackgroundColor(isItemSelected: Boolean): Int{
        val colorReference = if(isItemSelected){
            R.color.background_component_selected
        } else {
            R.color.background_component
        }

        return ContextCompat.getColor(this, colorReference)
    }

    private fun changeGender(){
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }
}