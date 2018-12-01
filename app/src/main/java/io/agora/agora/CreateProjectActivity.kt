package io.agora.agora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import io.agora.agora.component.Alert
import io.agora.agora.component.CustomSpinner
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import org.json.JSONObject

class CreateProjectActivity : AppCompatActivity() {

    fun getInputString(input: Int): String
    {
        val editText: EditText = findViewById(input)

        return editText.text.toString()
    }


    fun setListeners() {
        val self = this
        findViewById<Button>(R.id.next_button).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val name: String = getInputString(R.id.project_name)
                val shortDescription: String = getInputString(R.id.project_short_description)
                val category: String = findViewById<Spinner>(R.id.project_category).selectedItem.toString()
                val description: String = getInputString(R.id.project_description)
                val obj = JSONObject()

                obj.put("name", name)
                obj.put("description", name)
                obj.put("short_description", shortDescription)
                obj.put("category", category)

                if (name == "" || shortDescription == "" || category == "" || description == "") {
                    Alert.ok(self, getString(R.string.project_creation), getString(R.string.fill_all_fields), Callback())
                } else {
                    IntentManager.goTo(self, CreateProjectStep2Activity::class.java, obj)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)
        CustomSpinner.setCategories(this, findViewById(R.id.project_category))
        setListeners()
    }
}
