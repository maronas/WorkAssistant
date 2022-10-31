package com.example.workassistantv001

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_manage_objects =findViewById(R.id.btnManageObjects) as Button
        val btn_manage_workers = findViewById(R.id.btnManageWorkers) as Button

        btn_manage_objects.setOnClickListener {
            val intent = Intent(this, ObjectsActivity::class.java)
            startActivity(intent)
        }

        btn_manage_workers.setOnClickListener {
            val intent = Intent(this, WorkersActivity::class.java)
            startActivity(intent)
        }
    }
}