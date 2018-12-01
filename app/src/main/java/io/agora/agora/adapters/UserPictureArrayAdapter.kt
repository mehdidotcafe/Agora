package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import io.agora.agora.R
import io.agora.agora.entities.serialization.Project
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader

class UserPictureArrayAdapter(context: Context,
                              private val users: Array<User>) : ArrayAdapter<User>(context, R.layout.user_picture_item, users)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.user_picture_item, view, false)
        }
        val obj = users[position]

        if (obj.picture != null && obj.picture != "null" && obj.picture != "") {
            RemoteImageLoader.load(context, view!!.findViewById(R.id.user_picture), obj.picture)
        } else {
            view!!.findViewById<ImageView>(R.id.user_picture).setImageResource(R.drawable.no_avatar)
        }
        return view
    }
}