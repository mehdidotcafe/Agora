package io.agora.agora

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import io.agora.agora.adapters.UserArrayAdapter
import io.agora.agora.entities.serialization.User
import android.widget.AdapterView
import android.widget.ListView
import io.agora.agora.adapters.ProjectArrayAdapter
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.Project
import org.json.JSONObject


class UserListActivity : Layout() {

    private var users: Array<User> = arrayOf()

    private fun setListener(gv: GridView) {
        val self: UserListActivity = this
        gv.onItemClickListener = (object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var obj: JSONObject = JSONObject()

                obj.put("id", users[position].id)
                IntentManager.goTo(self, UserActivity::class.java, obj)
            }
        })
    }

    fun getUsers(callback: (Array<User>) -> Unit) {
        Request.getInstance().send(this, "users", "GET", JSONObject(), JSONObject(), object: Callback() {
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

        getUsers{users: Array<User> ->
            val adapter = UserArrayAdapter(this,
                    R.layout.user_item, users)
            val gridView = findViewById<GridView>(R.id.user_list)

            gridView.adapter = adapter
            setListener(gridView)
        }



    }
}
