package com.example.workassistantv001

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.LocaleData
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.workassistantv001.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var txt_selected_date : TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btn_manage_objects = findViewById(R.id.btnManageObjects) as Button
        val btn_manage_workers = findViewById(R.id.btnManageWorkers) as Button
        val btn_select_date = findViewById(R.id.btnSelectDate) as Button
        txt_selected_date = findViewById(R.id.tvSelectedDate)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        txt_selected_date.text = LocalDateTime.now().format(formatter)


        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        btn_manage_objects.setOnClickListener {
            val intent = Intent(this, ObjectsActivity::class.java)
            startActivity(intent)
        }

        btn_manage_workers.setOnClickListener {
            val intent = Intent(this, WorkersActivity::class.java)
            startActivity(intent)
        }

        btn_select_date.setOnClickListener {
//            val intent = Intent(this, CalendarActivity::class.java)
//            startActivity(intent)

            DatePickerDialog(this, datePicker,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        txt_selected_date.setText(sdf.format(myCalendar.time))
    }
}