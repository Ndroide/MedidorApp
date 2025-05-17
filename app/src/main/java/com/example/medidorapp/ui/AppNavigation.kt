package com.example.medidorapp.ui

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.medidorapp.data.Medicion
import com.example.medidorapp.data.MedicionEntity

@Composable
fun AppNavigation(app: Application) {
    val navController = rememberNavController()

    val factory = MedicionViewModelFactory(app)
    val viewModel: MedicionViewModel = viewModel(factory = factory)

    val medicionesRoom by viewModel.mediciones.collectAsState(initial = emptyList())

    val mediciones: List<Medicion> = medicionesRoom.map { entity ->
        Medicion(
            tipo = entity.tipo,
            valor = entity.valor,
            fecha = entity.fecha
        )
    }

    NavHost(navController = navController, startDestination = "listado") {
        composable("listado") {
            val currentEntry = navController.currentBackStackEntry
            val savedStateHandle = currentEntry?.savedStateHandle
            val mostrarMensaje = savedStateHandle?.get<Boolean>("medicion_guardada") == true

            println("🧭 mostrarMensaje = $mostrarMensaje")

            if (mostrarMensaje) {
                savedStateHandle?.set("medicion_guardada", false)
            }

            ListaMedicionesScreen(
                mediciones = mediciones,
                onAgregarClick = { navController.navigate("registro") },
                mostrarConfirmacion = mostrarMensaje
            )
        }

        composable("registro") {
            RegistroMedicionScreen(
                onGuardar = { nuevaMedicion ->
                    viewModel.guardarMedicion(nuevaMedicion)
                },
                navController = navController
            )
        }
    }
}