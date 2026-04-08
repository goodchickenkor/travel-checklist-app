package com.example.perfecttrip.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Insert
    suspend fun insertTrip(trip: TripEntity): Long

    @Query("SELECT * FROM trip ORDER BY id DESC")
    fun getAllTrips(): Flow<List<TripEntity>>

    @Query("DELETE FROM trip WHERE id = :tripId")
    suspend fun deleteTripById(tripId: Int)
}