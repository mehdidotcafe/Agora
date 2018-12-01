package io.agora.agora.entities.serialization

import org.json.JSONObject

class Experience(json: JSONObject) {
    val id = json.getInt("idexperience")
    val idUser  = json.getInt("idUser")
    val poste = json.getString("poste")
    val company = json.getString("company")
    val startDate = json.getString("start_date")
    val endDate = json.getString("end_date")
    val description = json.getString("description")
}