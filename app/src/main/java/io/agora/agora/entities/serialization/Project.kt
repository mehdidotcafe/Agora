package io.agora.agora.entities.serialization

import org.json.JSONObject
import io.agora.agora.entities.serialization.Hashtag

class Project(json: JSONObject) {
    var id: Int = json.getInt("id")
    var name: String = json.getString("name")
    var description: String = json.getString("description")
    var effectives: Int = json.getJSONArray("users").length()
    val owner: User? = getProjectOwner(json)
    val picture: String? = json.getString("picture")
    //var startDate: String = json.getString("start_date")
    //var endDate: String = json.getString("end_date")
    //var shortDescription: String = json.getString("short_description")
    var category: String = json.getString("category")
    var location: String = json.getString("location")
    //var hashtags: ArrayList<Hashtag> = Hashtag.getFromJSONArray(json.getJSONArray("hashtags"))

    fun getProjectOwner(p: JSONObject): User? {
        val users = p.getJSONArray("users")

        for (i in 0 until users.length()) {
            val user = users.getJSONObject(i)

            if (user.getInt("owner") == 1)
                return User(users.getJSONObject(i))
        }
        return null
    }
}