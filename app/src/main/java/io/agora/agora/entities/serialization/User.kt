package io.agora.agora.entities.serialization

import org.json.JSONObject

class User(json: JSONObject) {
    val id: Int = json.getInt("id")
    val email: String = json.getString("email")
    val firstname: String = json.getString("first_name")
    val lastname: String = json.getString("last_name")
    val picture: String? =  json.getString("picture")
    val location: String? =  json.getString("location")
    val description: String? =  json.getString("description")
}