package com.starshine.betracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.starshine.betracker.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM `Expense table`")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("DELETE FROM `Expense table`")
    suspend fun deleteAll()

    @Query("DELETE FROM `Expense table` WHERE tableId=:id ")
    suspend fun deleteExpense(id: String)

    @Query("SELECT COUNT() FROM `Expense table` WHERE tableId=:id")
    suspend fun checkIfExpenseExists(id: String): Int



    /**
     * get all
     * insert
     * delete
     * check if exists
     * ----------------------
     * delete all
     * */

}
