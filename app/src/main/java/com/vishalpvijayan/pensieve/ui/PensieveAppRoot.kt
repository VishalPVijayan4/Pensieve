package com.vishalpvijayan.pensieve.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishalpvijayan.pensieve.dashboard.DashboardScreen
import com.vishalpvijayan.pensieve.onboarding.OnboardingScreen
import com.vishalpvijayan.pensieve.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PensieveAppRoot(viewModel: PensieveViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = AppDestination.Splash.name) {
        composable(AppDestination.Splash.name) {
            SplashScreen(onSplashFinished = {
                val route = viewModel.completeSplash().name
                navController.navigate(route) {
                    popUpTo(AppDestination.Splash.name) { inclusive = true }
                }
            })
        }
        composable(AppDestination.Onboarding.name) {
            OnboardingScreen(
                onEnableFingerprint = {
                    viewModel.enableFingerprint()
                    viewModel.completeOnboarding()
                    navController.navigate(AppDestination.Dashboard.name) {
                        popUpTo(AppDestination.Onboarding.name) { inclusive = true }
                    }
                },
                onSkip = {
                    viewModel.completeOnboarding()
                    navController.navigate(AppDestination.Dashboard.name) {
                        popUpTo(AppDestination.Onboarding.name) { inclusive = true }
                    }
                }
            )
        }
        composable(AppDestination.Dashboard.name) {
            DashboardScreen(isFingerprintEnabled = uiState.fingerprintEnabled)
        }
    }
}
