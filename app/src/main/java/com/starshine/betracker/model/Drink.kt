package com.starshine.betracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Test Table")
data class Drink(
    @PrimaryKey(autoGenerate = true)
    val tableId:Int =0,
    val drinkId: String,
    val drinkName: String,
    val drinkImgUrl: String,
):Parcelable