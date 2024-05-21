package com.example.imcapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

//   Inicializar las card view de genero
    // Lateinit lo inicializa después. Rendimiento: se recomienda instanciar los elementos visuales al inicializarlo
    private lateinit var viewMale: CardView
    private lateinit var viewFemale:CardView
    private var isMaleSelected:Boolean = true
    private var isFemaleSelected:Boolean = true

//    Inicializar slider
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider

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
    }

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
            tvHeight.text = "$result cm"
        }
    }

    private fun setGenderColor(){
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
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