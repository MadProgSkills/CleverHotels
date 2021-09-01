package ru.iqmafia.cleverhotels.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.Response
import ru.iqmafia.cleverhotels.MyApp
import ru.iqmafia.cleverhotels.database.models.AllHotelsResponse
import ru.iqmafia.cleverhotels.database.retrofit.HotelsRetrofitApi
import ru.iqmafia.cleverhotels.database.models.AllHotelsEntity
import ru.iqmafia.cleverhotels.database.retrofit.HotelsRetrofitClient
import ru.iqmafia.cleverhotels.database.room.HotelsRoomClient
import ru.iqmafia.cleverhotels.database.room.MyDataBase
import ru.iqmafia.cleverhotels.database.room.RoomDao
import ru.iqmafia.cleverhotels.database.room.RoomRepository
import ru.iqmafia.cleverhotels.utils.ACTIVITY
import ru.iqmafia.cleverhotels.utils.MY_IO_SCOPE
import ru.iqmafia.cleverhotels.utils.ROOM_REPOSITORY
import ru.iqmafia.cleverhotels.utils.SafetyResponse

class ListViewModel : ViewModel() {
    var allHotelsResponse: MutableLiveData<Response<List<AllHotelsResponse>>> = MutableLiveData()
    var allHotelsEntity: LiveData<List<AllHotelsEntity>> = MutableLiveData()


    //LOADING FROM WEB COMPLETE
    private var _loadedFromWebFlag = MutableLiveData<Boolean>()
    val loadedFromWebFlag: LiveData<Boolean>
        get() = _loadedFromWebFlag

    //VIEW STATE SWITCHER
    private var _sortingState = MutableLiveData<String>()
    val sortingState: LiveData<String>
        get() = _sortingState

    init {
        _loadedFromWebFlag.value = false
        _sortingState.value = "default"

    }

    fun switchLoadedFlag() {
        _loadedFromWebFlag.value = true
    }

    fun switchSortingState(state: String) {
        when (state) {
            "default" -> _sortingState.value = "default"
            "distance" -> _sortingState.value = "distance"
            "suites" -> _sortingState.value = "suites"
        }
    }

    //RETROFIT OPERATIONS
    fun fetchAllHotels(hotelsRetrofitApi: HotelsRetrofitApi) {
        viewModelScope.launch {
            val hotelsRetrofitClient = HotelsRetrofitClient(hotelsRetrofitApi)
            delay(1000)
            hotelsRetrofitClient?.let {
                val response = hotelsRetrofitClient.fetchHotelsRetrofit()
                allHotelsResponse.value = response
            }
        }
    }


    //ROOM OPERATIONS
    //ACCESS TO DATABASE IN MAIN TREAD?!
    fun insertAllHotelsToRoom(hotel: AllHotelsEntity) {
        MY_IO_SCOPE.launch {
            ROOM_REPOSITORY.insertAllHotels(hotel)
        }
    }

    fun getSortedByDefault(): LiveData<List<AllHotelsEntity>> {
        var roomResponse: LiveData<List<AllHotelsEntity>>
        viewModelScope.launch {
            roomResponse = ROOM_REPOSITORY.allHotels
            allHotelsEntity = roomResponse
        }
        return allHotelsEntity
    }

    fun getSortedByDistance(): LiveData<List<AllHotelsEntity>> {
        var roomResponse: LiveData<List<AllHotelsEntity>>
        viewModelScope.launch {
            roomResponse = ROOM_REPOSITORY.allHotelsByDistance
            allHotelsEntity = roomResponse
        }
        return allHotelsEntity
    }

    fun getSortedBySuites(): LiveData<List<AllHotelsEntity>> {
        var roomResponse: LiveData<List<AllHotelsEntity>>
        viewModelScope.launch {
            roomResponse = ROOM_REPOSITORY.allHotelsBySuites
            allHotelsEntity = roomResponse
        }
        return allHotelsEntity
    }
}

