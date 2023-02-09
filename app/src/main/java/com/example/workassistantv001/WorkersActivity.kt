package com.example.workassistantv001

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workassistantv001.databinding.ActivityMainBinding
import com.example.workassistantv001.databinding.ActivityWorkersBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_workers.*
import kotlinx.android.synthetic.main.activity_workers.view.*
import kotlinx.android.synthetic.main.worker_view.*
import kotlinx.android.synthetic.main.worker_view.view.*

class WorkersActivity : AppCompatActivity() {

    private lateinit var workerAdapter: WorkerAdapter

    private lateinit var binding: ActivityWorkersBinding
    private lateinit var database : DatabaseReference
    private lateinit var workerArrayList: ArrayList<Worker>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        workerAdapter = WorkerAdapter(mutableListOf())
        rvWorkers.adapter = workerAdapter
        rvWorkers.layoutManager = LinearLayoutManager(this)
        readData()
        binding.btnAddWorker.setOnClickListener {

            if(binding.etWorkerLastname.text.isNotEmpty() && binding.etWorkerName.text.isNotEmpty() ){
                val firstName = binding.etWorkerName.text.toString()
                val lastName = binding.etWorkerLastname.text.toString()
                val username = firstName + lastName
                database = FirebaseDatabase.getInstance().getReference("Workers")
                val worker = Worker(username, firstName, lastName)
                database.child(username).setValue(worker).addOnSuccessListener {
                    binding.etWorkerName.text.clear()
                    binding.etWorkerLastname.text.clear()
                    Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                }
                refresh()
            }
        }

        binding.btnDeleteSelectedWorkers.setOnClickListener {
            for(i in workerArrayList){
                if(i.isChecked)
                    deleteWorker(i.username.toString())
            }
            refresh()
        }
    }

    private fun readData(){
        database = FirebaseDatabase.getInstance().getReference("Workers")
        workerArrayList = arrayListOf<Worker>()
        getWorkerData()
    }

    private fun getWorkerData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (workerSnapshot in snapshot.children) {
                        val worker = workerSnapshot.getValue(Worker::class.java)
                        workerArrayList.add(worker!!)
                    }
                    rvWorkers.adapter = WorkerAdapter(workerArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun deleteWorker(workerUsername: String){
        database = FirebaseDatabase.getInstance().getReference("Workers")
        database.child(workerUsername).removeValue().addOnSuccessListener {
            Toast.makeText(this,"Successfuly Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refresh(){
        workerArrayList.clear()
    }

}