package com.example.dimagiassignment.model

data class BotMessage(val message : String, val type : CommandType = CommandType.NONE) : ChatMessage

enum class CommandType{
    NONE, FIND_Location, UPDATE_LOCATION
}
