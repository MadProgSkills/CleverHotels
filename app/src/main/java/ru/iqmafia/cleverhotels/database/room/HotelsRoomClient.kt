package ru.iqmafia.cleverhotels.database.room

import androidx.lifecycle.LiveData
import retrofit2.Response
import ru.iqmafia.cleverhotels.database.models.AllHotelsEntity
import ru.iqmafia.cleverhotels.database.models.InfoResponseEntity
import ru.iqmafia.cleverhotels.database.models.PicEntity
import ru.iqmafia.cleverhotels.utils.SafetyResponse

class HotelsRoomClient(private val dao: RoomDao): RoomRepository {
    override val allHotels: LiveData<List<AllHotelsEntity>>
        get() = dao.getAllHotels()
    override val allHotelsByDistance: LiveData<List<AllHotelsEntity>>
        get() = dao.getSortedByDistance()
    override val allHotelsBySuites: LiveData<List<AllHotelsEntity>>
        get() = dao.getSortedBySuites()
    override val infoRoomResponse: LiveData<InfoResponseEntity>
        get() = dao.getInfo()
    override val picRoomResponse: LiveData<PicEntity>
        get() = dao.getPic()

    override suspend fun insertAllHotels(hotel: AllHotelsEntity) {
        dao.insertAllHotels(hotel)
    }

    override suspend fun insertInfo(info: InfoResponseEntity) {
        dao.insertInfo(info)
    }

    override suspend fun insertPic(pic: PicEntity) {
        dao.insertPic(pic)
    }

    override suspend fun clearAllRoomTables() {
        dao.clearHotels()
        dao.clearInfo()
        dao.clearPic()
    }
}