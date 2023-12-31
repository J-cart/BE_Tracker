package com.starshine.betracker.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.starshine.betracker.db.BudgetDatabase
import com.starshine.betracker.model.Earning
import com.starshine.betracker.model.Expense
import com.starshine.betracker.model.TransactionsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.launch
import kotlin.math.exp

class BudgetViewModel(private val budgetDatabase: BudgetDatabase) : ViewModel() {

    private val earningsDao = budgetDatabase.earningDao()
    private val expenseDao = budgetDatabase.expenseDao()


    var totalExpenseFlow = MutableStateFlow<List<Expense>>(emptyList())
        private set

    var totalEarningFlow = MutableStateFlow<List<Earning>>(emptyList())
        private set

    var totalBalanceFlow = MutableStateFlow(0)
        private set

    var totalExpenseTransactions = MutableStateFlow<List<TransactionsModel>>(emptyList())
        private set

    var totalEarningTransactions = MutableStateFlow<List<TransactionsModel>>(emptyList())
        private set

    init {
        loadAllExpense()
        loadAllEarnings()
    }

    private fun loadAllExpense() {
        viewModelScope.launch {
            expenseDao.getAllExpenses().collect { expenses ->
                totalExpenseFlow.value = expenses
                totalBalanceFlow.value -= expenses.sumOf { it.amount }
                loadAllExpenseTransactions()
            }
        }
    }


    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.insertExpense(expense)
        }

    }

    fun getExpense(id: Int):Expense{
        return totalExpenseFlow.value.find { it.tableId == id } ?: Expense()

    }
    fun deleteAllExpense() {
        viewModelScope.launch {
            expenseDao.deleteAll()
        }

    }

    fun deleteExpense(id: String) {
        viewModelScope.launch {
            expenseDao.deleteExpense(id)
        }

    }

    //region EARNING
    private fun loadAllEarnings() {
        viewModelScope.launch {
            earningsDao.getAllEarnings().collect { earnings ->
                totalEarningFlow.value = earnings
                totalBalanceFlow.value += earnings.sumOf { it.amount }
                loadAllEarningsTransactions()
            }
        }
    }

    fun addEarning(earning: Earning) {
        viewModelScope.launch {
            earningsDao.insertEarning(earning)
        }

    }

    fun getEarning(id: Int):Earning{
       return totalEarningFlow.value.find { it.tableId == id } ?: Earning()

    }

    fun deleteAllEarning() {
        viewModelScope.launch {
            earningsDao.deleteAll()
        }

    }
    fun deleteEarning(id: String) {
        viewModelScope.launch {
            earningsDao.deleteEarning(id)
        }

    }
    //endregion


    private fun loadAllEarningsTransactions() {
        val transactions = mutableListOf<TransactionsModel>()
        val distinctGroup = totalEarningFlow.value.groupBy {
            it.category
        }
        distinctGroup.forEach { (budgetCategory, earnings) ->
            transactions.add(
                TransactionsModel(
                    category = budgetCategory,
                    transactionsSize = earnings.size,
                    totalAmount = earnings.sumOf { it.amount }
                )
            )
        }
        totalEarningTransactions.value = transactions
    }
    private fun loadAllExpenseTransactions() {
        val transactions = mutableListOf<TransactionsModel>()
        val distinctGroup = totalExpenseFlow.value.groupBy {
            it.category
        }
        distinctGroup.forEach { (budgetCategory, expense) ->
            transactions.add(
                TransactionsModel(
                    category = budgetCategory,
                    transactionsSize = expense.size,
                    totalAmount = expense.sumOf { it.amount }
                )
            )
        }
        totalExpenseTransactions.value = transactions
    }

}

class BudgetViewModelFactory(private val database: BudgetDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BudgetViewModel(database) as T
    }

}
