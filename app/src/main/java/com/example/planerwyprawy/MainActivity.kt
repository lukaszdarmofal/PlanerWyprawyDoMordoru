package com.example.planerwyprawydomordoru

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planerwyprawy.R
import java.time.Month


class MainActivity : AppCompatActivity() {
    var selectedRace: String = ""
    var output = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val summaryText = findViewById<TextView>(R.id.summaryTextView)
        val name = findViewById<EditText>(R.id.characterName).text
        val elfPath = findViewById<Switch>(R.id.elfySwitch)
        val moral = findViewById<RatingBar>(R.id.moraleRating)
        val image = findViewById<ImageView>(R.id.imageView)

        val cloakCheckBox = findViewById<CheckBox>(R.id.equipmentCheckBox1)
        val lembasCheckBox = findViewById<CheckBox>(R.id.equipmentCheckBox2)
        val torchCheckBox = findViewById<CheckBox>(R.id.equipmentCheckBox3)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)

        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth

        var path = ""
        if (elfPath.isChecked == true) {
            path = "tak"
        } else path = "nie"

        var march = ""
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId).text
            march = "$radioButton"
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
                selectedRace = races[position]
                when (position) {
                    0 -> image.setImageResource(R.drawable.vaginal)
                    1 -> image.setImageResource(R.drawable.sword)
                    2 -> image.setImageResource(R.drawable.arrow)
                    3 -> image.setImageResource(R.drawable.hammer)
                    4 -> image.setImageResource(R.drawable.weapon)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedRace = races[0]
            }
        }

        // Jak coś zmienna z wybraną rasą to " selectedRace "

        val durationTimeSeekBar: SeekBar = findViewById<SeekBar>(R.id.czasTrwaniaSeekBar)
        val durationTimeTexView: TextView = findViewById<TextView>(R.id.czasTrwaniaTextView)

        durationTimeSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    output = "" + seek.progress + " min"
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



        val summaryButton = findViewById<Button>(R.id.summaryButton)
        summaryButton.setOnClickListener {
            val equipment = mutableListOf<String>()
            if (cloakCheckBox.isChecked) equipment.add("Płaszcz elfów")
            if (lembasCheckBox.isChecked) equipment.add("Lembasy")
            if (torchCheckBox.isChecked) equipment.add("Pochodnia")
            summaryText.setText("Imię: "+name+"\nRasa: "+selectedRace+"\nŚcieżki elfów: "+path+"\nMorale: "+moral.rating + "\nCzas trwania marszu: "+output + "\nTermin: "+year+"."+month+"."+day+" "+selectedTime+"\n Ekwipunek: "+equipment.joinToString(" ,")+"\n Priorytet marszu: "+march.toString()) }
    }
}