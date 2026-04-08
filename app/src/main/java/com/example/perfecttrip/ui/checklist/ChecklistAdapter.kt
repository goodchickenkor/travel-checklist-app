package com.example.perfecttrip.ui.checklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perfecttrip.R
import com.example.perfecttrip.databinding.ItemChecklistBinding
import com.example.perfecttrip.model.Category
import com.example.perfecttrip.model.displayName

class ChecklistAdapter(
    private val items: MutableList<ChecklistItem>,
    private val onCheckedChange: (ChecklistItem.Item) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ChecklistItem.Header -> TYPE_HEADER
            is ChecklistItem.Item -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val binding = ItemChecklistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ItemViewHolder(binding)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ChecklistItem.Header -> (holder as HeaderViewHolder).bind(item)
            is ChecklistItem.Item -> (holder as ItemViewHolder).bind(item)
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val text: TextView = view.findViewById(R.id.headerText)

        fun bind(item: ChecklistItem.Header) {
            text.text = item.title
        }
    }

    inner class ItemViewHolder(val binding: ItemChecklistBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChecklistItem.Item) {
            binding.itemText.text = item.name

            binding.checkBox.setOnCheckedChangeListener(null)
            binding.checkBox.isChecked = item.isChecked

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
                onCheckedChange(item)

                reorderWithinCategory(item.category)
            }
        }
    }

    fun submitList(newList: MutableList<ChecklistItem>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    fun getItems(): List<ChecklistItem> = items

    private fun reorderWithinCategory(category: Category) {
        val headerTitle = category.displayName()

        val headerIndex = items.indexOfFirst {
            it is ChecklistItem.Header && it.title == headerTitle
        }
        if (headerIndex == -1) return

        val nextHeaderIndex = ((headerIndex + 1) until items.size)
            .firstOrNull { items[it] is ChecklistItem.Header }
            ?: items.size

        val categoryBlock = items.subList(headerIndex + 1, nextHeaderIndex)
            .filterIsInstance<ChecklistItem.Item>()

        val uncheckedItems = categoryBlock.filter { !it.isChecked }
        val checkedItems = categoryBlock.filter { it.isChecked }

        val reorderedBlock = uncheckedItems + checkedItems

        for (i in reorderedBlock.indices) {
            items[headerIndex + 1 + i] = reorderedBlock[i]
        }

        notifyDataSetChanged()
    }
}