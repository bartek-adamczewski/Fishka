package com.example.fishka.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fishka.database.entity.CaughtFish
import kotlinx.coroutines.flow.Flow

@Dao
interface CaughtFishDao {
    @Query("SELECT * FROM fishes ORDER BY timestamp DESC")
    fun getAllCatches(): Flow<List<CaughtFish>>

    @Query("SELECT * FROM fishes WHERE id = :id")
    fun getCatchById(id: Int): Flow<CaughtFish>

    @Insert
    suspend fun insert(caughtFish: CaughtFish): Long

    @Delete
    suspend fun delete(caughtFish: CaughtFish)
}