package com.example.appconretrofti

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
   suspend fun getDogsByBreeds(@Url url:String):Response<DogResponse>
//suspend por que esta funcion esta en un hilo asincrono
}