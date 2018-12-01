package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.Button
import io.agora.agora.component.Alert
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import org.json.JSONObject

class UserSearchActivity : Layout() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_user_search, false)
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
        val obj = JSONObject()

        if (skills == "" || place == "") {
            Alert.ok(this, getString(R.string.error), getString(R.string.fill_all_fields), Callback())
        } else {
            obj.put("tag", skills)
            obj.put("place", place)

            IntentManager.goTo(this, UserListActivity::class.java, obj)
        }
    }


}
