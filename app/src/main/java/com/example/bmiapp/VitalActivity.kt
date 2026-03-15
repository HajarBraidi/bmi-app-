package com.example.bmiapp

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bmiapp.databinding.ActivityVitalBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class VitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVitalBinding
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Date picker
        binding.dpDate.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            picker.show(supportFragmentManager, "datePicker")
            picker.addOnPositiveButtonClickListener { selection ->
                val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.dpDate.setText(fmt.format(Date(selection)))
            }
        }

        // Time picker
        binding.tpTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(0).setMinute(0)
                .setTitleText("Select Time")
                .build()
            timePicker.show(supportFragmentManager, "timePicker")
            timePicker.addOnPositiveButtonClickListener {
                val hh = String.format("%02d", timePicker.hour)
                val mm = String.format("%02d", timePicker.minute)
                binding.tpTime.setText("$hh:$mm")
            }
        }

        // Save
        binding.btnSave.setOnClickListener {
            val date = binding.dpDate.text?.toString()?.trim().orEmpty()
            val time = binding.tpTime.text?.toString()?.trim().orEmpty()
            val systolic = binding.etSys.text?.toString()?.trim()?.toIntOrNull()
            val diastolic = binding.etDia.text?.toString()?.trim()?.toIntOrNull()
            val heart = binding.etHr.text?.toString()?.trim()?.toIntOrNull()
            val temperature = binding.etTemp.text?.toString()?.trim()?.toDoubleOrNull()
            val sugar = binding.etSugar.text?.toString()?.trim()?.toDoubleOrNull()

            // Clear previous errors
            binding.tilDate.error = null; binding.tilTime.error = null
            binding.tilSys.error = null; binding.tilDia.error = null
            binding.tilHr.error = null; binding.tilTemp.error = null
            binding.tilSugar.error = null

            var ok = true
            if (date.isEmpty()) { binding.tilDate.error = "Required"; ok = false }
            if (time.isEmpty()) { binding.tilTime.error = "Required"; ok = false }
            if (systolic == null) { binding.tilSys.error = "Enter a number"; ok = false }
            if (diastolic == null) { binding.tilDia.error = "Enter a number"; ok = false }
            if (heart == null) { binding.tilHr.error = "Enter a number"; ok = false }
            if (temperature == null) { binding.tilTemp.error = "Enter a value"; ok = false }
            if (sugar == null) { binding.tilSugar.error = "Enter a value"; ok = false }

            if (systolic != null && systolic !in 80..200) { binding.tilSys.error = "Range 80–200 mmHg"; ok = false }
            if (diastolic != null && diastolic !in 50..130) { binding.tilDia.error = "Range 50–130 mmHg"; ok = false }
            if (heart != null && heart !in 40..200) { binding.tilHr.error = "Range 40–200 bpm"; ok = false }
            if (temperature != null && (temperature < 34.0 || temperature > 42.0)) { binding.tilTemp.error = "Range 34–42 °C"; ok = false }
            if (sugar != null && (sugar < 50.0 || sugar > 400.0)) { binding.tilSugar.error = "Range 50–400 mg/dL"; ok = false }

            if (!ok) return@setOnClickListener

            val values = ContentValues().apply {
                put("date", date); put("time", time)
                put("systolic", systolic!!); put("diastolic", diastolic!!)
                put("heart", heart!!); put("temperature", temperature!!)
                put("sugar", sugar!!)
            }
            val rowId = db.insert("Record", null, values)
            if (rowId > 0) Toast.makeText(this, "Record saved.", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "Save failed.", Toast.LENGTH_SHORT).show()
        }

        // Share
        binding.btnShare.setOnClickListener {
            val msg = buildString {
                appendLine("Vital signs record")
                appendLine("Date: ${binding.dpDate.text}")
                appendLine("Time: ${binding.tpTime.text}")
                appendLine("Systolic: ${binding.etSys.text} mmHg")
                appendLine("Diastolic: ${binding.etDia.text} mmHg")
                appendLine("Heart rate: ${binding.etHr.text} bpm")
                appendLine("Temperature: ${binding.etTemp.text} °C")
                appendLine("Blood sugar: ${binding.etSugar.text} mg/dL")
            }
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, msg)
            }
            startActivity(Intent.createChooser(intent, "Share record"))
        }

        // Clear
        binding.btnClear.setOnClickListener {
            binding.dpDate.setText(""); binding.tpTime.setText("")
            binding.etSys.setText(""); binding.etDia.setText("")
            binding.etHr.setText(""); binding.etTemp.setText("")
            binding.etSugar.setText("")
            binding.tilDate.error = null; binding.tilTime.error = null
            binding.tilSys.error = null; binding.tilDia.error = null
            binding.tilHr.error = null; binding.tilTemp.error = null
            binding.tilSugar.error = null
        }
    }

    override fun onStart() {
        super.onStart()
        db = openOrCreateDatabase("vitals.db", MODE_PRIVATE, null)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS Record(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT NOT NULL,
                time TEXT NOT NULL,
                systolic INTEGER NOT NULL,
                diastolic INTEGER NOT NULL,
                heart INTEGER NOT NULL,
                temperature REAL NOT NULL,
                sugar REAL NOT NULL
            );
        """.trimIndent())
    }

    override fun onStop() {
        super.onStop()
        if (::db.isInitialized && db.isOpen) db.close()
    }
}