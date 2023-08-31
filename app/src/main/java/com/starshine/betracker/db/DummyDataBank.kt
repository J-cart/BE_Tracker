package com.starshine.betracker.db

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.starshine.betracker.R
import com.starshine.betracker.model.TransactionCategories
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DummyDataBank {
    val transactionCategoryData =
        listOf(
            TransactionCategories(
                title = "Foods & Drinks",
                amount = 12,
                total_amount = 23,
                icon = R.drawable.food
            ), TransactionCategories(
                title = "Clothes & Shoes",
                amount = 1,
                total_amount = 230,
                icon = R.drawable.shopping_icon
            ), TransactionCategories(
                title = "Entertainment",
                amount = 120,
                total_amount = 2300,
                icon = R.drawable.headset_icon
            ), TransactionCategories(
                title = "Gym Instructor",
                amount = 7,
                total_amount = 290,
                icon = R.drawable.gym_icon
            ), TransactionCategories(
                title = "Clothing Brand",
                amount = 6,
                total_amount = 257,
                icon = R.drawable.shopping_icon
            ), TransactionCategories(
                title = "OnlyFans",
                amount = 1,
                total_amount = 130,
                icon = R.drawable.gym_icon
            )
        )

    val dashboardCategoryData =
        listOf(
            "Transfer",
            "Pay a bill",
            "Airtime/Data",
            "Savings"
        )

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkIfSameMonth(date: Long): Boolean {

        val systemTimeFormatter1 =
            SimpleDateFormat("EEE, yyyy-MM-dd hh:mm a", Locale.getDefault()).format(date)
        val month = SimpleDateFormat("EEE, yyyy-MM-dd hh:mm a", Locale.getDefault()).parse(
            systemTimeFormatter1
        )?.month ?: 0
        val currentMonth = LocalDateTime.now().month.value

        return currentMonth == month + 1


    }

    fun getDate(date: Long): String {
        return SimpleDateFormat("EEE, yyyy-MM-dd hh:mm a", Locale.getDefault()).format(date)
    }

    fun getShortDate(date: Long): String {
        return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(date)
    }


}