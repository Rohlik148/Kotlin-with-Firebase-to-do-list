package com.example.to_do_list.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _signUpSuccess = MutableStateFlow<Boolean?>(null)
    val signUpSuccess: StateFlow<Boolean?> = _signUpSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signUp(email: String, pass: String) {
        _isLoading.value = true
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    _isLoading.value = false
                    if (task.isSuccessful) {
                        _signUpSuccess.value = true
                    } else {
                        _error.value = task.exception?.message
                        Log.e("SignUpViewModel", "Error signing up: ${task.exception?.message}")
                    }
                }
        }
    }

    fun resetState() {
        _signUpSuccess.value = null
        _error.value = null
    }
}