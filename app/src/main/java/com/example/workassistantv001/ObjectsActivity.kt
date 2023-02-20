package com.example.workassistantv001

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workassistantv001.databinding.ActivityObjectsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_objects.*

class ObjectsActivity : AppCompatActivity() {
    private lateinit var objectAdapter: ObjectAdapter
    private lateinit var binding: ActivityObjectsBinding
    private lateinit var database : DatabaseReference
    private lateinit var objectArrayList: ArrayList<Object>
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            userId = user.uid
        }

        objectAdapter = ObjectAdapter((mutableListOf()))

        rvObjects.adapter = objectAdapter
        rvObjects.layoutManager = LinearLayoutManager(this)

        readData()

        binding.btnAddObject.setOnClickListener {
            if(binding.etObjectName.text.isNotEmpty()){
                val objectName = binding.etObjectName.text.toString()
                database = FirebaseDatabase.getInstance().getReference(userId).child("Objects")
                val objekt = Object(objectName)
                database.child(objectName).setValue(objekt).addOnSuccessListener {
                    binding.etObjectName.text.clear()

                    Toast.makeText(this,"Successfully Added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                }
                refresh()
            }
            else{
                Toast.makeText(this,"Object name is empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteSelectedObjects.setOnClickListener {
            for(i in objectArrayList){
                if(i.isChecked){
                    deleteObject(i.name.toString())
                }
            }
            refresh()
        }
    }

    private fun readData(){
        database = FirebaseDatabase.getInstance().getReference(userId).child("Objects")

        objectArrayList = arrayListOf<Object>()
        getObjectData()
    }

    private fun getObjectData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (objectSnapshot in snapshot.children) {
                        val newObject = objectSnapshot.getValue(Object::class.java)
                        objectArrayList.add(newObject!!)
                    }
                    rvObjects.adapter = ObjectAdapter(objectArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun deleteObject(objectName: String){
        database = FirebaseDatabase.getInstance().getReference(userId).child("Objects")
        database.child(objectName).removeValue().addOnSuccessListener {
            Toast.makeText(this,"Successfuly Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refresh(){
        objectArrayList.clear()
    }
}