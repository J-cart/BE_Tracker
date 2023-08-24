package com.starshine.betracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.starshine.betracker.R
import com.starshine.betracker.databinding.FragmentSignInBinding
import com.starshine.betracker.databinding.FragmentStatisticsBinding

class Statistics : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

}