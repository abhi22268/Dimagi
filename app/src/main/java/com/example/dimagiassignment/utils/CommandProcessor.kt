package com.example.dimagiassignment.utils

import com.example.dimagiassignment.model.BotMessage
import com.example.dimagiassignment.model.CommandType


object CommandProcessor {

    fun process(command: String): BotMessage {

        if (command.equals("Hello", true) || command.equals("Hi", true)) {
            return BotMessage("$command!")
        } else if (command.startsWith("ping")) {
            return BotMessage("Bot is Active")
        } else if (command.startsWith("whereiam")) {
            return BotMessage("You are here", CommandType.FIND_Location)
        } else if (command.startsWith("hereiam")) {
            return BotMessage("Your location updated :", CommandType.FIND_Location)
        }
        return BotMessage("Sorry, I did not understand that!")
    }

}