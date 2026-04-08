package com.example.perfecttrip.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {

    @Insert
    suspend fun insertItems(items: List<ChecklistItemEntity>)

    @Query("SELECT * FROM checklist_item WHERE tripId = :tripId")
    fun getItems(tripId: Int): Flow<List<ChecklistItemEntity>>

    @Update
    suspend fun updateItem(item: ChecklistItemEntity)
}