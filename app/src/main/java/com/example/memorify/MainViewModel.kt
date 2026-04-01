package com.example.memorify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.repository.AuthRepository
import com.example.core_ui.navigation.GraphRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _startDestination = MutableStateFlow<GraphRoute?>(null)
    val startDestination: StateFlow<GraphRoute?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val isLoggedIn = authRepository.currentUser.first() != null
            _startDestination.value = if (isLoggedIn) GraphRoute.Home else GraphRoute.Auth
        }
    }
}