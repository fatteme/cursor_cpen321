package com.biteswipe.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biteswipe.data.api.UserApi
import com.biteswipe.data.auth.AuthManager
import kotlinx.coroutines.launch

class RegisterViewModel(private val authManager: AuthManager) : ViewModel() {
    private val api = UserApi()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _navigateToSwipe = MutableLiveData<Boolean>()
    val navigateToSwipe: LiveData<Boolean> = _navigateToSwipe

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                api.register(username, email, password)
                    .onSuccess { response ->
                        authManager.setAuthToken(response.token)
                        _navigateToSwipe.value = true
                    }
                    .onFailure { e ->
                        _error.value = e.message
                    }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onNavigationHandled() {
        _navigateToSwipe.value = false
    }
} 