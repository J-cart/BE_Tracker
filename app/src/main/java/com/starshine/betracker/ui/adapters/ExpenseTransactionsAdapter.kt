package com.starshine.betracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.starshine.betracker.R
import com.starshine.betracker.databinding.TransactionsViewholderBinding
import com.starshine.betracker.db.DummyDataBank
import com.starshine.betracker.model.Expense

class ExpenseTransactionsAdapter : ListAdapter<Expense, ExpenseTransactionsAdapter.ViewHolder>(diffObject) {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding = TransactionsViewholderBinding.bind(view)
            fun bind(item: Expense) {
                binding.apply {
                    detailsText.text = item.details
                    dateText.text = DummyDataBank.getShortDate(item.dateCreated.toLong())
                    amountText.text = "$${item.amount}"
                    root.setOnClickListener {
                        listener?.let { it(item) }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.transactions_viewholder, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pos = getItem(position)
            holder.bind(pos)
        }

        companion object {
            val diffObject = object : DiffUtil.ItemCallback<Expense>() {
                override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                    return oldItem.tableId == newItem.tableId
                }

                override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                    return oldItem.tableId == newItem.tableId && oldItem.details == newItem.details
                }
            }
        }

        private var listener: ((Expense) -> Unit)? = null
        fun adapterClick(listener: (Expense) -> Unit) {
            this.listener = listener
        }

}