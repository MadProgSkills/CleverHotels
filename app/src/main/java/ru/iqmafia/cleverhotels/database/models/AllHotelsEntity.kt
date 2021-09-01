package ru.iqmafia.cleverhotels.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Types.REAL

@Entity(tableName = "all_hotels_table")
data class AllHotelsEntity(

    @PrimaryKey
    val position: Int = 0,

    val id: Int = 0,

    val address: String = "",

    @ColumnInfo(name = "suites_count")
    val suitesCount: Int = 0,

    val distance: Double = 999.9,

    val name: String = "",

    val stars: Double = 3.0
)
