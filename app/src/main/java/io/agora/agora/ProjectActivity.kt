package io.agora.agora

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import io.agora.agora.adapters.ProjectArrayAdapter
import io.agora.agora.adapters.SkillArrayAdapter
import io.agora.agora.adapters.UserPictureArrayAdapter
import io.agora.agora.entities.*
import io.agora.agora.entities.serialization.Project
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader
import org.json.JSONObject

class ProjectActivity : Layout() {

    private fun getProject(projectId: Int, callback: (Project) -> Unit) {
        Request.getInstance().send(this, "project/$projectId", "GET", JSONObject(), JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val objData: JSONObject = obj as JSONObject
                val project = Project(objData.getJSONObject("data"))

                callback(project)
            }
        })

    }

    private fun setSkillsFromProject(project: Project){
        val gv = findViewById<GridView>(R.id.skills_list)

        gv.adapter = SkillArrayAdapter(this, project.skills)
    }

    private fun setContributorsFromProject(project: Project) {
        val gv = findViewById<GridView>(R.id.contributors_list)

        gv.adapter = UserPictureArrayAdapter(this, project.users)
    }

    private fun setViewFromProject(project: Project) {
        findViewById<TextView>(R.id.project_name).text = project.name
        findViewById<TextView>(R.id.project_place).text = project.location.capitalize()
        if (project.owner != null) {
            if (project.owner.picture != null && project.owner.picture.isNotEmpty() && project.owner.picture != "null") {
                RemoteImageLoader.load(this, findViewById(R.id.owner_picture), project.owner.picture)
            } else {
                findViewById<ImageView>(R.id.owner_picture).setImageResource(R.drawable.no_avatar)
            }
            findViewById<TextView>(R.id.project_owner).text = project.owner.firstname.capitalize() + " " + project.owner.lastname.toUpperCase()
        }
        findViewById<TextView>(R.id.project_description).text = project.description
        if (project.picture != null && project.picture != "" && project.picture != "null") {
            RemoteImageLoader.load(this, findViewById(R.id.project_image), project.picture)
        } else {
            findViewById<ImageView>(R.id.project_image).setImageResource(R.drawable.rectangle2)
        }
        setSkillsFromProject(project)
        setContributorsFromProject(project)
    }

    private fun hideOrShowButtons(project: Project, user: User) {
        val button = findViewById<FloatingActionButton>(R.id.contact_btn)
        val menu = findViewById<com.github.clans.fab.FloatingActionMenu>(R.id.menu)

        if (project.users.filter{ it.id == user.id}.isNotEmpty()) {
            button.visibility = View.INVISIBLE
            menu.visibility = View.VISIBLE
        } else {
            button.visibility = View.VISIBLE
            menu.visibility = View.INVISIBLE

        }
    }

    private fun setListeners(projectId: Int) {
        findViewById<View>(R.id.contact_btn).setOnClickListener {
            val obj = JSONObject()

            obj.put("projectId", projectId)
            IntentManager.goTo(this, ProjectContactActivity::class.java, obj)
        }

        findViewById<View>(R.id.task_list_btn).setOnClickListener {
            val obj = JSONObject()

            obj.put("projectId", projectId)
            IntentManager.goTo(this, TodoActivity::class.java, obj)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_project, true)
        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))

        setListeners(params.getInt("id"))
        ConnectedUser.get(this, object: Callback() {
            override fun success(obj: Any?) {
                getProject(params.getInt("id")) {
                    hideOrShowButtons(it, obj as User)
                    setViewFromProject(it)
                }
            }
        })

    }
}
