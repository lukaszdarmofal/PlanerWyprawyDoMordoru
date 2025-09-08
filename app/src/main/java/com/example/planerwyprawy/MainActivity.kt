package com.example.planerwyprawydomordoru

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planerwyprawy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val raceSpinner: Spinner = findViewById<Spinner>(R.id.characterRaceSpinner)
        val races = arrayOf("hobbit", "człowiek", "elf", "krasnolud", "czarodziej")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, races)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        raceSpinner.adapter = adapter

        raceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedRace = races[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                 val selectedRace = races[0]
            }
        }

        // Jak coś zmienna z wybraną rasą to " selectedRace "

    }
}