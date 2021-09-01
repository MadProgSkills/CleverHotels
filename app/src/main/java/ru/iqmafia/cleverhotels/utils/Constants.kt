package ru.iqmafia.cleverhotels.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.iqmafia.cleverhotels.ui.activities.MainActivity
import ru.iqmafia.cleverhotels.database.room.RoomRepository

lateinit var ROOM_REPOSITORY: RoomRepository
lateinit var ACTIVITY: MainActivity
const val IMAGE_BASE_URL = "https://github.com/iMofas/ios-android-test/raw/master/"
const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"
val MY_IO_SCOPE = CoroutineScope(Job() + Dispatchers.IO)
val MY_MAIN_SCOPE = CoroutineScope(Job() + Dispatchers.Main)
