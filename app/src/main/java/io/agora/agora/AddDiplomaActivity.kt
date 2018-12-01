package io.agora.agora

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import io.agora.agora.component.Alert
import io.agora.agora.entities.Callback
import io.agora.agora.entities.ConnectedUser
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.User
import org.json.JSONObject

class AddDiplomaActivity : Layout() {

    fun setListeners() {
        findViewById<FloatingActionButton>(R.id.send_button).setOnClickListener {
            val name = findViewById<EditText>(R.id.name_input).text.toString()
            val place = findViewById<EditText>(R.id.place_input).text.toString()
            val start = findViewById<EditText>(R.id.start_input).text.toString()
            val end = findViewById<EditText>(R.id.end_input).text.toString()
            val description = findViewById<EditText>(R.id.description_input).text.toString()
            var obj = JSONObject()
            val self = this

            if (name.isEmpty() || place.isEmpty() || start.isEmpty() || end.isEmpty() || description.isEmpty()) {
                Alert.ok(this, getString(R.string.error), getString(R.string.fill_all_fields), Callback())
            } else {
                obj.put("degree", name)
                obj.put("school", place)
                obj.put("start_date", start)
                obj.put("end_date", end)
                obj.put("description", description)
                ConnectedUser.get(this, object: Callback() {
                    override fun success(data: Any?) {
                        val user = data as User

                        Request.getInstance().send(self, "users/${user.id}/educations/", "POST", JSONObject(), obj, object: Callback() {
                            override fun success(obj: Any?) {
                                Alert.ok(self, getString(R.string.success), getString(R.string.success_add_diploma), object: Callback() {
                                    override fun success(obj: Any?) {
                                        self.finish()
                                    }

                                    override fun fail(error: String) {
                                        Alert.ok(self, getString(R.string.error), getString(R.string.fail_add_diploma), Callback())
                                    }
                                })
                            }
                        })

                    }
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_add_diploma, false)
        setListeners()
    }
}
