package com.example.dimagiassignment.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ChatDetail::class],
    version = 1
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun getChatDao(): ChatDao

    companion object {
        const val DB_NAME = "chat_database"

        @Volatile
        private var INSTANCE: ChatDatabase? = null


        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ChatDatabase::class.java,
                DB_NAME
            ).build()
            INSTANCE
        }

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ChatDatabase::class.java,
                DB_NAME
            ).build()
    }
}
