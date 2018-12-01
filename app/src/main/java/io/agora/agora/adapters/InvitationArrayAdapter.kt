package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import io.agora.agora.R
import io.agora.agora.entities.Callback
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.Invitation
import org.json.JSONObject

class InvitationArrayAdapter(context: Context,
                       private val invs: Array<Invitation>, private val hasButton: Boolean): ArrayAdapter<Invitation>(context, R.layout.invitation_item, invs)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.invitation_item, null, false)
        }
        val obj = invs[position]

        if (hasButton && obj.status == 0) {
            view!!.findViewById<LinearLayout>(R.id.button_container).visibility = View.VISIBLE

            view.findViewById<ImageButton>(R.id.validate_button).setOnClickListener {
                Request.getInstance().send(context, "invitation/${obj.id}/accept", "PUT", JSONObject(), JSONObject(), Callback())
            }

            view.findViewById<ImageButton>(R.id.refuse_button).setOnClickListener {
                Request.getInstance().send(context, "invitation/${obj.id}/refuse", "PUT", JSONObject(), JSONObject(), Callback())
            }
        } else {
            view!!.findViewById<LinearLayout>(R.id.button_container).visibility = View.GONE
        }

        val textView = view!!.findViewById<TextView>(R.id.invitation_content)

        if (obj.status == 0) {
            textView.text = obj.projectName
        } else if (obj.status == 1) {
            textView.text = context.getString(R.string.accepted_cap)
        } else {
            textView.text = context.getString(R.string.refused_cap)
        }
        view!!.findViewById<TextView>(R.id.invitation_date).text = obj.created.substring(8, 10) + "/" + obj.created.substring(5, 7) + "/" + obj.created.substring(0, 4)
        view!!.findViewById<TextView>(R.id.invitation_project).text = obj.message
        return view
    }
}