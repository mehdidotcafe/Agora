package io.agora.agora.rendering

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.agora.agora.entities.Callback

object  RemoteImageLoader {
    private val URL = ""

    fun load(ctx: Context, view: ImageView, name: Int) {
        return load(ctx, view, name, null)
    }

    fun load(ctx: Context, view: ImageView, name: String) {
        return load(ctx, view, name, null)
    }

    fun load(ctx: Context, view: ImageView, name: Int, cb: Callback?) {
        val img = Picasso.with(ctx).load(name).fit().centerCrop()

        cb?.success(img)
        return img.into(view)
    }


    fun load(ctx: Context, view: ImageView, name: String, cb: Callback?) {
        val img = Picasso.with(ctx).load(name).fit().centerCrop()

        cb?.success(img)
        return img.into(view)
    }
}