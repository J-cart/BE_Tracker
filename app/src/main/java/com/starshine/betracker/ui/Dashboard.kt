package com.starshine.betracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.starshine.betracker.R
import com.starshine.betracker.databinding.AddBudgetLayoutBinding
import com.starshine.betracker.databinding.FragmentDashboardBinding
import com.starshine.betracker.db.DummyDataBank
import com.starshine.betracker.ui.adapters.DashboardCategoriesAdapter


class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val categoriesAdapter = DashboardCategoriesAdapter()
    private val viewModel by activityViewModels<BudgetViewModel>()
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

        val items = listOf(
            "BankUserModel.AccountType.CURRENT",
            "BankUserModel.AccountType.SAVINGS"
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
//        binding.accountTypeDropDown.setAdapter(adapter)

    }

    private fun showAddToCartDialog(type:String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_budget_layout,mContainer, false)
        val binding = AddBudgetLayoutBinding.bind(dialogView)
        val newDialog = MaterialAlertDialogBuilder(requireContext()).create()
        if (dialogView.parent != null) {
            (dialogView.parent as ViewGroup).removeView(binding.root)
        }
        newDialog.setView(binding.root)
        newDialog.show()

        binding.apply {
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
                /*val cartItem = CartItem(response, binding.amountEdtText.text.toString().toInt())
                viewModel.addToCart(cartItem)*/
                newDialog.dismiss()
            }
            cancelBtn.setOnClickListener {
                newDialog.dismiss()
            }
        }
    }

}