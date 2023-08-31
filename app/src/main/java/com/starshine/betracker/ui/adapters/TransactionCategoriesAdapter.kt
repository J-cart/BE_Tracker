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
import com.starshine.betracker.model.BudgetCategory
import com.starshine.betracker.model.TransactionCategories
import com.starshine.betracker.model.TransactionsModel

class TransactionCategoriesAdapter : ListAdapter<TransactionsModel, TransactionCategoriesAdapter.ViewHolder>(diffObject) {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding = PlannerCategoriesViewholderBinding.bind(view)
            fun bind(item: TransactionsModel) {
                binding.apply {
                    typeText.text = item.category.displayName
                    amountText.text = "$${item.totalAmount}"
                    totalTransactionText.text = "${item.transactionsSize} Transaction(s)"
                    categoryImg.load(getIcon(item.category))

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
            val diffObject = object : DiffUtil.ItemCallback<TransactionsModel>() {
                override fun areItemsTheSame(oldItem: TransactionsModel, newItem: TransactionsModel): Boolean {
                    return oldItem.category == newItem.category
                }

                override fun areContentsTheSame(oldItem: TransactionsModel, newItem: TransactionsModel): Boolean {
                    return oldItem.category == newItem.category && oldItem.transactionsSize == newItem.transactionsSize
                }
            }
        }

        private var listener: ((TransactionsModel) -> Unit)? = null
        fun adapterClick(listener: (TransactionsModel) -> Unit) {
            this.listener = listener
        }

    private fun getIcon(category:BudgetCategory):Int{
        return when (category) {
            BudgetCategory.FOODANDDRINKS -> {
                R.drawable.food
            }
            BudgetCategory.SHOPPING -> {
                R.drawable.shopping_icon
            }
            BudgetCategory.CLOTHESANDSHOES -> {
                R.drawable.shopping_icon
            }
            BudgetCategory.CLOTHINGBRANDS -> {
                R.drawable.headset_icon
            }
            BudgetCategory.GYM -> {
                R.drawable.gym_icon
            }
            else -> R.drawable.food
        }
    }

}