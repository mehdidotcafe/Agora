package io.agora.agora

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import io.agora.agora.component.Alert
import android.graphics.BitmapFactory
import android.util.Base64
import io.agora.agora.entities.*
import io.agora.agora.entities.serialization.User
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayInputStream


class CreateProjectStep3Activity : AppCompatActivity() {
    var image: String? = ""
    var oldParams: JSONObject = JSONObject()

    private fun displayImage() {
        val decodedString = Base64.decode(this.image, Base64.NO_WRAP)
        val input = ByteArrayInputStream(decodedString)
        val extPic = BitmapFactory.decodeStream(input)

        findViewById<ImageView>(R.id.image_preview).setImageBitmap(extPic)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            this.image = BitmapGetter.get(this, data, 1000)
            this.displayImage()
        } else {
            Alert.ok(this, getString(R.string.project_creation), getString(R.string.image_too_heavy), Callback())
        }
    }

    fun sendProject() {
        val self = this

        ConnectedUser.get(this, object: Callback() {
            override fun success(obj: Any?) {
                val user = obj as User

                oldParams.put("user_id", user.id.toString())
                oldParams.put("picture", self.image)

                Request.getInstance().send(self, "project", "POST", JSONObject(), oldParams, object: Callback() {
                    override fun success(obj: Any?) {
                        IntentManager.goTo(self, MyProjectsActivity::class.java)
                    }

                    override fun fail(error: String) {
                        val errors = JSONArray(error)

                        Alert.ok(self, getString(R.string.project_creation), errors.getString(0), Callback())
                    }
                })
            }
        })
    }

    fun setListeners() {
        val self = this

        findViewById<RelativeLayout>(R.id.file_uploader).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent()
                // Show only images, no videos or anything else
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, resources.getString(R.string.select_picture)), 1)
            }
        })

        findViewById<Button>(R.id.save_button).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (self.image != "") {
                    self.sendProject()
                } else {
                    Alert.ok(self, getString(R.string.project_creation), getString(R.string.pick_image), Callback())
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.oldParams = Serializer.unserialize(intent.getStringExtra("params"))
        setContentView(R.layout.activity_create_project_step3)
        setListeners()
    }
}
