package io.agora.agora.entities

import android.app.Activity
import android.content.Context
import android.content.Intent

import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import org.json.JSONObject

object IntentManager {

    private fun setExtrasToIntent(activity: Intent, extras: JSONObject) {
        activity.putExtra("params", Serializer.serialize(extras))
    }

    fun goTo(packageContext: Context, cls: Class<*>) {
        goTo(packageContext, cls, null)
    }

    fun goTo(packageContext: Context, cls: Class<*>, obj: JSONObject?) {
        val activity = Intent(packageContext, cls)

        if (obj != null)
            setExtrasToIntent(activity, obj)
        activity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        packageContext.startActivity(activity)
    }

    fun replace(packageContext: Context, cls: Class<*>) {
        val intent = Intent(packageContext, cls)

        intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        packageContext.startActivity(intent)
        (packageContext as Activity).finish()
    }
}
