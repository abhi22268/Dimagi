package com.example.dimagiassignment.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dimagiassignment.Repository
import com.example.dimagiassignment.databinding.ActivityChatBinding
import com.example.dimagiassignment.local.ChatDatabase
import com.example.dimagiassignment.model.BotMessage
import com.example.dimagiassignment.model.CommandType
import com.example.dimagiassignment.model.UserMessage
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding


    private val db by lazy {
        ChatDatabase.getInstance(this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ChatViewModelFactory(Repository(db)))[ChatViewModel::class.java]
    }
    private val adapter by lazy {
        ChatAdapter(this, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserver()
    }

    private fun initObserver() {
        viewModel.botReply.observe(this) {
            when(it.type){
                CommandType.UPDATE_LOCATION -> {
                    fetchLocation(it.message)
                }
                CommandType.FIND_Location -> {
                    fetchLocation(it.message)
                }
                CommandType.NONE -> {
                    adapter.setData(it)
                    binding.rvChat.scrollToPosition(0)
                }
            }

        }
    }

    private fun initUI() {

        // Recyclerview
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        binding.rvChat.layoutManager = layoutManager

        binding.rvChat.adapter = adapter

        // Send Button
        binding.tvSend.setOnClickListener {
            val command = binding.etChat.text.toString().trim()
            binding.etChat.setText("")

            val chatMsg = UserMessage(command)
            adapter.setData(chatMsg)
            binding.rvChat.scrollToPosition(0)
            viewModel.processCommand(chatMsg)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    fun fetchLocation(message: String) {
        if(isLocationPermissionGranted()){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            locationRequest = LocationRequest().apply {
                interval = TimeUnit.MINUTES.toMillis(30)
                fastestInterval = TimeUnit.MINUTES.toMillis(30)
                maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    locationResult.lastLocation?.let {
                        val latitude = it.latitude
                        val longitude = it.longitude
                        adapter.setData(BotMessage("$message\nlat : $latitude lon: $longitude"))
                        binding.rvChat.scrollToPosition(0)
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        }

    }


    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                200
            )
            false
        } else {
            true
        }
    }



}