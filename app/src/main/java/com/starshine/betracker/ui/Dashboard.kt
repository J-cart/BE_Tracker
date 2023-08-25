package com.starshine.betracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.starshine.betracker.R
import com.starshine.betracker.databinding.FragmentDashboardBinding
import com.starshine.betracker.db.DummyDataBank
import com.starshine.betracker.ui.adapters.DashboardCategoriesAdapter


class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val categoriesAdapter = DashboardCategoriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriesRecyclerView.adapter = categoriesAdapter
        categoriesAdapter.submitList(DummyDataBank.dashboardCategoryData)

    }

}