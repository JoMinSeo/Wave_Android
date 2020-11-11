package com.project.wave_v2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: Retrofit? = null
    private var API : Service? = null

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl("http://10.80.161.64:8080/Wave_backend/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }

    fun getService() : Service?{
        if(API == null){
            API = instance?.create(Service::class.java)
        }
        return API
    }
}