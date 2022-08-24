package com.example.dimagiassignment.local
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dimagiassignment.local.ChatDetail.Companion.TABLE_NAME

/**
 * Data class for Database entity and Serialization.
 */
@Entity(tableName = TABLE_NAME)
data class ChatDetail(
    @PrimaryKey
    var id: Int? = 0,
    var message:String? = ""
) {
    companion object {
        const val TABLE_NAME = "chat_detail"
    }
}