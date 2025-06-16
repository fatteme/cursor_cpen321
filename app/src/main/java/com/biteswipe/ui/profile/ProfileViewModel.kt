package com.biteswipe.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biteswipe.data.model.UserProfile
import com.biteswipe.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = UserRepository() // TODO: Pass auth token from a central auth manager

    private val _profile = MutableLiveData<UserProfile>()
    val profile: LiveData<UserProfile> = _profile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getUserProfile()
                    .onSuccess { userProfile ->
                        _profile.value = userProfile
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

    fun logout() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.logout()
                    .onSuccess {
                        _navigateToLogin.value = true
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
        _navigateToLogin.value = false
    }
} 