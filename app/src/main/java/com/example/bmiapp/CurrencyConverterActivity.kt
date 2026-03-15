package com.example.bmiapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bmiapp.databinding.ActivityCurrencyConverterBinding

class CurrencyConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyConverterBinding

    // Rates relative to MAD (Moroccan Dirham)
    private val rates = mapOf(
        "MAD" to 1.0,
        "EUR" to 0.092,
        "USD" to 0.099
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currencies = rates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
            .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        binding.btnConvert.setOnClickListener {
            val amount = binding.etAmount.text?.toString()?.toDoubleOrNull()
            if (amount == null) {
                binding.tilAmount.error = "Enter a valid amount"
                return@setOnClickListener
            }
            binding.tilAmount.error = null
            val from = binding.spinnerFrom.selectedItem.toString()
            val to = binding.spinnerTo.selectedItem.toString()
            val rateFrom = rates[from] ?: run {
                Toast.makeText(this, "Rate not found", Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            val rateTo = rates[to] ?: run {
                Toast.makeText(this, "Rate not found", Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            val result = amount / rateFrom * rateTo
            binding.tvResult.text = String.format("%.2f %s = %.2f %s", amount, from, result, to)
        }
    }
}