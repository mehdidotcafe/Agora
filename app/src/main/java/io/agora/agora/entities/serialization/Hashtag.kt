package io.agora.agora.entities.serialization

import org.json.JSONArray
import org.json.JSONObject

class Hashtag(json: JSONObject) {
    val id: Int = json.getInt("id")
    val name: String = json.getString("name")

    companion object {
        fun getFromJSONArray(array: JSONArray): ArrayList<Hashtag> {
            var ret: ArrayList<Hashtag> = ArrayList()

            for (i in 0 until array.length()) {
                ret.add(Hashtag(array.getJSONObject(i)))
            }

            return ret
        }
    }
}