package io.agora.agora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.agora.agora.component.Alert
import io.agora.agora.entities.Callback
import io.agora.agora.entities.IntentManager
import io.agora.agora.entities.Serializer
import org.json.JSONObject

class CreateProjectStep2Activity : AppCompatActivity() {
    var oldParams: JSONObject = JSONObject()

    fun getInputString(input: Int): String
    {
        val editText: EditText = findViewById(input)

        return editText.text.toString()
    }

    fun setCalendarListener() {

    }

    fun setListeners() {
        val self = this
        setCalendarListener()
        findViewById<Button>(R.id.next_button).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val effectives: String = getInputString(R.id.project_effectives)
                val tags: String = getInputString(R.id.project_tags)
                val place: String = getInputString(R.id.project_place)
                val start: String = getInputString(R.id.project_start)
                val end: String = getInputString(R.id.project_end)
                val arrayTags = tags.split(',').map {t -> val obj = JSONObject()
                    obj.put("name", t)
                    obj
                }

                self.oldParams.put("effectives", effectives)
                self.oldParams.put("hashtags", arrayTags)
                self.oldParams.put("location", place)
                self.oldParams.put("start_date", start)
                self.oldParams.put("end_date", end)
                if (effectives == "" || tags == "" || place == "" || end == "") {
                    Alert.ok(self, getString(R.string.project_creation), getString(R.string.fill_all_fields), Callback())
                } else {
                    IntentManager.goTo(self, CreateProjectStep3Activity::class.java, oldParams)
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.oldParams = Serializer.unserialize(intent.getStringExtra("params"))
        setContentView(R.layout.activity_create_project_step2)
        setListeners()
    }
}
