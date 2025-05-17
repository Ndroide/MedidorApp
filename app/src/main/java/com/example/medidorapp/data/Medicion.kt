package com.example.medidorapp.data

enum class TipoMedidor {
    AGUA, LUZ, GAS
}

data class Medicion(
    val tipo: TipoMedidor,
    val valor: Float,
    val fecha: String
)