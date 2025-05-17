package com.example.medidorapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mediciones")
data class MedicionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: TipoMedidor,
    val valor: Float,
    val fecha: String
)