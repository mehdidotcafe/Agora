package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.agora.agora.R
import io.agora.agora.entities.serialization.Experience

class ExperienceArrayAdapter(context: Context,
                             private val exps: Array<Experience>): ArrayAdapter<Experience>(context, R.layout.experience_item, exps)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.experience_item, view, false)
        }
        val obj = exps[position]

        view!!.findViewById<TextView>(R.id.experience_text).text = obj.startDate.substring(0, 4) + " - " + obj.endDate.substring(0, 4) + " " + obj.company + ", " + obj.description

        return view
    }
}