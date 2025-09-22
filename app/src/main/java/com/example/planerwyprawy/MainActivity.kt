package com.example.planerwyprawydomordoru

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
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

        val timePicker: TimePicker = findViewById<TimePicker>(R.id.timePicker)
        var selectedTime = "00:00"
        timePicker.setIs24HourView(true)

        timePicker.setOnTimeChangedListener { view, hour, minute ->
            selectedTime = "$hour:$minute"
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

        val durationTimeSeekBar: SeekBar = findViewById<SeekBar>(R.id.czasTrwaniaSeekBar)
        val durationTimeTexView: TextView = findViewById<TextView>(R.id.czasTrwaniaTextView)

        durationTimeSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    val output = "" + seek.progress + " min"
                    durationTimeTexView.text = output
                }

                override fun onStartTrackingTouch(seek: SeekBar) {}

                // Handle when the user stops tracking touch
                override fun onStopTrackingTouch(seek: SeekBar) {

                }
            }
        )

        val treningChronometer: Chronometer = findViewById<Chronometer>(R.id.treningChronometer)
        var treningRunning = false
        var pauseOffset:Long =0
        val treningButton: Button = findViewById<Button>(R.id.treningButton)

        treningButton.setOnClickListener {
            if(!treningRunning) {
                treningChronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                treningChronometer.start()
                treningRunning = true
            } else {
                pauseOffset = SystemClock.elapsedRealtime() - treningChronometer.base
                treningChronometer.stop()
                treningRunning = false
            }
        }

    }
}