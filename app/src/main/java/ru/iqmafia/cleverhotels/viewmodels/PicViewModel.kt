package ru.iqmafia.cleverhotels.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import ru.iqmafia.cleverhotels.database.models.InfoResponseEntity
import ru.iqmafia.cleverhotels.database.models.PicEntity
import ru.iqmafia.cleverhotels.utils.MY_IO_SCOPE
import ru.iqmafia.cleverhotels.utils.ROOM_REPOSITORY

class PicViewModel : ViewModel() {
    var picEntity = ROOM_REPOSITORY.picRoomResponse

    fun insertPicToRoom(pic: PicEntity) {
        MY_IO_SCOPE.launch {
            ROOM_REPOSITORY.insertPic(pic)
        }
    }
}