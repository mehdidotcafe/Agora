package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import io.agora.agora.DateManager
import io.agora.agora.R
import io.agora.agora.entities.serialization.Project
import io.agora.agora.rendering.RemoteImageLoader

class ProjectArrayAdapter(context: Context,
                             private val projects: Array<Project>): ArrayAdapter<Project>(context, R.layout.project_item, projects)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.project_item, view, false)
        }
        val obj: Project = projects[position]
        val picture: ImageView = view!!.findViewById(R.id.project_image)
        if (obj.picture != null && obj.picture != "null" && obj.picture != "") {
            RemoteImageLoader.load(context, picture, obj.picture)
        } else {
            picture.setImageResource(R.drawable.rectangle2)
        }
        val delay = Math.max(0,DateManager.getDateDiff(DateManager.getDateFromNow(), DateManager.getDateFromString(obj.endDate)))

        view.findViewById<TextView>(R.id.project_name).text = obj.name
        view.findViewById<TextView>(R.id.project_length).text = obj.effectives.toString()
        view.findViewById<TextView>(R.id.project_short_description).text = obj.shortDescription
        view.findViewById<TextView>(R.id.project_delay).text = delay.toString()
        if (obj.effectives <= 1) {
            view.findViewById<TextView>(R.id.project_length_text).text = context.getString(R.string.person)
        } else {
            view.findViewById<TextView>(R.id.project_length_text).text = context.getString(R.string.person_plu)
        }
        if (delay <= 1) {
            view.findViewById<TextView>(R.id.project_delay_text).text = context.getString(R.string.remaining_day)
        } else {

            view.findViewById<TextView>(R.id.project_delay_text).text = context.getString(R.string.remaining_day_plu)
        }
        return view
    }
}