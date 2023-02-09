package com.example.workassistantv001

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.day_worker_view.view.*
import kotlinx.android.synthetic.main.day_worker_view.view.tvWorkerName
import kotlinx.android.synthetic.main.object_view.view.*
import kotlinx.android.synthetic.main.worker_view.view.*

class MainAdapter(
    private val objects: MutableList<Object>,
    private val workers: MutableList<Worker>,
): RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
    var languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.day_worker_view,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val curWorker = workers[position]

        holder.itemView.apply {
            tvWorkerName.text = curWorker.name
            ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                languages
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spObjects.adapter = adapter
            }
        }


    }

    override fun getItemCount(): Int{
        return workers.size
    }

}