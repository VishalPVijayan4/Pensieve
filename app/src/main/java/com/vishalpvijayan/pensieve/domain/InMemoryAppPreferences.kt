package com.vishalpvijayan.pensieve.domain

class InMemoryAppPreferences : AppPreferences {
    private var onboardingComplete = false

    override fun isOnboardingComplete(): Boolean = onboardingComplete

    override fun setOnboardingComplete(complete: Boolean) {
        onboardingComplete = complete
    }
}
