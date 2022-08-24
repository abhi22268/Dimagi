package com.example.dimagiassignment

import com.example.dimagiassignment.model.ChatMessage
import com.example.dimagiassignment.local.ChatDatabase


class Repository(private val db: ChatDatabase?) {

    fun insertChatMessage(chatMessage: ChatMessage) {
        // insert into DB
        TempCache.insertChatMessage(chatMessage)
    }

    fun getChatHistory() : ArrayList<ChatMessage> {
      // fetch from DB
        return TempCache.getChatHistory()
    }


}