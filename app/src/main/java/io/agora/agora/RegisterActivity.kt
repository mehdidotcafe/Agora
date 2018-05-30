package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import org.json.JSONObject

class RegisterActivity : Layout() {
    val self: RegisterActivity = this


    fun getInputString(input: Int): String
    {
        val editText: EditText = findViewById<EditText>(input)

        return editText.getText().toString()
    }

    fun sendRequestRegister(email: String, password: String, firstname: String, lastname: String, location: String) {
        var obj: JSONObject = JSONObject()

        obj.put("email", email)
        obj.put("password", password)
        obj.put("first_name", firstname)
        obj.put("last_name", lastname)
        obj.put("location", location)

        println(obj)
        Request.getInstance().send(this, "users", "POST", JSONObject(), obj, object: Callback() {
            override fun success(obj: Any? ) {
                IntentManager.goTo(self, MainActivity::class.java)
            }
        })
    }

    fun setListeners() {

        findViewById<Button>(R.id.register_button).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val email: String = getInputString(R.id.emailInput)
                val password: String = getInputString(R.id.passwordInput)
                val confPassword: String = getInputString(R.id.confPasswordInput)
                val firstname: String = getInputString(R.id.firstname_container)
                val lastname: String = getInputString(R.id.lastname_container)
                val location: String = getInputString(R.id.location_container)

                if (password.length >= 9 && password == confPassword && firstname.isNotEmpty() && lastname.isNotEmpty() && location.isNotEmpty()) {
                    sendRequestRegister(email, password, firstname, lastname, location)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_register, true)

        setListeners()
    }

}
