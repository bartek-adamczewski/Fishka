package com.example.fishka.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fishes")
data class CaughtFish(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val size: Float,
    val weight: Float,
    val note: String?,
    val photoUri: String?,
    val timestamp: Long = System.currentTimeMillis()
)