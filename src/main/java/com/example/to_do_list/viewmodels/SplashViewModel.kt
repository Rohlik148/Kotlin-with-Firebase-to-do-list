package com.example.to_do_list.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _navigateToHome = MutableStateFlow<Boolean?>(null)
    val navigateToHome: StateFlow<Boolean?> = _navigateToHome

    private val _navigateToSignIn = MutableStateFlow<Boolean?>(null)
    val navigateToSignIn: StateFlow<Boolean?> = _navigateToSignIn

    init {
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn() {
        viewModelScope.launch {
            delay(2000)
            if (auth.currentUser != null) {
                _navigateToHome.value = true
            } else {
                _navigateToSignIn.value = true
            }
        }
    }

    fun resetNavigation() {
        _navigateToHome.value = null
        _navigateToSignIn.value = null
    }
}