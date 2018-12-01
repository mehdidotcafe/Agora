package io.agora.agora.entities.serialization

import org.json.JSONObject

class Todo(json: JSONObject) {
    val id = json.getInt("id_note")
    val projectId = json.getInt("id_project")
    val name = json.getString("name")
    val completed = json.getInt("completed")
}