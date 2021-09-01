package ru.iqmafia.cleverhotels.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.iqmafia.cleverhotels.database.models.AllHotelsEntity
import ru.iqmafia.cleverhotels.database.models.InfoResponseEntity
import ru.iqmafia.cleverhotels.database.models.PicEntity

@Dao
interface RoomDao {

    @Insert(entity = AllHotelsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllHotels(row: AllHotelsEntity)

    @Insert(entity = InfoResponseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertInfo(row: InfoResponseEntity)

    @Insert(entity = PicEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertPic(row: PicEntity)

    @Query("SELECT * FROM all_hotels_table")
    fun getAllHotels(): LiveData<List<AllHotelsEntity>>

    @Query("SELECT * FROM all_hotels_table ORDER BY distance ASC")
    fun getSortedByDistance(): LiveData<List<AllHotelsEntity>>

    @Query("SELECT * FROM all_hotels_table ORDER BY suites_count ASC")
    fun getSortedBySuites(): LiveData<List<AllHotelsEntity>>

    @Query("SELECT * FROM info_table")
    fun getInfo(): LiveData<InfoResponseEntity>

    @Query("SELECT * FROM pic_table")
    fun getPic(): LiveData<PicEntity>

    @Query("DELETE FROM all_hotels_table")
    fun clearHotels()

    @Query("DELETE FROM info_table")
    fun clearInfo()

    @Query("DELETE FROM pic_table")
    fun clearPic()

}