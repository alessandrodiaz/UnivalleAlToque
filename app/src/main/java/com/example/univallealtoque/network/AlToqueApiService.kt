package com.example.univallealtoque.network

import com.example.univallealtoque.sign_in.RegisterViewModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private const val BASE_URL =
    "https://univalle-al-toque-backend.vercel.app"

interface AlToqueService {


//    @GET("users")
//    suspend fun getUsers(): RemoteResult

    @POST("register/user")
    suspend fun registerUser(@Body jsonBody: RequestBody): String

}

object AlToqueServiceFactory {
    fun makeAlToqueService(): AlToqueService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build().create(AlToqueService::class.java)

    }
}