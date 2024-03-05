package com.example.composelayouts.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("/get?bid=176382&key=H6zV1UVyw5VpEpHF&uid=[H6zV1UVyw5VpEpHF]]")
    fun getUserById(@Query("msg") id: String): Call<Any>?

}