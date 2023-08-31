package com.starshine.betracker.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.starshine.betracker.R
import com.starshine.betracker.databinding.FragmentSettingsBinding
import com.starshine.betracker.databinding.FragmentTransactionDetailsBinding

class TransactionDetails : Fragment() {
    private lateinit var binding: FragmentTransactionDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}