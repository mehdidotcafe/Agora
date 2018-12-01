package io.agora.agora.entities

import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.HashMap

import android.content.Context

import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.builder.Builders
import com.google.gson.JsonParser

class Request private constructor() {
    private var BASE_URL = "http://ec2-35-180-103-130.eu-west-3.compute.amazonaws.com:3000/api/v1/"
    private val urlParams = HashMap<String, String>()
    private var headerParams = HashMap<String, String>()

    companion object {
        private val request: Request = Request()

        @Synchronized
        fun getInstance(): Request {
            return request
        }
    }

    fun addHeader(key: String, value: String) {
        headerParams[key] = value
    }

    fun addQueryString(key: String, value: String) {
        urlParams[key] = value
    }


    fun success(obj: JSONObject?) {
    }

    fun fail(error: String) {

    }

    private fun setDefaultUrlParams(_route: String, obj: JSONObject?): String {
        var route = _route
        var isFirst: Boolean? = true

        for (entry in urlParams.entries) {
            if (isFirst!!) {
                route += '?'.toString()
                isFirst = false
            } else
                route += '&'.toString()
            route += entry.key + '='.toString() + entry.value
        }

        if (obj?.names() != null) {
            try {
                for (i in 0 until obj.names().length()) {
                    if (isFirst!!) {
                        route += '?'.toString()
                        isFirst = false
                    } else
                        route += '&'.toString()
                    route += obj.names().getString(i) + '='.toString() + obj.get(obj.names().getString(i))
                }
            } catch (e: JSONException) {
            }

        }
        return route
    }

    private fun format(json: JSONObject): JsonObject {
        val jsonParser = JsonParser()


        return jsonParser.parse(json.toString()) as JsonObject
    }

   /*private fun format(json: JSONObject): JsonObject {
        val ret = JsonObject()

        val iterator = json.keys()
        try {
            while (iterator.hasNext()) {
                val key = iterator.next()

                ret.addProperty(key, json.get(key) as String)

            }
        } catch (e: JSONException) {
            // Something went wrong!
        }

        return ret
    }*/

    fun sendFile(ctx: Context, route: String, pathname: String, queryString: JSONObject) {
        Ion.with(ctx)
                .load(setDefaultUrlParams(BASE_URL + route, queryString))
                .setMultipartFile("file", "application/octet", File(pathname))
                .asJsonObject()
                .setCallback(object : FutureCallback<JsonObject> {
                    override fun onCompleted(e: Exception, result: JsonObject?) {
                        val isSucceed = result?.get("success") != null && result.get("success").asBoolean

                        try {
                            if (isSucceed) {
                                success(if (result!!.get("data") != null) JSONObject(result.get("data").toString()) else null)
                            } else {
                                var ret = if (result != null) result.get("message") else null

                                if (result != null && ret == null)
                                    ret = result.get("error")
                                fail(if (ret != null) ret.asString else "unknowError")
                            }
                        } catch (error: JSONException) {
                        }

                    }
                })
    }

    private fun setRequestHeaders(req: Builders.Any.B): Builders.Any.B {
        for ((key, value) in headerParams) {
            req.addHeader(key, value)
        }
        return req
    }

    fun send(ctx: Context, route: String, method: String, queryString: JSONObject, body: JSONObject?, cb: Callback) {
        val req = Ion.with(ctx)
                .load(method, setDefaultUrlParams(BASE_URL + route, queryString))

        println("SENDING")
        println(setDefaultUrlParams(BASE_URL + route, queryString))
        if (body != null)
            req.setJsonObjectBody(format(body))
        setRequestHeaders(req)
        req.asJsonObject()
                .setCallback(object : FutureCallback<JsonObject> {
                    override fun onCompleted(e: Exception?, result: JsonObject?) {
                        val isSucceed = result != null && (result.get("errors") == null || result.has("errors"))

                        println("REQUEST RESPONSE")
                        println(result)
                        try {
                            if (isSucceed) {
                                cb.success(JSONObject(result.toString()))
                            } else {
                                cb.fail(if (result != null) result.getAsJsonArray("errors").toString() else "unknownError")
                            }
                        } catch (error: JSONException) {
                        }

                    }
                })
    }

}