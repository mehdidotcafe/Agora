package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.agora.agora.R
import io.agora.agora.entities.serialization.Diploma

class DiplomaArrayAdapter(context: Context,
                          private val dips: Array<Diploma>): ArrayAdapter<Diploma>(context, R.layout.diploma_item, dips)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.diploma_item, view, false)
        }
        val obj = dips[position]

        view!!.findViewById<TextView>(R.id.diploma_text).text = obj.startDate.substring(0, 4) + " - " + obj.endDate.substring(0, 4) + " " + obj.school + ", " + obj.description

        return view
    }
}