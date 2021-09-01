package ru.iqmafia.cleverhotels.database.retrofit

import retrofit2.Response
import ru.iqmafia.cleverhotels.database.models.AllHotelsResponse
import ru.iqmafia.cleverhotels.database.models.InfoResponse
import ru.iqmafia.cleverhotels.utils.SafetyResponse

class HotelsRetrofitClient(val retrofitApi: HotelsRetrofitApi)  {
     suspend fun fetchHotelsRetrofit(): Response<List<AllHotelsResponse>> {
        return retrofitApi.fetchHotelsRetrofit()
    }

     suspend fun dynamicFetchHotelRetrofit(hotelId: Int): Response<InfoResponse> {
        return retrofitApi.dynamicFetchHotelRetrofit(hotelId)
    }

//    private inline fun <T> safetyCall(apiCall: () -> Response<T>): SafetyResponse<T> {
//        return try {
//            SafetyResponse.success(apiCall.invoke())
//        } catch (e: Exception) {
//            SafetyResponse.failure(e)
//        }
//    }
}