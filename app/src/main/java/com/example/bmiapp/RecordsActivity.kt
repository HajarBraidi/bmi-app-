package com.example.bmiapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bmiapp.databinding.ActivityRecordsBinding

class RecordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordsBinding
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddRecord.setOnClickListener {
            startActivity(Intent(this, VitalActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        db = openOrCreateDatabase("vitals.db", MODE_PRIVATE, null)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS Record(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT NOT NULL, time TEXT NOT NULL,
                systolic INTEGER NOT NULL, diastolic INTEGER NOT NULL,
                heart INTEGER NOT NULL, temperature REAL NOT NULL, sugar REAL NOT NULL
            );
        """.trimIndent())
        loadData()
    }

    override fun onStop() {
        super.onStop()
        if (::db.isInitialized && db.isOpen) db.close()
    }

    private fun loadData() {
        val cursor = db.rawQuery(
            "SELECT date, time, systolic, diastolic, heart, temperature, sugar FROM Record ORDER BY id DESC",
            null
        )
        binding.tableRecords.removeAllViews()

        if (cursor.count == 0) {
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
            while (cursor.moveToNext()) {
                val values = listOf(
                    cursor.getString(0), cursor.getString(1),
                    cursor.getInt(2).toString(), cursor.getInt(3).toString(),
                    cursor.getInt(4).toString(), cursor.getDouble(5).toString(),
                    cursor.getDouble(6).toString()
                )
                val row = TableRow(this)
                for (text in values) {
                    val cell = TextView(this)
                    cell.text = text
                    cell.setPadding(12, 8, 12, 8)
                    cell.gravity = Gravity.CENTER
                    row.addView(cell)
                }
                binding.tableRecords.addView(row)
            }
        }
        cursor.close()
    }
}