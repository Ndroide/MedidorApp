package com.example.medidorapp.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.medidorapp.data.*
import kotlinx.coroutines.launch

import androidx.lifecycle.asLiveData

class MedicionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = MedicionRepository(db.medicionDao())

    //val mediciones = repository.mediciones.asLiveData()
    val mediciones = repository.mediciones

    fun guardarMedicion(medicion: Medicion) {
        val entity = MedicionEntity(
            tipo = medicion.tipo,
            valor = medicion.valor,
            fecha = medicion.fecha
        )
        viewModelScope.launch {
            repository.guardar(entity)
        }
    }
}