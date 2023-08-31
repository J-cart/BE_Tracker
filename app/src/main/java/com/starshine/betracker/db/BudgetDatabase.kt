package com.starshine.betracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.starshine.betracker.model.Earning
import com.starshine.betracker.model.Expense

@Database(entities = [Expense::class,Earning::class], version = 1)
abstract class BudgetDatabase:RoomDatabase() {
    abstract fun earningDao():EarningsDao
    abstract fun expenseDao():ExpenseDao

    companion object {
        private var appDataBase: BudgetDatabase? = null

        fun getDatabase(context: Context): BudgetDatabase {
            if (appDataBase != null)
                return appDataBase!!

            appDataBase = Room.databaseBuilder(
                context.applicationContext,
                BudgetDatabase::class.java,
                "BUDGET_DATABASE"
            )
                .build()
            return appDataBase!!
        }
    }

}