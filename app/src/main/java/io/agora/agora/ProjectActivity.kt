package io.agora.agora

import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import io.agora.agora.adapters.ProjectArrayAdapter
import io.agora.agora.entities.Callback
import io.agora.agora.entities.Request
import io.agora.agora.entities.Serializer
import io.agora.agora.entities.serialization.Project
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader
import org.json.JSONObject

class ProjectActivity : Layout() {

    private fun getProject(projectId: Int, callback: (Project) -> Unit) {
        Request.getInstance().send(this, "project/" + projectId, "GET", JSONObject(), JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val objData: JSONObject = obj as JSONObject
                val project: Project = Project(obj.getJSONObject("data"))
                callback(project)
            }
        })

    }

    private fun setViewFromProject(project: Project) {
        findViewById<TextView>(R.id.project_name).text = project.name
        findViewById<TextView>(R.id.project_owner).text = project.owner?.firstname + " " + project.owner?.firstname
                findViewById<TextView>(R.id.project_description).text = project.description
        if (project.picture != null && project.picture != "" && project.picture != "null") {
            RemoteImageLoader.load(this, findViewById(R.id.project_image), project.picture!!)
        } else {
            findViewById<ImageView>(R.id.project_image).setImageResource(R.drawable.rectangle2)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_project, true)
        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))

        getProject(params.getInt("id"), {project ->
            setViewFromProject(project)
        })
    }
}
