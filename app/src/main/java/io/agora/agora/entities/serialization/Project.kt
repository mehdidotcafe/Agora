package io.agora.agora.entities.serialization

import org.json.JSONObject
import org.json.JSONArray

class Project(json: JSONObject) {
    val id: Int = json.getInt("id")
    val name: String = json.getString("name")
    val description: String = json.getString("description")
    val effectives: Int = json.getJSONArray("users").length()
    val picture: String? = json.getString("picture")
    var endDate: String = json.getString("end_date")
    var shortDescription: String = json.getString("short_description")
    val location: String = json.getString("location")
    val users = this.getUsers(json.getJSONArray("users")).toTypedArray()
    val owner: User? = this.getProjectOwner(json.getJSONArray("users"))
    var skills = this.getSkills(json.getJSONArray("hashtags")).toTypedArray()


    fun getSkills(json: JSONArray) : List<String> {
        val skills : MutableList<String> = mutableListOf()

        for (i in 0 until json.length()) {
            skills.add(json.getJSONObject(i).getString("name"))
        }
        return skills
    }


    fun getUsers(json: JSONArray) : MutableList<User> {
        val users : MutableList<User> = mutableListOf()

        for (i in 0 until json.length()) {
            users.add(User(json.get(i) as JSONObject))
        }
        return users
    }

    fun getProjectOwner(json: JSONArray) : User? {
        for (i in 0 until json.length()) {
            if ((json.get(i) as JSONObject).getInt("owner") == 1) {
                return User(json.get(i) as JSONObject)
            }
        }
        return User(json.get(0) as JSONObject)
    }
}