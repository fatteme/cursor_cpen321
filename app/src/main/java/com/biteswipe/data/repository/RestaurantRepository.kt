package com.biteswipe.data.repository

import com.biteswipe.data.api.RestaurantApi
import com.biteswipe.data.model.Restaurant
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestaurantRepository {
    private val api = RestaurantApi()

    suspend fun getNextRestaurant(): Result<Restaurant> = withContext(Dispatchers.IO) {
        try {
            api.getNextRestaurant()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun likeRestaurant(restaurantId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
        api.likeRestaurant(restaurantId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun dislikeRestaurant(restaurantId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
        api.dislikeRestaurant(restaurantId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRestaurantsByLocation(location: LatLng, radius: Int): Result<List<Restaurant>> = withContext(Dispatchers.IO) {
        try {
        api.getRestaurantsByLocation(location, radius)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRestaurantDetails(restaurantId: String): Result<Restaurant> = withContext(Dispatchers.IO) {
        try {
            api.getRestaurantDetails(restaurantId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRestaurantHistory(): Result<List<Restaurant>> = withContext(Dispatchers.IO) {
        try {
            api.getRestaurantHistory()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 