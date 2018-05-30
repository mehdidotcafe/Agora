package io.agora.agora.entities

import org.json.JSONException
import org.json.JSONObject

import java.util.HashMap

object Serializer {

    fun serialize(obj: JSONObject): String {
        return obj.toString()
    }

    fun unserialize(obj: String): JSONObject {
        try {
            return JSONObject(obj)
        } catch (e: JSONException) {
            e.printStackTrace()
            return JSONObject()
        }

    }


    fun toMap(json: String): Map<String, Any> {


        return HashMap()
    }
}
