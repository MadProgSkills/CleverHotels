package ru.iqmafia.cleverhotels.database.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.iqmafia.cleverhotels.database.models.AllHotelsResponse
import ru.iqmafia.cleverhotels.database.models.InfoResponse


interface HotelsRetrofitApi {

    @GET("./0777.json")
    @Headers("Content-Type: application/json")
    suspend fun fetchHotelsRetrofit(): Response<List<AllHotelsResponse>>


    @GET("{hotelId}.json")
    @Headers("Content-Type: application/json")
    suspend fun dynamicFetchHotelRetrofit(@Path("hotelId")hotelId: Int): Response<InfoResponse>

}