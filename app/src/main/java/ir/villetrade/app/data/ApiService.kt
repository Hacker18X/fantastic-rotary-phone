package ir.villetrade.app.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/app-data")
    suspend fun getAppData(): AppDataResponse

    @GET("api/plans")
    suspend fun getPlans(): List<Plan>

    @GET("api/broadcasts")
    suspend fun getBroadcasts(): List<Broadcast>

    @POST("api/profile/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @GET("api/auth/me")
    suspend fun me(@Header("Authorization") auth: String): UserProfile

    @GET("api/subscription-status/{key}")
    suspend fun subscriptionStatus(@Path("key") key: String): SubscriptionStatus
}
