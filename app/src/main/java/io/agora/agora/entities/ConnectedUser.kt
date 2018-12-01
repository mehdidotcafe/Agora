package io.agora.agora.entities

import android.content.Context
import io.agora.agora.entities.serialization.User
import org.json.JSONObject

class ConnectedUser {
    companion object {
        var user: User? = null
        fun get(ctx: Context, cb: Callback) {
            if (user == null) {
                this.refresh(ctx, cb)
            } else {
                cb.success(user)
            }
        }

        fun refresh(ctx: Context, cb: Callback) {
            Request.getInstance().send(ctx, "users/me", "GET", JSONObject(), JSONObject(), object: Callback() {
                override fun success(obj: Any?) {
                    val jsonObj = obj as JSONObject

                    val data = jsonObj.getJSONObject("data")

                    user = User(data)
                    cb.success(user)
                }
            })
        }

        fun set(newUser: User) {
            user = newUser
        }
    }
}