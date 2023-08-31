package com.starshine.betracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starshine.betracker.model.Earning
import com.starshine.betracker.model.Expense

@Database(entities = [Expense::class,Earning::class], version = 1)
abstract class BudgetDatabase:RoomDatabase() {
    abstract fun earningDao():EarningsDao
    abstract fun expenseDao():ExpenseDao
}