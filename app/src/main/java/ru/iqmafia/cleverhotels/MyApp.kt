package ru.iqmafia.cleverhotels

import android.app.Application
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.iqmafia.cleverhotels.database.retrofit.HotelsRetrofitApi
import ru.iqmafia.cleverhotels.database.room.HotelsRoomClient
import ru.iqmafia.cleverhotels.database.room.MyDataBase
import ru.iqmafia.cleverhotels.utils.BASE_URL
import ru.iqmafia.cleverhotels.utils.MY_IO_SCOPE
import ru.iqmafia.cleverhotels.utils.ROOM_REPOSITORY

class MyApp : Application() {
    lateinit var hotelsRetrofitApi: HotelsRetrofitApi

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
        initRoom()
    }

    private fun initRoom() {
        MY_IO_SCOPE.launch {
        val dao = MyDataBase.getInstance(applicationContext).getDao()
        ROOM_REPOSITORY = HotelsRoomClient(dao)
        }
    }


    private fun initRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        hotelsRetrofitApi = retrofit.create(HotelsRetrofitApi::class.java)
    }
}

