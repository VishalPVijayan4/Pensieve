package com.vishalpvijayan.pensieve.ui

data class PensieveUiState(
    val startDestination: AppDestination = AppDestination.Splash,
    val fingerprintEnabled: Boolean = false
)
