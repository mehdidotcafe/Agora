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
import io.agora.agora.rendering.RemoteImageLoader

class ProjectArrayAdapter(context: Context,
                             private val textViewResourceId: Int,
                             private val projects: Array<Project>): ArrayAdapter<Project>(context, textViewResourceId, projects)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.project_item, null, false)
        }
        val obj: Project = projects[position]
        var picture: ImageView = view!!.findViewById(R.id.project_image)
        if (obj.picture != null && obj.picture != "null" && obj.picture != "") {
            RemoteImageLoader.load(context, picture, obj.picture!!)
        } else {
            picture.setImageResource(R.drawable.rectangle2)
        }
        view!!.findViewById<TextView>(R.id.project_name).text = obj.name
        view!!.findViewById<TextView>(R.id.project_length).text = obj.effectives.toString()
        return view
    }
}