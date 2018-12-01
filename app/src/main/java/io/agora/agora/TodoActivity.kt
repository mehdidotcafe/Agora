package io.agora.agora

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.GridView
import io.agora.agora.adapters.TodoArrayAdapter
import io.agora.agora.entities.*
import io.agora.agora.entities.serialization.Todo
import org.json.JSONObject

class TodoActivity : Layout() {


    private fun getTasksAndDisplay(projectId: Int) {
        val self = this

        Request.getInstance().send(this, "project/$projectId/notes", "GET", JSONObject(), JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val json = (obj as JSONObject).getJSONArray("data")
                var arr = arrayOf<Todo>()
                val list = findViewById<GridView>(R.id.tasks_container)

                for (i in 0 until json.length()) {
                    arr += Todo(json.getJSONObject(i))
                }
                list.adapter = TodoArrayAdapter(self, arr, projectId)
                LayoutManager.setGridViewHeight(list)
            }
        })
    }

    private fun setListener(projectId: Int) {
        findViewById<FloatingActionButton>(R.id.add_button).setOnClickListener {
            val obj = JSONObject()

            obj.put("projectId", projectId)
            IntentManager.goTo(this, TodoAddActivity::class.java, obj)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_todo, false)

        val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))
        val pid = params.getInt("projectId")

        getTasksAndDisplay(pid)
        setListener(pid)
    }
}
