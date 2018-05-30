package io.agora.agora.entities

import android.content.Context
import android.content.Intent

object Email {
    fun goTo(packageContext: Context, email: String) {
        var emailIntent: Intent  = Intent(android.content.Intent.ACTION_SEND)

        emailIntent.type = "plain/text"
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "")
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "")

        packageContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
    }
}