package com.example.medidorapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.medidorapp.R
import com.example.medidorapp.data.Medicion
import com.example.medidorapp.data.TipoMedidor

import java.text.NumberFormat
import java.util.*

import java.text.SimpleDateFormat

@Composable
fun ListaMedicionesScreen(
    mediciones: List<Medicion>,
    onAgregarClick: () -> Unit,
    mostrarConfirmacion: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarText = stringResource(R.string.snackbar_medicion_guardada)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.fab_agregar)
                )
            }
        }
    ) { innerPadding ->

        if (mostrarConfirmacion) {
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    message = snackbarText,
                    duration = SnackbarDuration.Long
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(mediciones.asReversed()) { medicion ->
                MedicionItem(medicion)
            }
        }
    }
}

@Composable
fun MedicionItem(medicion: Medicion) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            val icon = when (medicion.tipo) {
                TipoMedidor.AGUA -> R.drawable.ic_agua
                TipoMedidor.LUZ -> R.drawable.ic_luz
                TipoMedidor.GAS -> R.drawable.ic_gas
            }

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "${stringResource(R.string.label_tipo)}: ${medicion.tipo}")
                // Formato de valor con separador de miles
                val valorFormateado = NumberFormat.getNumberInstance(Locale("es", "CL")).format(medicion.valor.toInt())
                Text(text = "${stringResource(R.string.label_valor)}: $valorFormateado")
                // Formato de fecha
                val fechaOriginal = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val fechaFormateada = try {
                    val date = fechaOriginal.parse(medicion.fecha)
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date ?: medicion.fecha)
                } catch (e: Exception) {
                    medicion.fecha // fallback si algo falla
                }
                Text(text = "${stringResource(R.string.label_fecha)}: $fechaFormateada")
            }
        }
    }
}