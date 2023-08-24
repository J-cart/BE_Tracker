package com.starshine.betracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starshine.betracker.model.Drink

@Database(entities = [Drink::class], version = 1)
abstract class DrinksDatabase:RoomDatabase() {
    abstract fun drinksDao():DrinksDao
}