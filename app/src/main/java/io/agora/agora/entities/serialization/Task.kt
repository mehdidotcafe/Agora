package io.agora.agora.entities.serialization

import org.json.JSONObject

class Task(json: JSONObject) {
    val id = json.getInt("id_task")
    val projectId = json.getInt("id_project")
    val name = json.getString("name")
    val description = json.getString("description")
}