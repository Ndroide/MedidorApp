package com.example.medidorapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicionDao {
    @Query("SELECT * FROM mediciones ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<MedicionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(medicion: MedicionEntity)
}