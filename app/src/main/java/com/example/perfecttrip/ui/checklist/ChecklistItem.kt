package com.example.perfecttrip.ui.checklist

import com.example.perfecttrip.model.Category

sealed class ChecklistItem {

    data class Header(
        val title: String
    ) : ChecklistItem()

    data class Item(
        val id: Int = 0,
        val name: String,
        var isChecked: Boolean = false,
        val category: Category
    ) : ChecklistItem()
}