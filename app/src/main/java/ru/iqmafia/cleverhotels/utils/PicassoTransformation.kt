package ru.iqmafia.cleverhotels.utils

import android.graphics.Bitmap
import com.squareup.picasso.Transformation

class PicassoTransformation: Transformation {
    override fun transform(source: Bitmap?): Bitmap {
        lateinit var result: Bitmap
        source?.let {
            val sizeX = source.width.minus(2)
            val sizeY = source.height.minus(2)
            val x = 1
            val y = 1
            result = Bitmap.createBitmap(source, x, y, sizeX, sizeY)
            if (result != source) {
                source.recycle()
            }
        }
        return result
    }

    override fun key(): String {
        return "Go away red border"
    }
}