package com.vishalpvijayan.pensieve

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vishalpvijayan.pensieve.ui.PensieveAppRoot
import com.vishalpvijayan.pensieve.ui.theme.PensieveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PensieveTheme {
                PensieveAppRoot()
            }
        }
    }
}
