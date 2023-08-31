package com.starshine.betracker.db

import android.os.Build
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

    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkIfSameMonth(date: String): Boolean {
        val format =
            DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)
        val localDate = LocalDateTime.parse(date, format)

         return LocalDateTime.now().month ==  localDate.month

    }



}