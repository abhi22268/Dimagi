package com.example.dimagiassignment.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {

    /**
     * Duplicate values are replaced in the table.
     */

//    @Query("SELECT * FROM ${ChatDetail.TABLE_NAME}")
//    suspend fun getChatHistory(): List<ChatDetail>


}
