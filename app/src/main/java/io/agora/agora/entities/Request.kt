package io.agora.agora.entities

import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.HashMap

import android.content.Context

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.builder.Builders
import io.agora.agora.entities.Callback

class Request private constructor() {
    protected var BASE_URL = "http://ec2-18-188-173-4.us-east-2.compute.amazonaws.com/api/v1/"
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
        headerParams.put(key, value)
    }

    fun addQueryString(key: String, value: String) {
        urlParams.put(key, value)
    }


    fun success(result: JSONObject?) {
        println("successStr")
        println(result)
    }

    fun fail(result: String) {
        println("errorStr")
        println(result)

    }

    private fun setDefaultUrlParams(route: String, obj: JSONObject?): String {
        var route = route
        var isFirst: Boolean? = true

        for (entry in urlParams.entries) {
            if (isFirst!!) {
                route += '?'.toString()
                isFirst = false
            } else
                route += '&'.toString()
            route += entry.key + '='.toString() + entry.value
        }

        if (obj != null && obj.names() != null) {
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

    private fun setHeaders(header: JSONObject) {

    }

    private fun format(json: JSONObject): JsonObject {
        val ret = JsonObject()

        val iter = json.keys()
        try {
            while (iter.hasNext()) {
                val key = iter.next()
                ret.addProperty(key, json.get(key) as String)

            }
        } catch (e: JSONException) {
            // Something went wrong!
        }

        return ret
    }

    fun sendFile(ctx: Context, route: String, pathname: String, queryString: JSONObject) {
        Ion.with(ctx)
                .load(setDefaultUrlParams(BASE_URL + route, queryString))
                .setMultipartFile("file", "application/octet", File(pathname))
                .asJsonObject()
                .setCallback(object : FutureCallback<JsonObject> {
                    override fun onCompleted(e: Exception, result: JsonObject?) {
                        val isSucceed = result != null && result!!.get("success") != null && result!!.get("success").getAsBoolean()

                        try {
                            if (isSucceed) {
                                success(if (result!!.get("data") != null) JSONObject(result!!.get("data").toString()) else null)
                            } else {
                                var ret = if (result != null) result!!.get("message") else null

                                if (result != null && ret == null)
                                    ret = result!!.get("error")
                                fail(if (ret != null) ret!!.getAsString() else "unknowError")
                            }
                        } catch (error: JSONException) {
                        }

                    }
                })
    }

    fun setRequestHeaders(req: Builders.Any.B): Builders.Any.B {
        for ((key, value) in headerParams) {
            req.addHeader(key, value)
        }
        return req
    }

    fun send(ctx: Context, route: String, method: String, queryString: JSONObject, body: JSONObject?, cb: Callback) {
        val req = Ion.with(ctx)
                .load(method, setDefaultUrlParams(BASE_URL + route, queryString))

        println(setDefaultUrlParams(BASE_URL + route, queryString))
        if (body != null)
            req.setJsonObjectBody(format(body))
        setRequestHeaders(req)
        req.asJsonObject()
                .setCallback(object : FutureCallback<JsonObject> {
                    override fun onCompleted(e: Exception?, result: JsonObject?) {
                        val isSucceed = result != null && result!!.get("errors") == null

                        println("REQUEST RESPONSE")
                        println(result)
                        println(e)
                        try {
                            if (isSucceed) {
                                cb.success(JSONObject(result.toString()))
                            } else {
                                cb.fail(if (result != null) result.getAsJsonArray("errors").toString() else "unknowError")
                            }
                        } catch (error: JSONException) {
                        }

                    }
                })
    }

}