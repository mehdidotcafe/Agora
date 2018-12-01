package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.RequestCreator
import io.agora.agora.ProfileActivity
import io.agora.agora.R
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader
import jp.wasabeef.picasso.transformations.BlurTransformation
import org.json.JSONObject

/**
 *  @author mehdi.meddour@epitech.eu
 *  @class UserArrayAdapter
 *  Cette classe permet d'afficher un composant utilisateur dans une liste
 */

class UserArrayAdapter(context: Context,
                          private val textViewResourceId: Int,
                          private val projects: Array<User>): ArrayAdapter<User>(context, textViewResourceId, projects)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.user_item, view, false)
        }
        val obj: User = projects[position]
        if (obj.picture != null && obj.picture != "null" && obj.picture != "") {
            RemoteImageLoader.load(context, view!!.findViewById(R.id.user_image), obj.picture)
            RemoteImageLoader.load(context, view.findViewById(R.id.user_image_background), obj.picture, object: Callback() {
                override fun success(obj: Any? ) {
                    val img = obj as RequestCreator

                    img.transform(BlurTransformation(context))
                }
            })
        } else {
            view!!.findViewById<ImageView>(R.id.user_image).setImageResource(R.drawable.no_avatar)
            RemoteImageLoader.load(context, view.findViewById(R.id.user_image_background), R.drawable.rectangle2, object: Callback() {
                override fun success(obj: Any? ) {
                    val img = obj as RequestCreator

                    img.transform(BlurTransformation(context))
                }
            })
        }
        view.findViewById<TextView>(R.id.user_name).text = obj.firstname + " " + obj.lastname

        val gv = view.findViewById<GridView>(R.id.skill_list)

        gv.adapter = SkillArrayAdapter(context, obj.tags)


        view.findViewById<LinearLayout>(R.id.user_container).setOnClickListener{
            val toSend = JSONObject()

            toSend.put("id", obj.id)
            IntentManager.goTo(context, ProfileActivity::class.java, toSend)
        }
        return view
    }
}