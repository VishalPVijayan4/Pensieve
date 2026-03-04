package com.vishalpvijayan.pensieve.onboarding

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

data class OnboardingPage(
    val title: String,
    val body: String
)

@Composable
fun OnboardingScreen(
    onEnableFingerprint: () -> Unit,
    onSkip: () -> Unit
) {
    val pages = remember {
        listOf(
            OnboardingPage(
                title = "Your data stays with you.",
                body = "Everything is stored securely on your device. No servers. No tracking. No cloud syncing."
            ),
            OnboardingPage(
                title = "Capture everything.",
                body = "Notes, checklists, audio recordings, drawings, reminders. Organized with tags and smart search."
            ),
            OnboardingPage(
                title = "Unlock with your fingerprint.",
                body = "Enable biometric authentication for quick and secure access."
            )
        )
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 48.dp),
            modifier = Modifier.weight(1f)
        ) { page ->
            val currentPage = pages[page]
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = currentPage.title, style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = currentPage.body,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        if (pagerState.currentPage == pages.lastIndex) {
            Button(
                onClick = {
                    launchBiometricPrompt(context, onSuccess = onEnableFingerprint)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text("Enable Fingerprint")
            }
        }

        OutlinedButton(
            onClick = onSkip,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(text = "Skip")
        }
    }
}

private fun launchBiometricPrompt(context: Context, onSuccess: () -> Unit) {
    val activity = context as? FragmentActivity ?: return
    val biometricManager = BiometricManager.from(context)
    val canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)

    if (canAuthenticate != BiometricManager.BIOMETRIC_SUCCESS) {
        onSuccess()
        return
    }

    val executor = ContextCompat.getMainExecutor(context)
    val prompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }
        }
    )

    val info = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Secure Pensieve")
        .setSubtitle("Use fingerprint to unlock")
        .setNegativeButtonText("Cancel")
        .build()

    prompt.authenticate(info)
}
