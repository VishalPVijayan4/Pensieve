package com.vishalpvijayan.pensieve.ui

import androidx.lifecycle.ViewModel
import com.vishalpvijayan.pensieve.domain.AppPreferences
import com.vishalpvijayan.pensieve.reminder.ReminderScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PensieveViewModel(
    private val appPreferences: AppPreferences,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {
    private val _uiState = MutableStateFlow(PensieveUiState())
    val uiState: StateFlow<PensieveUiState> = _uiState.asStateFlow()

    fun completeSplash(): AppDestination {
        val destination = if (appPreferences.isOnboardingComplete()) {
            AppDestination.Dashboard
        } else {
            AppDestination.Onboarding
        }
        _uiState.value = _uiState.value.copy(startDestination = destination)
        return destination
    }

    fun completeOnboarding() {
        appPreferences.setOnboardingComplete(true)
        reminderScheduler.scheduleReminder()
        _uiState.value = _uiState.value.copy(startDestination = AppDestination.Dashboard)
    }

    fun enableFingerprint() {
        _uiState.value = _uiState.value.copy(fingerprintEnabled = true)
    }
}
