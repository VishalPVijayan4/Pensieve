package com.vishalpvijayan.pensieve.domain

interface AppPreferences {
    fun isOnboardingComplete(): Boolean
    fun setOnboardingComplete(complete: Boolean)
}
