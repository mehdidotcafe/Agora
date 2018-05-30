package io.agora.agora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.agora.agora.entities.Callback
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.User
import org.json.JSONObject

class ProfileActivity : Layout() {

    fun setListener() {
        val btn: Button = findViewById<Button>(R.id.update_button)

        btn.setOnClickListener {
            val firstname = getInputString(R.id.firstname_input)
            val lastname = getInputString(R.id.lastname_input)
            val phone = getInputString(R.id.phone_input)
            val email = getInputString(R.id.email_input)
            val location = getInputString(R.id.location_input)

            println("DATA TO UPDATE")
            println(firstname)
            println(lastname)
            println(phone)
            println(email)
            println(location)
        }
    }

    fun getInputString(input: Int): String
    {
        val editText: EditText = findViewById(input)

        return editText.getText().toString()
    }

    fun getUser(callback: (User) -> Unit) {
        Request.getInstance().send(this, "users/me" , "GET", JSONObject(), JSONObject(), object: Callback() {
            override fun success(obj: Any?) {
                val objJSON = obj as JSONObject
                val data = objJSON.getJSONObject("data")

                callback(User(data))
            }
        })
    }

    fun updateDisplay(user: User) {
        findViewById<EditText>(R.id.firstname_input).setText(user.firstname)
        findViewById<EditText>(R.id.lastname_input).setText(user.lastname)
        findViewById<EditText>(R.id.email_input).setText(user.email)
        findViewById<EditText>(R.id.location_input).setText(user.location)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getUser{user: User ->
            updateDisplay(user)
        }
    }
}
