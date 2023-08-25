package com.starshine.betracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.starshine.betracker.R
import com.starshine.betracker.databinding.FragmentSignInBinding
import com.starshine.betracker.databinding.FragmentStatisticsBinding
import com.starshine.betracker.db.DummyDataBank
import com.starshine.betracker.ui.adapters.TransactionCategoriesAdapter

class Statistics : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private val transactionAdapter = TransactionCategoriesAdapter()
    private var toggleState = true
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
        transactionAdapter.submitList(DummyDataBank.transactionCategoryData)
        binding.expenseHolder.setOnClickListener {
            toggleState = true
            toggleState()
            transactionAdapter.submitList(DummyDataBank.transactionCategoryData.shuffled())
        }
        binding.earningHolder.setOnClickListener {
            toggleState = false
            toggleState()
            transactionAdapter.submitList(DummyDataBank.transactionCategoryData.reversed())
        }
    }

    private fun toggleState(){
        if (toggleState){
            binding.expenseUnderline.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.be_red))
            binding.earningUnderline.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.be_green))
        }else{
            binding.expenseUnderline.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.be_green))
            binding.earningUnderline.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.be_red))
        }
    }


}