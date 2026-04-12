package com.example.perfecttrip.ui.create

import androidx.lifecycle.ViewModel
import com.example.perfecttrip.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TripDraft(
    val title: String = "",
    val startDate: Long? = null,
    val endDate: Long? = null,
    val categories: Set<Category> = emptySet()
)

class TripCreateViewModel : ViewModel() {

    private val _draft = MutableStateFlow(TripDraft())
    val draft = _draft.asStateFlow()

    fun reset() {
        _draft.value = TripDraft()
    }

    // Step1
    fun setStep1Data(title: String, start: Long, end: Long) {
        _draft.value = _draft.value.copy(
            title = title,
            startDate = start,
            endDate = end
        )
    }

    // Step2 + Step3 통합
    fun setCategories(categories: Set<Category>) {
        _draft.value = _draft.value.copy(
            categories = categories
        )
    }

    fun getDraft(): TripDraft {
        return _draft.value
    }
}