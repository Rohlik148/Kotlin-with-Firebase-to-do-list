package com.example.to_do_list.fragments.utils

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.EachTodoItemBinding

class ToDoAdapter(private val list: MutableList<ToDoData>):
RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>(){


    private var listener:TodoAdapterClicksInterface? = null

    fun setListener(listener:TodoAdapterClicksInterface){
        this.listener=listener
    }
    inner class ToDoViewHolder(val binding:EachTodoItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding =
            EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.task

                //Log.d(TAG, "onBindViewHolder: "+this)
                binding.editTask.setOnClickListener {
                    listener?.onEditItemClicked(this )
                }

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(this )
                }
            }
        }
    }

    fun setList(newList: List<ToDoData>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    interface TodoAdapterClicksInterface{
        fun onDeleteItemClicked(toDoData: ToDoData )
        fun onEditItemClicked(toDoData: ToDoData )
    }
}