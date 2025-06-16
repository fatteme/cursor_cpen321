package com.biteswipe.data.api

import com.biteswipe.data.model.Restaurant
import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.IOException

interface RestaurantApiService {
    @GET("restaurants/next")
    suspend fun getNextRestaurant(): Restaurant

    @POST("restaurants/{id}/like")
    suspend fun likeRestaurant(@Path("id") restaurantId: String)

    @POST("restaurants/{id}/dislike")
    suspend fun dislikeRestaurant(@Path("id") restaurantId: String)

    @GET("restaurants/nearby")
    suspend fun getRestaurantsByLocation(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("radius") radius: Int
    ): List<Restaurant>

    @GET("restaurants/{id}")
    suspend fun getRestaurantDetails(@Path("id") restaurantId: String): Restaurant

    @GET("restaurants/history")
    suspend fun getRestaurantHistory(): List<Restaurant>
}

class RestaurantApi(private val authToken: String? = null) {
    private val baseUrl = "http://localhost:3000/api/" // Update with your actual API URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = { chain: okhttp3.Interceptor.Chain ->
        val request = chain.request().newBuilder().apply {
            authToken?.let { addHeader("Authorization", "Bearer $it") }
        }.build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RestaurantApiService::class.java)

    suspend fun getNextRestaurant(): Result<Restaurant> = try {
        Result.success(apiService.getNextRestaurant())
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun likeRestaurant(restaurantId: String): Result<Unit> = try {
        apiService.likeRestaurant(restaurantId)
        Result.success(Unit)
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun dislikeRestaurant(restaurantId: String): Result<Unit> = try {
        apiService.dislikeRestaurant(restaurantId)
        Result.success(Unit)
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun getRestaurantsByLocation(location: LatLng, radius: Int): Result<List<Restaurant>> = try {
        Result.success(apiService.getRestaurantsByLocation(location.latitude, location.longitude, radius))
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun getRestaurantDetails(restaurantId: String): Result<Restaurant> = try {
        Result.success(apiService.getRestaurantDetails(restaurantId))
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun getRestaurantHistory(): Result<List<Restaurant>> = try {
        Result.success(apiService.getRestaurantHistory())
    } catch (e: IOException) {
        Result.failure(e)
    }
} 