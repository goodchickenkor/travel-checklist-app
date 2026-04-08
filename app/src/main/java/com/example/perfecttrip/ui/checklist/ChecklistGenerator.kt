package com.example.perfecttrip.ui.checklist

import com.example.perfecttrip.data.ChecklistRepository
import com.example.perfecttrip.model.Category


object ChecklistGenerator {

    fun generate(categories: List<Category>): MutableList<ChecklistItem> {
        return ChecklistRepository
            .getGroupedItems(categories)
            .toMutableList()
    }
}