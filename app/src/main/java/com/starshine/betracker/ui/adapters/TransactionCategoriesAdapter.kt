package com.starshine.betracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.starshine.betracker.R
import com.starshine.betracker.databinding.CategoryItemViewholderBinding
import com.starshine.betracker.databinding.PlannerCategoriesViewholderBinding
import com.starshine.betracker.model.TransactionCategories

class TransactionCategoriesAdapter : ListAdapter<TransactionCategories, TransactionCategoriesAdapter.ViewHolder>(diffObject) {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding = PlannerCategoriesViewholderBinding.bind(view)
            fun bind(item: TransactionCategories) {
                binding.apply {
                    typeText.text = item.title
                    amountText.text = "$${item.total_amount}"
                    totalTransactionText.text = "${item.amount} Transaction(s)"
                    categoryImg.load(item.icon)

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.planner_categories_viewholder, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pos = getItem(position)
            holder.bind(pos)
        }

        companion object {
            val diffObject = object : DiffUtil.ItemCallback<TransactionCategories>() {
                override fun areItemsTheSame(oldItem: TransactionCategories, newItem: TransactionCategories): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(oldItem: TransactionCategories, newItem: TransactionCategories): Boolean {
                    return oldItem.title == newItem.title && oldItem.amount == newItem.amount
                }
            }
        }

        private var listener: ((TransactionCategories) -> Unit)? = null
        fun adapterClick(listener: (TransactionCategories) -> Unit) {
            this.listener = listener
        }

}