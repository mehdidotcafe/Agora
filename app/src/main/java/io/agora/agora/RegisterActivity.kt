package io.agora.agora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import io.agora.agora.component.Alert
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Request
import io.agora.agora.entities.Storage
import org.json.JSONArray
import org.json.JSONObject

class RegisterActivity : Layout() {
    val self: RegisterActivity = this

    fun goToLogin(v: View) {
        IntentManager.goTo(self, MainActivity::class.java)
    }

    fun sendRequestRegister(email: String, password: String, firstname: String, lastname: String) {
        val obj = JSONObject()

        obj.put("email", email)
        obj.put("password", password)
        obj.put("first_name", firstname)
        obj.put("last_name", lastname)

        Request.getInstance().send(this, "users", "POST", JSONObject(), obj, object: Callback() {
            override fun success(obj: Any? ) {
                IntentManager.goTo(self, MainActivity::class.java)
            }

            override fun fail(error: String) {
                val errors = JSONArray(error)

                Alert.ok(self, getString(R.string.error), errors.getString(0) + '.', Callback())
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
                val hasAcceptedGSC : Boolean = findViewById<CheckBox>(R.id.checkbox_gsc).isChecked()

                if (password.length >= 9 && password == confPassword && firstname.isNotEmpty() && lastname.isNotEmpty() && hasAcceptedGSC) {
                    sendRequestRegister(email, password, firstname, lastname)
                } else if (!hasAcceptedGSC) {
                    Alert.ok(self, getString(R.string.error), getString(R.string.error_accept_cgu), Callback())
                } else {
                    Alert.ok(self, getString(R.string.error), getString(R.string.fill_all_fields), Callback())
                }
            }
        })

        findViewById<TextView>(R.id.cgu_button).setOnClickListener {
            IntentManager.goTo(this, CGUActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_register, true)

        setListeners()
    }

}
