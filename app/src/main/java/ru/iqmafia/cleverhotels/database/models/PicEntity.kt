package ru.iqmafia.cleverhotels.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pic_table")
data class PicEntity(
    @PrimaryKey
    val position: Int? = null,
    val name: String? = null,
    val image: String? = null
)