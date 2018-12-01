package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import io.agora.agora.adapters.ProjectArrayAdapter
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.Serializer
import io.agora.agora.entities.serialization.Project
import org.json.JSONObject

class ProjectListActivity : Layout() {
    private var self: ProjectListActivity = this

    private var projects: Array<Project> = arrayOf()

    private fun setListener(gv: GridView) {
        val self: ProjectListActivity = this
        gv.onItemClickListener = (object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val obj = JSONObject()

                obj.put("id", projects[position].id)
                IntentManager.goTo(self, ProjectActivity::class.java, obj)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_project_list, false)

        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))
        val qs = JSONObject()
        val tagsArray = params.getString("tag").split(',')

        val tagsString = "[" + tagsArray.map{
            '"' + it.trim() + '"'
        }.joinToString() + "]"

        qs.put("location", params.get("place"))
        qs.put("tag", tagsString)
        Request.getInstance().send(this, "project", "GET", qs, JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val jsonObj = obj as JSONObject
                val data  = jsonObj.getJSONArray("data")

                for (i in 0 until data.length()) {
                    projects += Project(data.getJSONObject(i))
                }
                val adapter = ProjectArrayAdapter(self, projects)

                val gridView = findViewById<GridView>(R.id.project_list)

                gridView.adapter = adapter
                setListener(gridView)
            }
        })

    }
}
