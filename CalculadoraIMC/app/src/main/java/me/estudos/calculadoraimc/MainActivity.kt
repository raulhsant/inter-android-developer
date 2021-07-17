package me.estudos.calculadoraimc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        calculateButton?.setOnClickListener { calculateImc(weightInput.text.toString(), heightInput.text.toString()) }
    }

    private fun calculateImc(weight: String, height: String) {
        val numericWeight = weight.toFloatOrNull()
        val numericHeight = height.toFloatOrNull()

        if (numericWeight != null && numericHeight != null) {
            titleText.text = "Seu IMC é igual a ${numericWeight * 1000 / (numericHeight * numericHeight)}"
        }
    }
}