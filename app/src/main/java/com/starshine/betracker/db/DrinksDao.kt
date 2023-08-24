package com.starshine.betracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.starshine.betracker.model.Drink
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink: Drink)

    @Query("SELECT * FROM `test table`")
    fun getAllDrinks(): Flow<List<Drink>>

    @Query("DELETE FROM `test table`")
    suspend fun deleteAll()

    @Query("DELETE FROM `test table` WHERE drinkId=:id ")
    suspend fun deleteDrink(id: String)

    @Query("SELECT COUNT() FROM `test table` WHERE drinkId=:id")
    suspend fun checkIfDrinkExists(id: String): Int



    /**
     * get all
     * insert
     * delete
     * check if exists
     * ----------------------
     * delete all
     * */

}
