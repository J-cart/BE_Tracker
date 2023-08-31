package com.starshine.betracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Expense Table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val tableId:Int =0,
    val amount: Int =0,
    val details: String="",
    val category: BudgetCategory = BudgetCategory.NONE,
    val dateCreated: String="",
):Parcelable{
    companion object{
        fun getEnum(name: String): BudgetCategory {
            return when (name) {
                BudgetCategory.FOODANDDRINKS.displayName -> {
                    BudgetCategory.FOODANDDRINKS
                }
                BudgetCategory.SHOPPING.displayName -> {
                    BudgetCategory.SHOPPING
                }
                BudgetCategory.CLOTHESANDSHOES.displayName -> {
                    BudgetCategory.CLOTHESANDSHOES
                }
                BudgetCategory.CLOTHINGBRANDS.displayName -> {
                    BudgetCategory.CLOTHINGBRANDS
                }
                BudgetCategory.GYM.displayName -> {
                    BudgetCategory.GYM
                }
                else -> BudgetCategory.NONE
            }
        }

    }
}