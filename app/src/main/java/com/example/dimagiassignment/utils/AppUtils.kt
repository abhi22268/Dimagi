package com.example.dimagiassignment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.ProgressBar
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {



    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(dateFormat: String): String =
        SimpleDateFormat(dateFormat).format(Date())

    fun getUserName(): String {
        return "Danny"
    }


}