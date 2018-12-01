package io.agora.agora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.Storage
import io.agora.agora.entities.serialization.User
import org.json.JSONObject

class ConfigurationActivity : Layout() {

    fun setListeners() {
        val self = this
        findViewById<TextView>(R.id.profile_btn).setOnClickListener {
            IntentManager.goTo(self, ProfileActivity::class.java)
        }

        findViewById<TextView>(R.id.invitation_btn).setOnClickListener {
            IntentManager.goTo(self, InvitationActivity::class.java)
        }

        findViewById<TextView>(R.id.asking_btn).setOnClickListener {
            IntentManager.goTo(self, AskingActivity::class.java)
        }


        findViewById<TextView>(R.id.logout_btn).setOnClickListener {
            Storage.put(self, "token", "")
            IntentManager.goTo(self, MainActivity::class.java)
        }

        findViewById<TextView>(R.id.cgu_btn).setOnClickListener {
            IntentManager.goTo(self, CGUActivity::class.java)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)
        setListeners()
    }
}
