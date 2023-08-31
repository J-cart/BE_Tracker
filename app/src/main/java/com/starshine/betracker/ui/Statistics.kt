package com.starshine.betracker.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.starshine.betracker.R
import com.starshine.betracker.databinding.FragmentSignInBinding
import com.starshine.betracker.databinding.FragmentStatisticsBinding
import com.starshine.betracker.db.BudgetDatabase
import com.starshine.betracker.db.DummyDataBank
import com.starshine.betracker.ui.adapters.TransactionCategoriesAdapter
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class Statistics : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private val transactionAdapter = TransactionCategoriesAdapter()
    private var toggleState = true
    private val viewModel by activityViewModels<BudgetViewModel> {
        BudgetViewModelFactory(
            BudgetDatabase.getDatabase(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleState()
        binding.plannerRecyclerView.adapter = transactionAdapter

        binding.expenseHolder.setOnClickListener {
            toggleState = true
            toggleState()
        }
        binding.earningHolder.setOnClickListener {
            toggleState = false
            toggleState()
        }

        transactionAdapter.adapterClick {
            if (toggleState){
                findNavController().navigate(R.id.transaction, bundleOf("args" to "expense"))
            }else{
                findNavController().navigate(R.id.transaction, bundleOf("args" to "earning"))
            }
        }


    }

    private fun toggleState() {
        if (toggleState) {
            binding.expenseUnderline.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.be_red
                )
            )
            binding.earningUnderline.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.be_green
                )
            )
            loadExpense()
        } else {
            binding.expenseUnderline.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.be_green
                )
            )
            binding.earningUnderline.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.be_red
                )
            )
            loadEarning()
        }
    }

    private fun loadEarning() {
        lifecycleScope.launch {
            viewModel.totalEarningTransactions.collect {
                if (it.isEmpty()) {
                    binding.plannerRecyclerView.isVisible = false
                    binding.emptyText.isVisible = true
                    return@collect
                }
                binding.plannerRecyclerView.isVisible = true
                binding.emptyText.isVisible = false
                transactionAdapter.submitList(it)
            }
        }

        /*lifecycleScope.launch {
            viewModel.totalEarningFlow.collect {earnings->
                val filtered =earnings.filter {
                    DummyDataBank.checkIfSameMonth(it.dateCreated)
                }
                binding.totalAmountText.text ="$${filtered.sumOf { it.amount }}"
            }
        }*/

    }

    private fun loadExpense() {
        lifecycleScope.launch {
            viewModel.totalExpenseTransactions.collect {
                if (it.isEmpty()) {
                    binding.plannerRecyclerView.isVisible = false
                    binding.emptyText.isVisible = true
                    return@collect
                }
                binding.plannerRecyclerView.isVisible = true
                binding.emptyText.isVisible = false
                transactionAdapter.submitList(it)
            }
        }

        /* lifecycleScope.launch {
             viewModel.totalExpenseFlow.collect {expenses->
                val filtered =expenses.filter {
                    DummyDataBank.checkIfSameMonth(it.dateCreated)
                }
                 binding.totalAmountText.text = "$${filtered.sumOf { it.amount }}"
             }
         }*/

    }


}