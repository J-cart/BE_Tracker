package com.starshine.betracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.starshine.betracker.R
import com.starshine.betracker.databinding.AddBudgetLayoutBinding
import com.starshine.betracker.databinding.FragmentDashboardBinding
import com.starshine.betracker.db.BudgetDatabase
import com.starshine.betracker.db.DummyDataBank
import com.starshine.betracker.model.BudgetCategory
import com.starshine.betracker.model.Earning
import com.starshine.betracker.model.Expense
import com.starshine.betracker.ui.adapters.DashboardCategoriesAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val categoriesAdapter = DashboardCategoriesAdapter()
    private val viewModel by activityViewModels<BudgetViewModel> {
        BudgetViewModelFactory(
            BudgetDatabase.getDatabase(requireContext())
        )
    }
    private lateinit var mContainer: ViewGroup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        mContainer = container!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriesRecyclerView.adapter = categoriesAdapter
        categoriesAdapter.submitList(DummyDataBank.dashboardCategoryData)

        lifecycleScope.launch {
            viewModel.totalEarningFlow.collect { earnings ->
                binding.earningText.text = "$%.2f".format(earnings.sumOf { it.amount }.toFloat())
            }
        }

        lifecycleScope.launch {
            viewModel.totalExpenseFlow.collect { expenses ->
                binding.expenseText.text = "$%.2f".format(expenses.sumOf { it.amount }.toFloat())
            }
        }
        lifecycleScope.launch {
            viewModel.totalBalanceFlow.collect { balance ->
                binding.balanceText.text = "$%.2f".format(balance.toFloat())
            }
        }

        binding.apply {
            addEarningBtn.setOnClickListener {
                showAddBudgetDialog("Add Earning")
            }
            addExpenseBtn.setOnClickListener {
                showAddBudgetDialog("Add Expense")
            }
        }


    }

    private fun showAddBudgetDialog(type: String) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.add_budget_layout, mContainer, false)
        val binding = AddBudgetLayoutBinding.bind(dialogView)
        val newDialog = MaterialAlertDialogBuilder(requireContext()).create()
        if (dialogView.parent != null) {
            (dialogView.parent as ViewGroup).removeView(binding.root)
        }
        newDialog.setView(binding.root)
        newDialog.show()

        val items = BudgetCategory.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)

        binding.apply {
            budgetTypeDropDown.setAdapter(adapter)
            titleText.text = type
            addBtn.text = type
            addBtn.setOnClickListener {
                if (amountEdtText.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "You have to include the amount",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                when (type) {
                    "Add Expense" -> {
                        val item =
                            Expense(
                                amount = binding.amountEdtText.text.toString().toInt(),
                                details = binding.detailsEdtText.text.toString().trim(),
                                category = Expense.getEnum(binding.budgetTypeDropDown.text.toString()),
                                dateCreated = System.currentTimeMillis().toString()
                            )
                        viewModel.addExpense(item)
                    }
                    "Add Earning" -> {
                        val item =
                            Earning(
                                amount = binding.amountEdtText.text.toString().toInt(),
                                details = binding.detailsEdtText.text.toString().trim(),
                                category = Earning.getEnum(binding.budgetTypeDropDown.text.toString()),
                                dateCreated = System.currentTimeMillis().toString()
                            )
                        viewModel.addEarning(item)
                    }
                }
                newDialog.dismiss()
            }
            cancelBtn.setOnClickListener {
                newDialog.dismiss()
            }
        }
    }

}