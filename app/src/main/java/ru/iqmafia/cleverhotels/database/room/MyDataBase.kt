package ru.iqmafia.cleverhotels.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.iqmafia.cleverhotels.database.models.AllHotelsEntity
import ru.iqmafia.cleverhotels.database.models.InfoResponseEntity
import ru.iqmafia.cleverhotels.database.models.PicEntity

@Database(entities = [AllHotelsEntity::class, InfoResponseEntity::class, PicEntity::class], version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getDao(): RoomDao

    companion object {
        @Volatile
        private var instance: MyDataBase? = null

        fun getInstance(context: Context): MyDataBase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        MyDataBase::class.java,
                        "database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    return instance as MyDataBase
                }
                return instance as MyDataBase
            }
        }

    }
}