package com.starshine.betracker.model

data class TransactionsModel(
    val category: BudgetCategory,
    val transactionsSize:Int,
    val totalAmount:Int
)