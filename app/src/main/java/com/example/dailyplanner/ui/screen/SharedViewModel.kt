package com.example.dailyplanner.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<String?>(null)
    val snackbarState: StateFlow<String?> = _state

    fun showSnackbarMessage(message: String) {
        _state.update {
            message
        }
    }

    fun onShown() {
        _state.update {
            null
        }
    }
}