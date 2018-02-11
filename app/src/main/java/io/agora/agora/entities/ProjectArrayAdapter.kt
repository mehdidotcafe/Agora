package io.agora.agora.entities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.agora.agora.R
import android.widget.TextView

class ProjectArrayAdapter<T>(context: Context,
                             private val textViewResourceId: Int,
                             private val projects: Array<T>): ArrayAdapter<T>(context, textViewResourceId, projects)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.project_item, null, false)
        }
        val obj: T = projects.get(position)
        view!!.findViewById<TextView>(R.id.project_name).setText(obj as CharSequence)

        return view
    }
}