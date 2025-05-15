package com.example.fishka.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fishka.database.entity.CaughtFish

@Database(entities = [CaughtFish::class], version = 1)
abstract class CaughtFishDatabase : RoomDatabase() {
    abstract fun caughtFishDao(): CaughtFishDao

    companion object {
        @Volatile private var INSTANCE: CaughtFishDatabase? = null

        fun getInstance(context: Context): CaughtFishDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CaughtFishDatabase::class.java,
                    "fishes_db"
                ).build().also { INSTANCE = it }
            }
    }
}