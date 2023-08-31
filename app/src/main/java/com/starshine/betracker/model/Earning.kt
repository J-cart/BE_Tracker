package com.starshine.betracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Earning Table")
data class Earning(
    @PrimaryKey(autoGenerate = true)
    val tableId:Int =0,
    val amount: Int,
    val details: String,
    val category: BudgetCategory,
    val dateCreated: String,
):Parcelable