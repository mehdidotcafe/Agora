package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.*
import io.agora.agora.adapters.ProjectArrayAdapter
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.Project
import io.agora.agora.entities.serialization.User
import org.json.JSONArray
import org.json.JSONObject


class ProjectListActivity : Layout() {
    private var self: ProjectListActivity = this

    private var projects: Array<Project> = arrayOf()

    private fun setListener(gv: ListView) {
        val self: ProjectListActivity = this
        gv.onItemClickListener = (object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var obj: JSONObject = JSONObject()

                obj.put("id", projects[position].id)
                IntentManager.goTo(self, ProjectActivity::class.java, obj)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_project_list, true)

        Request.getInstance().send(this, "project", "GET", JSONObject(), JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val JSONObj = obj as JSONObject
                val data  = JSONObj.getJSONArray("data")

                for (i in 0 until data.length()) {
                    projects += Project(data.getJSONObject(i))
                }
                val adapter = ProjectArrayAdapter(self,
                        R.layout.project_item, projects)

                val gridView = findViewById<ListView>(R.id.project_list)

                gridView.adapter = adapter
                setListener(gridView)
            }
        })

    }
}
