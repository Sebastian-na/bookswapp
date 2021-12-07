package com.example.bookswap.network

import com.example.bookswap.model.AccessTokenResponse
import com.example.bookswap.model.RegisterResponse
import com.example.bookswap.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.1.65:3000/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body body: User): AccessTokenResponse

    @POST("auth/register")
    suspend fun register(@Body body: User): RegisterResponse

}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}