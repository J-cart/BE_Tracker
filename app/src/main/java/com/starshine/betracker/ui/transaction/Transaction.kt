package com.starshine.betracker.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.starshine.betracker.R
import com.starshine.betracker.databinding.FragmentSettingsBinding
import com.starshine.betracker.databinding.FragmentTransactionBinding
import com.starshine.betracker.db.BudgetDatabase
import com.starshine.betracker.model.BudgetCategory
import com.starshine.betracker.ui.BudgetViewModel
import com.starshine.betracker.ui.BudgetViewModelFactory
import com.starshine.betracker.ui.adapters.EarningTransactionsAdapter
import com.starshine.betracker.ui.adapters.ExpenseTransactionsAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.exp

class Transaction : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private val viewModel by activityViewModels<BudgetViewModel> {
        BudgetViewModelFactory(
            BudgetDatabase.getDatabase(requireContext())
        )
    }

    private val earningAdapter = EarningTransactionsAdapter()
    private val expenseAdapter = ExpenseTransactionsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getString("args")
        val categoryArgs = arguments?.getString("args2") ?: "None"
        if (args == "expense"){
            loadExpense(categoryArgs)
        }else{
            loadEarning(categoryArgs)
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadExpense(category:String){
        binding.headerText.text = "Expenses"
        binding.plannerRecyclerView.adapter = expenseAdapter
        expenseAdapter.adapterClick {
            findNavController().navigate(R.id.transactionDetails, bundleOf("args" to it.tableId,"args2" to "expense"))
        }
        lifecycleScope.launch {
            viewModel.totalExpenseFlow.collect{expense->
                binding.deleteBtn.isVisible = expense.isNotEmpty()
                if (expense.isEmpty()) {
                    binding.plannerRecyclerView.isVisible = false
                    binding.emptyText.isVisible = true
                    return@collect
                }
                binding.plannerRecyclerView.isVisible = true
                binding.emptyText.isVisible = false
                expenseAdapter.submitList(expense.filter { it.category.displayName == category })
                attachExpenseSwipeToDelete(expenseAdapter)
                binding.deleteBtn.setOnClickListener {
                    viewModel.deleteAllExpense()
                }
            }
        }
    }
    private fun loadEarning(category: String){
        binding.headerText.text = "Earnings"
        binding.plannerRecyclerView.adapter = earningAdapter
        earningAdapter.adapterClick {
            findNavController().navigate(R.id.transactionDetails, bundleOf("args" to it.tableId,"args2" to "earning"))
        }
        lifecycleScope.launch {
            viewModel.totalEarningFlow.collect{earnings->
                binding.deleteBtn.isVisible = earnings.isNotEmpty()
                if (earnings.isEmpty()) {
                    binding.plannerRecyclerView.isVisible = false
                    binding.emptyText.isVisible = true
                    return@collect
                }
                binding.plannerRecyclerView.isVisible = true
                binding.emptyText.isVisible = false
                earningAdapter.submitList(earnings.filter { it.category.displayName == category })
                attachEarningSwipeToDelete(earningAdapter)
                binding.deleteBtn.setOnClickListener {
                    viewModel.deleteAllEarning()
                }
            }
        }
    }

    private fun attachEarningSwipeToDelete(earningeAdapter: EarningTransactionsAdapter){
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                val earning = earningeAdapter.currentList[position]
                viewModel.deleteEarning(earning.tableId.toString())
                Snackbar.make(requireView(), "Transaction deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("UNDO delete") {
                        viewModel.addEarning(earning)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.plannerRecyclerView)
        }

    }
    private fun attachExpenseSwipeToDelete(adapter: ExpenseTransactionsAdapter){
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                val expense = adapter.currentList[position]
                viewModel.deleteExpense(expense.tableId.toString())
                Snackbar.make(requireView(), "Transaction deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("UNDO delete") {
                        viewModel.addExpense(expense)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.plannerRecyclerView)
        }

    }
}