package com.example.dimagiassignment.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dimagiassignment.databinding.ItemBotMsgBinding
import com.example.dimagiassignment.databinding.ItemUserMsgBinding
import com.example.dimagiassignment.model.BotMessage
import com.example.dimagiassignment.model.ChatMessage
import com.example.dimagiassignment.model.UserMessage


class ChatAdapter(private val context : Context, private val viewModel : ChatViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatList = ArrayList<ChatMessage>()

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position] is UserMessage) 0 else 1
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ItemUserMsgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserViewHolder(binding)
        } else {
            val binding = ItemBotMsgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BotViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is UserViewHolder -> {
                holder.update(chatList[position] as? UserMessage?)
            }

            is BotViewHolder -> {
                holder.update(chatList[position] as? BotMessage?)
            }
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(cardsList: ArrayList<ChatMessage>?) {
        this.chatList.clear()
        this.chatList.addAll(cardsList ?: ArrayList())
        notifyDataSetChanged()
    }

    fun setData(chatMessage: ChatMessage?) {
        chatMessage?.let {
            this.chatList.add(0,it)
            notifyItemInserted(0)
        }
    }

    inner class UserViewHolder(val binding: ItemUserMsgBinding) : RecyclerView.ViewHolder(binding.root){

        fun update(chatMessage: UserMessage?){
            binding.tvUserMsg.text = chatMessage?.message
        }
    }
    inner class BotViewHolder(val binding: ItemBotMsgBinding) : RecyclerView.ViewHolder(binding.root) {

        fun update(chatMessage: BotMessage?){
            binding.tvBotMsg.text = chatMessage?.message
        }
    }

}


