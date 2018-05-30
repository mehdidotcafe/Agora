package io.agora.agora.rendering

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso

object  RemoteImageLoader {
    private val URL = ""

    fun load(ctx: Context, view: ImageView, name: String) {
        //println("NAME " + name)
        Picasso.with(ctx).load("http://eip-agora.s3.amazonaws.com/projects/32/32").fit().centerCrop().into(view)
    }
}