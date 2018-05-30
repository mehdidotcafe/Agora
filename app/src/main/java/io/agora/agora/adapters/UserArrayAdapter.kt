package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import io.agora.agora.R
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader

class UserArrayAdapter(context: Context,
                          private val textViewResourceId: Int,
                          private val projects: Array<User>): ArrayAdapter<User>(context, textViewResourceId, projects)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.user_item, null, false)
        }
        val obj: User = projects[position]
        if (obj.picture != null && obj.picture != "null" && obj.picture != "") {
            RemoteImageLoader.load(context, view!!.findViewById(R.id.user_image), obj.picture)
        } else {
            view!!.findViewById<ImageView>(R.id.user_image).setImageResource(R.drawable.rectangle2)
        }
        view!!.findViewById<TextView>(R.id.user_name).text = obj.firstname + " " + obj.lastname
        return view
    }
}