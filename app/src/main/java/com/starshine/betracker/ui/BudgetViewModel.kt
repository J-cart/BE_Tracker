package com.starshine.betracker.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.starshine.betracker.db.BudgetDatabase
import com.starshine.betracker.model.Earning
import com.starshine.betracker.model.Expense
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

    init {
        loadAllExpense()
        loadAllEarnings()
    }
    private fun loadAllExpense() {
        viewModelScope.launch {
            expenseDao.getAllExpenses().collect {expenses->
                totalExpenseFlow.value = expenses
                totalBalanceFlow.value -= expenses.sumOf { it.amount }
            }
        }
    }


    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.insertExpense(expense)
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
            earningsDao.getAllEarnings().collect {earnings->
                totalEarningFlow.value = earnings
                totalBalanceFlow.value += earnings.sumOf { it.amount }
            }
        }
    }

    fun addEarning(earning: Earning) {
        viewModelScope.launch {
            earningsDao.insertEarning(earning)
        }

    }
    fun deleteEarning(id: String) {
        viewModelScope.launch {
            earningsDao.deleteEarning(id)
        }

    }
    //endregion


}
class BudgetViewModelFactory(private val database: BudgetDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BudgetViewModel(database) as T
    }

}
