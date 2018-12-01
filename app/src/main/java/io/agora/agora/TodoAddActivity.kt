package io.agora.agora

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.TextView
import io.agora.agora.component.Alert
import io.agora.agora.entities.Callback
import io.agora.agora.entities.Request
import io.agora.agora.entities.Serializer
import org.json.JSONObject

class TodoAddActivity : Layout() {

    fun setListener(projectId: Int) {
        findViewById<FloatingActionButton>(R.id.send_button).setOnClickListener {
            val name = findViewById<TextView>(R.id.name_input).text.toString()
            val obj = JSONObject()
            val self = this

            obj.put("name", name)
            obj.put("id_project", projectId)
            Request.getInstance().send(this, "project/$projectId/notes", "POST", JSONObject(), obj, object: Callback() {
                override fun success(obj: Any?) {
                    Alert.ok(self, getString(R.string.success), getString(R.string.task_added), object: Callback() {
                        override fun success(obj: Any?) {
                            self.finish()
                        }
                    })
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_todo_add, false)


        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))
        val pid = params.getInt("projectId")

        setListener(pid)
    }
}
