package io.agora.agora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import io.agora.agora.R
import io.agora.agora.entities.Callback
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.Todo
import org.json.JSONObject

class TodoArrayAdapter(context: Context,
                       private val todos: Array<Todo>, private val projectId: Int): ArrayAdapter<Todo>(context, R.layout.todo_item, todos)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        var view = convertView
        val inflater: LayoutInflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view == null) {
            view = inflater.inflate(R.layout.todo_item, view, false)
        }
        val obj = todos[position]

        val checkbox = view!!.findViewById<CheckBox>(R.id.todo_completed)

        checkbox.setOnClickListener{
            val toSend = JSONObject()

            toSend.put("completed", if (checkbox.isChecked) "1" else "0")
            toSend.put("id_note", obj.id.toString())
            Request.getInstance().send(context, "project/$projectId/notes/${obj.id}", "PUT", JSONObject(), toSend, object: Callback() {
                override fun success(obj: Any?) {
                    println(obj as JSONObject)
                }
            })
        }

        checkbox.isChecked = obj.completed == 0
        view!!.findViewById<TextView>(R.id.todo_name).text = obj.name
        return view
    }
}