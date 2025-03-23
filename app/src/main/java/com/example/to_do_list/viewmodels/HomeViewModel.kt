package com.example.to_do_list.viewmodels

import android.util.Log
import androidx.compose.ui.input.key.key
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do_list.fragments.utils.ToDoData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Tasks")
        .child(auth.currentUser?.uid.toString())

    private val _todoList = MutableStateFlow<List<ToDoData>>(emptyList())
    val todoList: StateFlow<List<ToDoData>> = _todoList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchData()
    }

    private fun fetchData() {
        _isLoading.value = true
        viewModelScope.launch {
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<ToDoData>()
                    for (taskSnapShot in snapshot.children) {
                        val todoTask = taskSnapShot.key?.let {
                            ToDoData(it, taskSnapShot.value.toString())
                        }
                        if (todoTask != null) {
                            list.add(todoTask)
                        }
                    }
                    _todoList.value = list
                    _isLoading.value = false
                }

                override fun onCancelled(error: DatabaseError) {
                    _error.value = error.message
                    _isLoading.value = false
                    Log.e("HomeViewModel", "Error fetching data: ${error.message}")
                }
            })
        }
    }

    fun saveTask(todo: String) {
        viewModelScope.launch {
            databaseRef.push().setValue(todo)
        }
    }

    fun updateTask(toDoData: ToDoData) {
        viewModelScope.launch {
            val map = HashMap<String, Any>()
            map[toDoData.taskId] = toDoData.task
            databaseRef.updateChildren(map)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            databaseRef.child(taskId).removeValue()
        }
    }
}