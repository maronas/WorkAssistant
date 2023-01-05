package com.example.workassistantv001

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.object_view.view.*
import kotlinx.android.synthetic.main.worker_view.view.*

class ObjectAdapter(
    private val objects: MutableList<Object>
    ): RecyclerView.Adapter<ObjectAdapter.ObjectViewHolder>(){

        class ObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectAdapter.ObjectViewHolder {
        return ObjectAdapter.ObjectViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.object_view,
                parent,
                false
            )
        )
    }

    private fun toggleStrikeThrough(tvObjectName: TextView, isChecked: Boolean){
        if(isChecked){
            tvObjectName.paintFlags = tvObjectName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            tvObjectName.paintFlags = tvObjectName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: ObjectAdapter.ObjectViewHolder, position: Int) {
        val curObject = objects[position]
        holder.itemView.apply {
            tvObjectName.text = curObject.name
            cbObjectDelete.isChecked = curObject.isChecked
            toggleStrikeThrough(tvObjectName, curObject.isChecked)
            cbObjectDelete.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(tvObjectName, isChecked)
                curObject.isChecked = !curObject.isChecked
            }
        }
    }

    override fun getItemCount(): Int{
        return objects.size
    }

}