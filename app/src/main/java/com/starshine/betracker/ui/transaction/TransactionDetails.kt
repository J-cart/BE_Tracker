package com.starshine.betracker.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.starshine.betracker.R
import com.starshine.betracker.databinding.FragmentSettingsBinding
import com.starshine.betracker.databinding.FragmentTransactionDetailsBinding
import com.starshine.betracker.db.BudgetDatabase
import com.starshine.betracker.db.DummyDataBank
import com.starshine.betracker.ui.BudgetViewModel
import com.starshine.betracker.ui.BudgetViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.exp

class TransactionDetails : Fragment() {
    private lateinit var binding: FragmentTransactionDetailsBinding
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
        binding = FragmentTransactionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getInt("args")
        val categoryArgs = arguments?.getString("args2") ?: ""

        if (categoryArgs == "earning"){
            binding.headerText.text = "Earning Details"
            lifecycleScope.launch {
                args?.let {
                   val earning = viewModel.getEarning(it)
                    setView(
                        earning.amount.toString(),
                        earning.details,
                        earning.dateCreated
                    )
                }?: Toast.makeText(requireContext(),"No transaction found",Toast.LENGTH_SHORT).show()

            }
        }else{
            binding.headerText.text = "Expense Details"
            lifecycleScope.launch {
                args?.let {
                    val expense = viewModel.getExpense(it)
                    setView(
                        expense.amount.toString(),
                        expense.details,
                        expense.dateCreated
                    )
                }?: Toast.makeText(requireContext(),"No transaction found",Toast.LENGTH_SHORT).show()

            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setView(amount:String, details: String, date:String){

        binding.apply {
            detailsText.text = details
            totalAmountText.text = "$$amount"
            dateText.text = DummyDataBank.getDate(date.toLong())
        }

    }
}