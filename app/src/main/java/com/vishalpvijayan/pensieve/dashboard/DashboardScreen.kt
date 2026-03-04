package com.vishalpvijayan.pensieve.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(isFingerprintEnabled: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Pensieve", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Welcome back. Capture your private thoughts and organize your day.",
            style = MaterialTheme.typography.bodyLarge
        )
        Card {
            Text(
                text = if (isFingerprintEnabled) {
                    "Fingerprint security is enabled"
                } else {
                    "Fingerprint security is not enabled"
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
