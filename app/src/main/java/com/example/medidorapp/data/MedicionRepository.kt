package com.example.medidorapp.data

import kotlinx.coroutines.flow.Flow

class MedicionRepository(private val dao: MedicionDao) {
    val mediciones: Flow<List<MedicionEntity>> = dao.obtenerTodas()

    suspend fun guardar(medicion: MedicionEntity) {
        dao.insertar(medicion)
    }
}