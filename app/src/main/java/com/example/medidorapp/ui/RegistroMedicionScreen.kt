package com.example.medidorapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.medidorapp.R
import com.example.medidorapp.data.Medicion
import com.example.medidorapp.data.TipoMedidor
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions

@Composable
fun RegistroMedicionScreen(
    onGuardar: (Medicion) -> Unit,
    navController: NavHostController
) {
    var valorTexto by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf(TipoMedidor.AGUA) }
    var menuExpandido by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = stringResource(R.string.title_nueva_medicion), style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = valorTexto,
                onValueChange = { input ->
                    valorTexto = input.filter { it.isDigit() }
                },
                label = { Text(stringResource(R.string.label_valor_medidor)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = showError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Box {
                OutlinedButton(
                    onClick = { menuExpandido = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("${stringResource(R.string.label_tipo)}: ${tipoSeleccionado.name}")
                }
                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    TipoMedidor.entries.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo.name) },
                            onClick = {
                                tipoSeleccionado = tipo
                                menuExpandido = false
                            }
                        )
                    }
                }
            }

            Text("${stringResource(R.string.label_fecha)}: $fechaActual")

            Button(
                onClick = {
                    val valor = valorTexto.toFloatOrNull()
                    if (valor != null && valor > 0f) {
                        val nuevaMedicion = Medicion(
                            tipo = tipoSeleccionado,
                            valor = valor,
                            fecha = fechaActual
                        )
                        onGuardar(nuevaMedicion)

                        println("✅ Enviando confirmación de medición...")

                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("medicion_guardada", true)

                        navController.popBackStack()
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.btn_guardar))
            }
        }
    }
}
