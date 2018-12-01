package io.agora.agora.entities.serialization

import org.json.JSONArray
import org.json.JSONObject

class User(json: JSONObject) {
    val id: Int = json.getInt("id")
    val email: String = json.getString("email")
    val firstname: String = json.getString("first_name")
    val lastname: String = json.getString("last_name")
    val picture: String? =  json.getString("picture")
    val location: String? =  json.getString("location")
    val linkLinkedin: String =  if (json.has("link_linkedin") && !json.isNull("link_linkedin")) json.getString("link_linkedin") else ""
    val linkTwitter: String =  if (json.has("link_twitter") && !json.isNull("link_twitter")) json.getString("link_twitter") else ""
    val linkGithub: String =  if (json.has("link_github") && !json.isNull("link_github")) json.getString("link_github") else ""
    val function: String =  if (json.has("function") && !json.isNull("function")) json.getString("function") else ""
    val description: String =  if (json.has("description")  && !json.isNull("description")) json.getString("description") else ""
    val tags: Array<String> = if (json.has("tags")) getSkills(json.getJSONArray("tags")).toTypedArray() else arrayOf()


    fun getSkills(json: JSONArray) : List<String> {
        val skills : MutableList<String> = mutableListOf()

        for (i in 0 until json.length()) {
            skills.add(json.getString(i))
        }
        return skills
    }
}