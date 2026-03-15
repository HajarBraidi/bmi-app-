package com.example.bmiapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.bmiapp.databinding.ActivityHubBinding

class HubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // --- Boutons existants (Lab 4 & 5) ---
        binding.btnOpenBmi.setOnClickListener {
            startActivity(Intent(this, BmiActivity::class.java))
        }
        binding.btnOpenConverter.setOnClickListener {
            startActivity(Intent(this, CurrencyConverterActivity::class.java))
        }
        binding.btnOpenAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        // ---  Nouveaux boutons Lab 6 ---
        binding.btnOpenVital.setOnClickListener {
            startActivity(Intent(this, VitalActivity::class.java))
        }
        binding.btnOpenRecords.setOnClickListener {
            startActivity(Intent(this, RecordsActivity::class.java))
        }

        binding.btnQuit.setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hub, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_open_bmi       -> { startActivity(Intent(this, BmiActivity::class.java)); true }
        R.id.action_open_converter -> { startActivity(Intent(this, CurrencyConverterActivity::class.java)); true }
        R.id.action_open_about     -> { startActivity(Intent(this, AboutActivity::class.java)); true }

        // --- Nouveaux items menu Lab 6 ---
        R.id.action_open_vital     -> { startActivity(Intent(this, VitalActivity::class.java)); true }
        R.id.action_open_records   -> { startActivity(Intent(this, RecordsActivity::class.java)); true }

        R.id.action_exit           -> { finish(); true }
        else -> super.onOptionsItemSelected(item)
    }
}