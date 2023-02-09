package com.example.workassistantv001

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.LocaleData
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workassistantv001.databinding.ActivityMainBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rvWorkers
import kotlinx.android.synthetic.main.activity_objects.*
import kotlinx.android.synthetic.main.activity_workers.*
import kotlinx.android.synthetic.main.day_worker_view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var txt_selected_date : TextView
    private lateinit var workersRefs : DatabaseReference
    private lateinit var objectsRefs : DatabaseReference


    private lateinit var objectArrayList: ArrayList<Object>
    private lateinit var workerArrayList: ArrayList<Worker>

    @SuppressLint("MissingInflatedId")
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
            DatePickerDialog(this, datePicker,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        mainAdapter = MainAdapter(mutableListOf(), mutableListOf())
        rvWorkers.adapter = mainAdapter
        rvWorkers.layoutManager = LinearLayoutManager(this)

        readData()

    }

    private fun readData(){
        workersRefs = FirebaseDatabase.getInstance().getReference("Workers")
        objectsRefs = FirebaseDatabase.getInstance().getReference("Objects")

        objectArrayList = arrayListOf<Object>()
        workerArrayList = arrayListOf<Worker>()
        getObjectData()
    }

    private fun getObjectData() {
        workersRefs.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {


                    for (workerSnapshot in snapshot.children) {
                        val worker = workerSnapshot.getValue(Worker::class.java)
                        workerArrayList.add(worker!!)

                    }
                    rvWorkers.adapter = MainAdapter(objectArrayList, workerArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Failed to load", Toast.LENGTH_SHORT).show()
            }
        })

        objectsRefs.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (objectSnapshot in snapshot.children) {
                        val newObject = objectSnapshot.getValue(Object::class.java)
                        objectArrayList.add(newObject!!)
                    }

                    rvWorkers.adapter = MainAdapter(objectArrayList, workerArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Failed to load", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        txt_selected_date.setText(sdf.format(myCalendar.time))
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        Toast.makeText(this, "Selected : ", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}