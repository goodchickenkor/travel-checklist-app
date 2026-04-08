package com.example.perfecttrip.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfecttrip.data.local.entity.AppDatabase
import com.example.perfecttrip.data.local.entity.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TripListViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repo = TripRepository(db)

    val tripList = repo.getTrips()

    fun deleteTrip(tripId: Int) {
        viewModelScope.launch {
            repo.deleteTrip(tripId)
        }
    }
}