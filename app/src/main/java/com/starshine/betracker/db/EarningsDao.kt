package com.starshine.betracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.starshine.betracker.model.Earning
import kotlinx.coroutines.flow.Flow

@Dao
interface EarningsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEarning(earning: Earning)

    @Query("SELECT * FROM `Earning table`")
    fun getAllEarnings(): Flow<List<Earning>>

    @Query("DELETE FROM `Earning table`")
    suspend fun deleteAll()

    @Query("DELETE FROM `Earning table` WHERE tableId=:id ")
    suspend fun deleteEarning(id: String)

    @Query("SELECT COUNT() FROM `Earning table` WHERE tableId=:id")
    suspend fun checkIfEarningExists(id: String): Int



    /**
     * get all
     * insert
     * delete
     * check if exists
     * ----------------------
     * delete all
     * */

}
