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

class AddExperienceActivity : Layout() {

    fun setListeners() {
        findViewById<FloatingActionButton>(R.id.send_button).setOnClickListener {
            val position = findViewById<EditText>(R.id.position_input).text.toString()
            val place = findViewById<EditText>(R.id.place_input).text.toString()
            val start = findViewById<EditText>(R.id.start_input).text.toString()
            val end = findViewById<EditText>(R.id.end_input).text.toString()
            val description = findViewById<EditText>(R.id.description_input).text.toString()
            var obj = JSONObject()
            val self = this

            if (position.isEmpty() || place.isEmpty() || start.isEmpty() || end.isEmpty() || description.isEmpty()) {
                Alert.ok(this, getString(R.string.error), getString(R.string.fill_all_fields), Callback())
            } else {
                obj.put("poste", position)
                obj.put("company", place)
                obj.put("start_date", start)
                obj.put("end_date", end)
                obj.put("description", description)
                ConnectedUser.get(this, object: Callback() {
                    override fun success(data: Any?) {
                        val user = data as User

                        Request.getInstance().send(self, "users/${user.id}/experiences/", "POST", JSONObject(), obj, object: Callback() {
                            override fun success(obj: Any?) {
                                Alert.ok(self, getString(R.string.success), getString(R.string.success_add_experience), object: Callback() {
                                    override fun success(obj: Any?) {
                                        self.finish()
                                    }

                                    override fun fail(error: String) {
                                        Alert.ok(self, "Erreur", "Echec de l'ajout de l'expérience, veuillez réessayer.", Callback())
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
        super.onCreate(savedInstanceState, R.layout.activity_add_experience, false)
        setListeners()
    }
}
