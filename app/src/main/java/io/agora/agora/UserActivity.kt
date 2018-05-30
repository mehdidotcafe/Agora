package io.agora.agora

import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import io.agora.agora.entities.Callback
import io.agora.agora.entities.Email
import io.agora.agora.entities.Request
import io.agora.agora.entities.Serializer
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader
import org.json.JSONObject

class UserActivity : Layout() {
    private var user: User? = null

    private fun getUser(userId: Int, callback: (User) -> Unit) {
        Request.getInstance().send(this, "users/" + userId, "GET", JSONObject(), JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val objJSON = obj as JSONObject
                val data = objJSON.getJSONObject("data")

                callback(User(data))
            }
        })
    }

    private fun setViewFromUser(user: User) {
        findViewById<TextView>(R.id.user_name).text = user.firstname + " " + user.lastname
        //findViewById<TextView>(R.id.description_input).text = user.description
        if (user.picture != null && user.picture != "null" && user.picture != "") {
            RemoteImageLoader.load(this, findViewById(R.id.user_image), user.picture)
        } else {
            findViewById<ImageView>(R.id.user_image).setImageResource(R.drawable.rectangle2)
        }
    }

    private fun setListeners() {
        findViewById<Button>(R.id.ask_button).setOnClickListener{
            Email.goTo(this, user!!.email)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_user, true)
        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))

        getUser(params.getInt("id"), { u ->
            user = u
            setViewFromUser(u)
        })
        setListeners()
    }
}
