package com.example.bmiapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.bmiapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalc.setOnClickListener {
            val weight = binding.etWeight.text?.toString()?.toDoubleOrNull()
            val height = binding.etHeight.text?.toString()?.toDoubleOrNull()
            val bmi = bmiOrNull(weight, height)

            if (bmi == null) {
                binding.tilWeight.error = if (weight == null || weight <= 0) "Enter valid weight" else null
                binding.tilHeight.error = if (height == null || height <= 0) "Enter valid height" else null
                binding.tvBmi.text = "—"
                binding.tvCategory.text = "Invalid input"
            } else {
                binding.tilWeight.error = null
                binding.tilHeight.error = null
                binding.tvBmi.text = String.format("BMI: %.1f", bmi)
                val cat = bmiCategory(bmi)
                binding.tvCategory.text = "Category: $cat"
                binding.tvCategory.setTextColor(colorFor(cat))
            }
        }

        binding.btnClear.setOnClickListener {
            binding.etWeight.setText("")
            binding.etHeight.setText("")
            binding.tilWeight.error = null
            binding.tilHeight.error = null
            binding.tvBmi.text = "—"
            binding.tvCategory.text = "—"
            binding.etWeight.requestFocus()
        }
    }

    fun bmiOrNull(weightKg: Double?, heightCm: Double?): Double? {
        val w = weightKg ?: return null
        val h = heightCm ?: return null
        if (w <= 0.0 || h <= 0.0) return null
        val hM = h / 100.0
        return w / (hM * hM)
    }

    fun bmiCategory(bmi: Double): String = when {
        bmi < 18.5 -> "Underweight"
        bmi < 25.0 -> "Normal"
        bmi < 30.0 -> "Overweight"
        else -> "Obese"
    }

    private fun colorFor(category: String): Int = when (category) {
        "Underweight" -> 0xFF64B5F6.toInt()
        "Normal"      -> 0xFF66BB6A.toInt()
        "Overweight"  -> 0xFFFFA726.toInt()
        else          -> 0xFFE57373.toInt()
    }
}