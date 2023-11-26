package com.example.univallealtoque.network

import com.example.univallealtoque.model.LoginResponseExpress
import com.example.univallealtoque.model.UpdateBasicDataResponseExpress
import com.example.univallealtoque.model.UserDataResponse
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


private const val BASE_URL =
    "https://univalle-al-toque-backend.vercel.app"

interface AlToqueService {

//    @GET("users")
//    suspend fun getUsers(): RemoteResult


    @POST("login") // Reemplaza con la ruta correcta de tu servicio
    suspend fun loginUserExpress(@Body jsonBody: RequestBody): LoginResponseExpress

    @POST("register/user")
    suspend fun registerUser(@Body jsonBody: RequestBody): String

    @PUT("updateprofile")
    suspend fun updateProfile(@Body jsonBody: RequestBody): UpdateBasicDataResponseExpress

    @GET("user/{email}")
    suspend fun getUserByEmail(@Path("email") email: String,type:String): UserDataResponse
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