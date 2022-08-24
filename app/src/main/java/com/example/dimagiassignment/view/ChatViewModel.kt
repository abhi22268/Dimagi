package com.example.dimagiassignment.view

import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimagiassignment.Repository
import com.example.dimagiassignment.model.BotMessage
import com.example.dimagiassignment.model.ChatMessage
import com.example.dimagiassignment.model.UserMessage
import com.example.dimagiassignment.model.UserType
import com.example.dimagiassignment.utils.CommandProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatViewModel(private val repository: Repository) : ViewModel() {

    private val _botReply = MutableLiveData<BotMessage>()
    val botReply: LiveData<BotMessage>
        get() = _botReply

    private val _chatHistory = MutableLiveData<ArrayList<ChatMessage>>()
    val chatHistory: LiveData<ArrayList<ChatMessage>>
        get() = _chatHistory

    fun processCommand(chatMessage: UserMessage) {
        repository.insertChatMessage(chatMessage)
        val msg = CommandProcessor.process(chatMessage.message)
        _botReply.value = msg
    }

    fun loadChatHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val chatHistory = repository.getChatHistory()
            withContext(Dispatchers.Main) {
                _chatHistory.value = chatHistory
            }
        }

    }

}