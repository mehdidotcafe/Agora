package io.agora.agora.entities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException

object BitmapGetter {

    private val imageHeader = ""

    private fun isSmallerThan(bLength: Int, maxKbLength: Int): Boolean {
        return bLength / 1000 <= maxKbLength
    }

    @JvmOverloads
    operator fun get(ctx: Context, data: Intent, maxKbLength: Int = -1): String? {
        val uri = data.data

        try {
            val bitmap = MediaStore.Images.Media.getBitmap(ctx.contentResolver, uri)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos)
            try {
                val b = baos.toByteArray()

                if (maxKbLength != -1 && isSmallerThan(b.size, maxKbLength)) {
                    val encImage = Base64.encodeToString(b, Base64.DEFAULT)
                    return imageHeader + encImage
                }
            } catch (e: NullPointerException) {
                println("bitmaperror1")
                e.printStackTrace()
            } catch (e: OutOfMemoryError) {
                println("bitmaperror2")
                e.printStackTrace()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}
