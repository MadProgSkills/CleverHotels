package ru.iqmafia.cleverhotels.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Types.REAL

@Entity(tableName = "info_table")
data class InfoResponseEntity(
    @PrimaryKey
    val position: Int = 0,

    val id: Int? = null,

    val image: String? = null,

    val address: String? = null,

    @ColumnInfo(name = "suites_count")
    val suitesCount: Int? = null,

    @ColumnInfo(typeAffinity = REAL)
    val distance: Double? = null,

    val name: String? = null,

    @ColumnInfo(typeAffinity = REAL)
    val stars: Double? = null
)