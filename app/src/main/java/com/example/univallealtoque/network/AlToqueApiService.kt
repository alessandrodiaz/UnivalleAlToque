package com.example.univallealtoque.network

import com.example.univallealtoque.model.CancelEnrollmentModel
import com.example.univallealtoque.model.CancelErrollmentResponse
import com.example.univallealtoque.model.ChangePasswordResponse
import com.example.univallealtoque.model.CreateNewActivityResponseExpress
import com.example.univallealtoque.model.DeleteAccountResponse
import com.example.univallealtoque.model.EnrolledActivitiesResponse
import com.example.univallealtoque.model.ErrollmentResponse
import com.example.univallealtoque.model.EventResponse
import com.example.univallealtoque.model.EventsListResponse
import com.example.univallealtoque.model.LoginResponseExpress
import com.example.univallealtoque.model.NewPasswordResponse
import com.example.univallealtoque.model.RecoverPasswordResponse
import com.example.univallealtoque.model.SemilleroResponse
import com.example.univallealtoque.model.SemillerosListResponse
import com.example.univallealtoque.model.SendCodeDeleteAccountModel
import com.example.univallealtoque.model.SendCodeDeleteAccountResponse
import com.example.univallealtoque.model.UpdateBasicDataResponseExpress
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


private const val BASE_URL = "https://univalle-al-toque-backend.vercel.app"

//private const val BASE_URL = "http://10.0.2.2:9000/"

interface AlToqueService {

    @POST("login") // Reemplaza con la ruta correcta de tu servicio
    suspend fun loginUserExpress(@Body jsonBody: RequestBody): LoginResponseExpress

    @POST("register/user")
    suspend fun registerUser(@Body jsonBody: RequestBody): String

    @PUT("updateprofile")
    suspend fun updateProfile(@Body jsonBody: RequestBody): UpdateBasicDataResponseExpress

    @POST("user/recover")
    suspend fun recoverPassword(@Body jsonBody: RequestBody): RecoverPasswordResponse

    @POST("user/delete/code")
    suspend fun sendCodeDeleteAccount(@Body jsonBody: RequestBody): SendCodeDeleteAccountResponse

    @POST("user/delete/confirm")
    suspend fun deleteAccountConfirm(@Body jsonBody: RequestBody): DeleteAccountResponse

    @PUT("user/lockout")
    suspend fun lockoutUserByEmail(@Body jsonBody: RequestBody): DeleteAccountResponse

    @POST("user/change/password")
    suspend fun changePassword(@Body jsonBody: RequestBody): ChangePasswordResponse

    @POST("user/newpassword")
    suspend fun newPassword(@Body jsonBody: RequestBody): NewPasswordResponse

    @POST("createnewactivity")
    suspend fun createNewActivity(@Body jsonBody: RequestBody): CreateNewActivityResponseExpress

    @POST("activity/list")
    suspend fun enrolledActivities(@Body jsonBody: RequestBody): EnrolledActivitiesResponse

    @POST("activity/semillero")
    suspend fun semilleroInfo(@Body jsonBody: RequestBody): SemilleroResponse

    @POST("activity/event")
    suspend fun eventInfo(@Body jsonBody: RequestBody): EventResponse

    @POST("activity/enroll")
    suspend fun makeEnrrollment(@Body jsonBody: RequestBody): ErrollmentResponse
    @POST("activity/cancel-enroll")
    suspend fun cancelEnrrollment(@Body jsonBody: RequestBody): CancelErrollmentResponse

    @GET("events")
    suspend fun getEvents(): EventsListResponse

    @GET("activities")
    suspend fun getSemilleros(): SemillerosListResponse

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