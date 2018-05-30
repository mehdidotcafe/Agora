package io.agora.agora

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import org.json.JSONObject

class MainActivity : Layout() {
    val EXTRA_MESSAGE: String = "agora.io.MESSAGE"
    val self: MainActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_main, false)
        testAutoConnection()
    }

    fun testAutoConnection() {

    }

    fun login(username: String, password: String)
    {
        var obj: JSONObject = JSONObject()

        obj.put("email", username)
        obj.put("password", password)
        Request.getInstance().send(this, "login", "POST", JSONObject(), obj, object: Callback() {
            override fun success(obj: Any?) {
                val ret: JSONObject = obj as JSONObject

                Request.getInstance().addHeader("Authorization", "JWT " + ret.getString("auth_token"))
                IntentManager.replace(self, ProjectListActivity::class.java)
            }
        })
    }

    fun getInputString(input: Int): String
    {
        val editText: EditText = findViewById(input)

        return editText.getText().toString()
    }

    fun register(view: View)
    {
        IntentManager.goTo(this, RegisterActivity::class.java)
    }

    fun forgotPassword(view: View)
    {
        IntentManager.goTo(this, ForgotPasswordActivity::class.java)
    }

    fun auth(view: View)
    {
        val username = this.getInputString(R.id.usernameInput)
        val password= this.getInputString(R.id.passwordInput)
        println("bonjour")
        println(username)
        println(password)
        login(username, password)
    }

    fun sendMessage(view: View)
    {
        val intent: Intent = Intent(this,  DisplayMessageActivity::class.java)
        val editText: EditText = findViewById(R.id.passwordInput)
        val message: String = editText.getText().toString()

        intent.putExtra(EXTRA_MESSAGE, message)
        startActivity(intent)
    }
}
