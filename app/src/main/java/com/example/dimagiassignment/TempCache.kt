package com.example.dimagiassignment

import com.example.dimagiassignment.model.ChatMessage

object TempCache {

    private val chatHistory = ArrayList<ChatMessage>()

    fun getChatHistory(): ArrayList<ChatMessage> {
        return chatHistory
    }

    fun insertChatMessage(chatMessage: ChatMessage) {
        chatHistory.add(chatMessage)
    }
}