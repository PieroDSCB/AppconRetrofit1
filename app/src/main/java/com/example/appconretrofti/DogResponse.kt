package com.example.appconretrofti

import com.google.gson.annotations.SerializedName

data class DogResponse (@SerializedName("status")var status :String , @SerializedName("message")var image:List<String>)
