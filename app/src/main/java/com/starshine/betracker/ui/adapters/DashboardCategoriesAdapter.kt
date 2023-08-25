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

class DashboardCategoriesAdapter : ListAdapter<String, DashboardCategoriesAdapter.ViewHolder>(diffObject) {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding = CategoryItemViewholderBinding.bind(view)
            fun bind(item: String) {
                binding.apply {
                    categoryText.text = item
                    root.clipToOutline = true
                   /* categoryImg.load(item.images) {
                        crossfade(true)
                        error(R.drawable.ic_launcher_foreground)
                        placeholder(R.drawable.ic_launcher_foreground)
                    }*/
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.category_item_viewholder, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pos = getItem(position)
            holder.bind(pos)
        }

        companion object {
            val diffObject = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem.hashCode() == newItem.hashCode() && oldItem.length == newItem.length
                }
            }
        }

        private var listener: ((String) -> Unit)? = null
        fun adapterClick(listener: (String) -> Unit) {
            this.listener = listener
        }

}