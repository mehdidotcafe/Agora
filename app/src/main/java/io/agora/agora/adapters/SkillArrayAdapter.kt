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

class SkillArrayAdapter(context: Context,
                        private val skills: Array<String>): ArrayAdapter<String>(context, R.layout.skill_item, skills)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.skill_item, view, false)
        }
        val obj = skills[position]

        view!!.findViewById<TextView>(R.id.skill_name).text = obj
        return view
    }
}