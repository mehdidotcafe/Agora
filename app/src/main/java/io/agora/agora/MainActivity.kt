package io.agora.agora

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import io.agora.agora.component.Alert
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.Storage
import org.json.JSONObject

class MainActivity : Layout() {
    val EXTRA_MESSAGE: String = "agora.io.MESSAGE"
    val self: MainActivity = this


    fun setDefaultValue() {
        val email = Storage.get(self, "accountEmail")

        if (email != null && email != "") {
            findViewById<EditText>(R.id.usernameInput).setText(email)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_main, false)
        setDefaultValue()
        testAutoConnection()
    }

    private fun testAutoConnection() {
        val token: String? = Storage.get(self, "token")

        if (token != null && token != "") {
            Request.getInstance().addHeader("Authorization", "JWT " + token)
            IntentManager.replace(self, MyProjectsActivity::class.java)
        }
    }

    fun login(username: String, password: String)
    {
        val obj = JSONObject()

        obj.put("email", username)
        obj.put("password", password)
        Request.getInstance().send(this, "login", "POST", JSONObject(), obj, object: Callback() {
            override fun success(obj: Any?) {
                val ret: JSONObject = obj as JSONObject
                val token: String = ret.getString("auth_token")

                Storage.put(self, "accountEmail", username)
                Storage.put(self, "token", token)
                Request.getInstance().addHeader("Authorization", "JWT " + token)
                IntentManager.replace(self, MyProjectsActivity::class.java)
            }
        })
    }

    fun register(view: View)
    {
        IntentManager.goTo(this, RegisterActivity::class.java)
    }

    fun forgotPassword(view: View)
    {
        val self = this

        Alert.input(this, getString(R.string.forgotPassword), getString(R.string.fill_email), object: Callback() {
            override fun success(obj: Any?) {
                val toSend = JSONObject()
                val email = obj as String

                toSend.put("email", email)
                Request.getInstance().send(self, "login/forgot_password", "POST", toSend, toSend, Callback())
            }
        })
    }

    fun auth(view: View)
    {
        val username: String = this.getInputString(R.id.usernameInput)
        val password: String = this.getInputString(R.id.passwordInput)

        login(username, password)
    }

    fun sendMessage(view: View)
    {
        val intent = Intent(this,  DisplayMessageActivity::class.java)
        val editText: EditText = findViewById(R.id.passwordInput)
        val message: String = editText.text.toString()

        intent.putExtra(EXTRA_MESSAGE, message)
        startActivity(intent)
    }
}
