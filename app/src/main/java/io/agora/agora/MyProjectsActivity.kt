package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageButton
import io.agora.agora.adapters.ProjectArrayAdapter
import io.agora.agora.entities.Callback
import io.agora.agora.entities.ConnectedUser
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.Project
import io.agora.agora.entities.serialization.User
import org.json.JSONArray
import org.json.JSONObject

class MyProjectsActivity : Layout() {

    var projects: Array<Project> = arrayOf()

    private fun setGridListener(gv: GridView) {
        val self = this
        gv.onItemClickListener = (object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val obj = JSONObject()

                obj.put("id", projects[position].id)
                IntentManager.goTo(self, ProjectActivity::class.java, obj)
            }
        })
    }

    fun setListeners() {
        val self = this

        findViewById<ImageButton>(R.id.imageButton).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                IntentManager.goTo(self, CreateProjectActivity::class.java)
            }
        })
    }

    fun setProjectsToGridView(rowProjects: JSONArray) {
        val gridView = findViewById<GridView>(R.id.project_list)

        for (i in 0 until rowProjects.length()) {
            projects += Project(rowProjects.getJSONObject(i))
        }
        val adapter = ProjectArrayAdapter(this, projects)

        gridView.adapter = adapter
        setGridListener(gridView)
    }

    fun getProjectsFromUser(user: User) {
        Request.getInstance().send(this, "users/" + user.id.toString() + "/projects", "GET", JSONObject(), JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val jsonObject = obj as JSONObject
                val data  = jsonObject.getJSONArray("data")

                if (data.length() > 0) {
                    findViewById<View>(R.id.project_list).visibility = View.VISIBLE
                    findViewById<View>(R.id.no_project_message).visibility = View.INVISIBLE
                    setProjectsToGridView(data)
                } else {

                    findViewById<View>(R.id.project_list).visibility = View.INVISIBLE
                    findViewById<View>(R.id.no_project_message).visibility = View.VISIBLE
                }
            }
        })
    }

    fun getProjects() {
        ConnectedUser.get(this, object: Callback() {
            override fun success(obj: Any?) {
                val user = obj as User

                getProjectsFromUser(user)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_my_projects, false)
        this.setListeners()
        this.getProjects()
    }
}
