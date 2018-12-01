package io.agora.agora

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import io.agora.agora.component.Alert
import io.agora.agora.entities.*
import io.agora.agora.entities.serialization.User
import org.json.JSONObject

class ProjectContactActivity : Layout() {

    private fun setListeners(projectId: Int) {
        val self = this

        findViewById<FloatingActionButton>(R.id.send_button).setOnClickListener{
            val content = findViewById<EditText>(R.id.message_input).text.toString()
            val data = JSONObject()

            ConnectedUser.get(self, object: Callback() {
                override fun success(obj: Any?) {
                    val user = obj as User

                    data.put("id_sender", user.id)
                    data.put("msg", content)
                    data.put("object", "L'utilisateur ${user.firstname} ${user.lastname} veut rejoindre votre projet.")
                    Request.getInstance().send(self, "invitation/join/projects/$projectId", "POST", JSONObject(), data, object: Callback() {
                        override fun success(obj: Any?) {
                            Alert.ok(self, getString(R.string.success), getString(R.string.success_send_message), object: Callback() {
                                override fun success(obj: Any?) {
                                    self.finish()
                                }
                            })
                        }
                    })
                }
            })

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_project_contact, false)

        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))

        setListeners(params.getInt("projectId"))
    }
}
