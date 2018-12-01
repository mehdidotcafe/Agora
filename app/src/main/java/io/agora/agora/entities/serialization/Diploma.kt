package io.agora.agora.entities.serialization

import org.json.JSONObject

class Diploma(json: JSONObject) {
    val id = json.getInt("id_education")
    val idUser  = json.getInt("idUser")
    val degree = json.getString("degree")
    val school = json.getString("school")
    val startDate = json.getString("start_date")
    val endDate = json.getString("end_date")
    val description = json.getString("description")
}