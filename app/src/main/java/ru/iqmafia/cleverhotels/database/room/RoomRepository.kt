package ru.iqmafia.cleverhotels.database.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.iqmafia.cleverhotels.database.models.AllHotelsEntity
import ru.iqmafia.cleverhotels.database.models.InfoResponseEntity
import ru.iqmafia.cleverhotels.database.models.PicEntity

interface RoomRepository {
    val allHotels: LiveData<List<AllHotelsEntity>>
    val allHotelsByDistance: LiveData<List<AllHotelsEntity>>
    val allHotelsBySuites: LiveData<List<AllHotelsEntity>>
    val infoRoomResponse: LiveData<InfoResponseEntity>
    val picRoomResponse: LiveData<PicEntity>

    suspend fun insertAllHotels(hotel: AllHotelsEntity)

    suspend fun insertInfo(info: InfoResponseEntity)

    suspend fun insertPic(pic: PicEntity)

    suspend fun clearAllRoomTables()



}