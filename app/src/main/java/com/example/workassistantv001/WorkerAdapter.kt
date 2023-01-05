package com.example.workassistantv001

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.worker_view.view.*

class WorkerAdapter(
    private val workers: MutableList<Worker>
) : RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        return WorkerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.worker_view,
                parent,
                false
            )
        )

    }

    fun addWorker(worker: Worker){
        workers.add(worker)
        notifyItemInserted(workers.size -1)
    }

    fun deleteWorker(){
        workers.removeAll { worker ->
            worker.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvWorkerName: TextView, isChecked: Boolean){
        if(isChecked){
            tvWorkerName.paintFlags = tvWorkerName.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvWorkerName.paintFlags = tvWorkerName.paintFlags or STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val curWorker = workers[position]
        holder.itemView.apply {
            tvWorkerName.text = curWorker.name
            tvWorkerLastname.text = curWorker.lastname
            cbWorker.isChecked = curWorker.isChecked
            toggleStrikeThrough(tvWorkerName, curWorker.isChecked)
            cbWorker.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(tvWorkerName, isChecked)
                curWorker.isChecked = !curWorker.isChecked
            }
        }
    }

    override fun getItemCount(): Int{
        return workers.size
    }

}