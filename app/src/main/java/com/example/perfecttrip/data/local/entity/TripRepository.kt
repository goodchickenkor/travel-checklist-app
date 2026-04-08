package com.example.perfecttrip.data.local.entity

class TripRepository(private val db: AppDatabase) {

    suspend fun insertTripWithItems(
        trip: TripEntity,
        items: List<ChecklistItemEntity>
    ) {
        val tripId = db.tripDao().insertTrip(trip).toInt()

        val updatedItems = items.map {
            it.copy(tripId = tripId)
        }

        db.checklistDao().insertItems(updatedItems)
    }

    fun getTrips() = db.tripDao().getAllTrips()

    suspend fun deleteTrip(tripId: Int) {
        db.tripDao().deleteTripById(tripId)
    }
}