package com.example.to_do_list.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _signInSuccess = MutableStateFlow<Boolean?>(null)
    val signInSuccess: StateFlow<Boolean?> = _signInSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signIn(email: String, pass: String) {
        _isLoading.value = true
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    _isLoading.value = false
                    if (task.isSuccessful) {
                        _signInSuccess.value = true
                    } else {
                        _error.value = task.exception?.message
                        Log.e("SignInViewModel", "Error signing in: ${task.exception?.message}")
                    }
                }
        }
    }

    fun resetState() {
        _signInSuccess.value = null
        _error.value = null
    }
}