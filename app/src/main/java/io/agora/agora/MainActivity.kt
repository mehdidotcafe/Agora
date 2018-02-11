package io.agora.agora

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.EditText
import io.agora.agora.Layout

class MainActivity : Layout() {
    val EXTRA_MESSAGE: String = "agora.io.MESSAGE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_main, false)
        login("toto", "aqwzsxedc")
    }

    fun login(username: String, password: String)
    {
        if (username == "toto" && (password == "qazwsxedc" || password == "aqwzsxedc"))
        {
            println(username + " se connecte");
            val intent: Intent = Intent(this,  ProjectListActivity::class.java)

            startActivity(intent)
        }
        else
        {
            println(username + " se connecte pas");
        }
    }

    fun getInputString(input: Int): String
    {
        val editText: EditText = findViewById(input)

        return editText.getText().toString()
    }

    fun register(view: View)
    {
        val intent: Intent = Intent(this,  RegisterActivity::class.java)

        startActivity(intent)
    }

    fun forgotPassword(view: View)
    {
        val intent: Intent = Intent(this,  ForgotPasswordActivity::class.java)

        startActivity(intent)
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
