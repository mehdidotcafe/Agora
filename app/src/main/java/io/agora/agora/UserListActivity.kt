package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.GridView
import io.agora.agora.adapters.UserArrayAdapter
import io.agora.agora.entities.serialization.User
import android.widget.AdapterView
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.Serializer
import org.json.JSONObject


class UserListActivity : Layout() {

    private var users: Array<User> = arrayOf()

    private fun setListener(gv: GridView) {
        val self: UserListActivity = this
        gv.onItemClickListener = (object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val obj = JSONObject()

                obj.put("id", users[position].id)
                IntentManager.goTo(self, ProfileActivity::class.java, obj)
            }
        })
    }

    private fun getUsers(qs: JSONObject, callback: (Array<User>) -> Unit) {
        Request.getInstance().send(this, "users", "GET", qs, JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val objJSON = obj as JSONObject
                val data = objJSON.getJSONArray("data")

                for (i in 0 until data.length()) {
                    users += User(data.getJSONObject(i))
                }
                callback(users)
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_user_list, true)

        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))
        val qs = JSONObject()
        val tagsArray = params.getString("tag").split(',')
        val tagsString = "[" + tagsArray.map{
            '"' + it.trim() + '"'
        }.joinToString() + "]"

        println(tagsString)

        qs.put("location", params.get("place"))
        qs.put("tag", tagsString)

        getUsers(qs){users: Array<User> ->
            val adapter = UserArrayAdapter(this,
                    R.layout.user_item, users)
            val gridView = findViewById<GridView>(R.id.user_list)

            gridView.adapter = adapter
            setListener(gridView)
        }



    }
}
