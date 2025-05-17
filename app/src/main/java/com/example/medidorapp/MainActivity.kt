package com.example.medidorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.medidorapp.ui.theme.MedidorAppTheme
import com.example.medidorapp.ui.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MedidorAppTheme {
                Surface {
                    AppNavigation(application)
                }
            }
        }
    }
}