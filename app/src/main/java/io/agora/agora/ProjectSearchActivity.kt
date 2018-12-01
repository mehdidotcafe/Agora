package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.Button
import io.agora.agora.component.Alert
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import org.json.JSONArray
import org.json.JSONObject

class ProjectSearchActivity : Layout() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_project_search, false)
        this.setListeners()
    }

    fun setListeners() {
        val self = this

        findViewById<Button>(R.id.user_search_button).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                self.showResult()
            }
        })
    }

    fun showResult() {
        val skills: String = this.getInputString(R.id.skills_input)
        val place: String = this.getInputString(R.id.place_input)
        val skillJsonArray = JSONArray()
        val obj = JSONObject()

        if (skills == "" || place == "") {
            Alert.ok(this, getString(R.string.error), getString(R.string.fill_all_fields), Callback())
        } else {
            val skillsArray = skills.split(",")

            skillsArray.forEach{
                skillJsonArray.put(it)
            }
            obj.put("tag", skills)
            obj.put("place", place)

            IntentManager.goTo(this, ProjectListActivity::class.java, obj)
        }

    }


}
