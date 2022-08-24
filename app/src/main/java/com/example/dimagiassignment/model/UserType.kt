package com.example.dimagiassignment.model

sealed class UserType{

    data class User(val name : String) : UserType()
    data class Bot(val name : String = "@skypeBot") : UserType()
}
