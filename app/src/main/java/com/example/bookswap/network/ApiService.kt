package com.example.bookswap.network

import com.example.bookswap.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

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

    @GET("user/books/search")
    suspend fun queryBooks(@Query("q") query: String, @Header("Authorization") accessToken: String): List<Book>

    @POST("auth/verifyToken")
    suspend fun verifyToken(@Header("Authorization") accessToken: String): GenericResponse

    @GET("user/feed")
    suspend fun getFeed(@Header("Authorization") accessToken: String): List<Post>

    @GET("user/profile")
    suspend fun getUser(@Header("Authorization") accessToken: String): UserData

    @GET("user/books/available")
    suspend fun getAvailable(@Header("Authorization") accessToken: String): BooksResponse

    @GET("user/books/wanted")
    suspend fun getWanted(@Header("Authorization") accessToken: String): BooksResponse

    @Multipart
    @POST("user/books/addAvailable")
    suspend fun addAvailable(@Header("Authorization") accessToken: String, @Part image: MultipartBody.Part, @Part("title") title: RequestBody, @Part("author") author: RequestBody, @Part("owner") owner: RequestBody, @Part("genre") genre: RequestBody, @Query("tags[]") tags: List<String>, @Part("description") description: RequestBody): GenericResponse

    @Multipart
    @POST("user/books/addWanted")
    suspend fun addWanted(@Header("Authorization") accessToken: String, @Part image: MultipartBody.Part, @Part("title") title: RequestBody, @Part("author") author: RequestBody, @Part("genre") genre: RequestBody,  @Query("tags[]") tags: List<String>, @Part("description") description: RequestBody): GenericResponse

    @DELETE("user/books/deleteAvailable/{id}")
    suspend fun deleteAvailable(@Header("Authorization") accessToken: String, @Path("id") id: String): GenericResponse

    @DELETE("user/books/deleteWanted/{id}")
    suspend fun deleteWanted(@Header("Authorization") accessToken: String, @Path("id") id: String): GenericResponse
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}