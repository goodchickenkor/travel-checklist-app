package com.example.perfecttrip.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.perfecttrip.databinding.ItemTripBinding
import java.text.SimpleDateFormat
import java.util.*

class TripAdapter(
    private val onClick: (Trip) -> Unit,
    private val onDeleteClick: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    private val items = mutableListOf<Trip>()
    private val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

    inner class ViewHolder(val binding: ItemTripBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTripBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.titleText.text = item.title
        holder.binding.dateText.text =
            "${dateFormat.format(Date(item.startDate))} ~ ${dateFormat.format(Date(item.endDate))}"

        holder.binding.root.setOnClickListener {
            onClick(item)
        }

        holder.binding.deleteBtn.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newList: List<Trip>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size
            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }
}