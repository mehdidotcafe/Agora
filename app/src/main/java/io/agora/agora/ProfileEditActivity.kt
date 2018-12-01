package io.agora.agora

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.squareup.picasso.RequestCreator
import io.agora.agora.component.Alert
import io.agora.agora.entities.*
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader
import jp.wasabeef.picasso.transformations.BlurTransformation
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayInputStream

class ProfileEditActivity : Layout() {
    var image: String? = ""

    private fun updateDisplayFromUser(user: User) {
        val self = this
        findViewById<EditText>(R.id.firstname_input).setText(user.firstname)
        findViewById<EditText>(R.id.lastname_input).setText(user.lastname)
        findViewById<EditText>(R.id.location_input).setText(user.location)
        findViewById<EditText>(R.id.function_input).setText(user.function)
        findViewById<EditText>(R.id.link_github_input).setText(user.linkGithub)
        findViewById<EditText>(R.id.link_linkedin_input).setText(user.linkLinkedin)
        findViewById<EditText>(R.id.link_twitter_input).setText(user.linkTwitter)
        findViewById<EditText>(R.id.description_input).setText(user.description)
        findViewById<EditText>(R.id.skill_input).setText(user.tags.joinToString())

        if (user.picture != null && user.picture != "null" && user.picture != "") {
            RemoteImageLoader.load(this, findViewById(R.id.user_image_background), user.picture, object: Callback() {
                override fun success(obj: Any? ) {
                    val img = obj as RequestCreator

                    img.transform(BlurTransformation(self))
                }
            })
            RemoteImageLoader.load(this, findViewById(R.id.user_image), user.picture)
        } else {
            RemoteImageLoader.load(this, findViewById(R.id.user_image_background), R.drawable.rectangle2, object: Callback() {
                override fun success(obj: Any? ) {
                    val img = obj as RequestCreator

                    img.transform(BlurTransformation(self))
                }
            })
            RemoteImageLoader.load(this, findViewById(R.id.user_image), R.drawable.no_avatar)
        }

    }

    private fun updateDisplay() {
        ConnectedUser.get(this, object: Callback() {
            override fun success(obj: Any? ) {
                val user = obj as User

                updateDisplayFromUser(user)
            }
        })
    }

    private fun setUpdateListener() {
        val self = this

        findViewById<FloatingActionButton>(R.id.save_button).setOnClickListener{
            val firstname = findViewById<EditText>(R.id.firstname_input).text.toString()
            val lastname = findViewById<EditText>(R.id.lastname_input).text.toString()
            val location = findViewById<EditText>(R.id.location_input).text.toString()
            val function = findViewById<EditText>(R.id.function_input).text.toString()
            val github = findViewById<EditText>(R.id.link_github_input).text.toString()
            val linkedin = findViewById<EditText>(R.id.link_linkedin_input).text.toString()
            val twitter = findViewById<EditText>(R.id.link_twitter_input).text.toString()
            val description = findViewById<EditText>(R.id.description_input).text.toString()
            val skills = findViewById<EditText>(R.id.skill_input).text.toString().split(',')

            if (firstname.isEmpty()) {
                Alert.ok(this, getString(R.string.error), getString(R.string.fill_firstname), Callback())
            } else if (lastname.isEmpty()) {
                Alert.ok(this, getString(R.string.error), getString(R.string.fill_lastname), Callback())
            } else if (location.isEmpty()) {
                Alert.ok(this, getString(R.string.error), getString(R.string.fill_place), Callback())
            } else {
                val obj = JSONObject()
                val skillObj = JSONArray()

                skills.forEach{
                    skillObj.put(it.trim())
                }
                obj.put("first_name", firstname)
                obj.put("last_name", lastname)
                obj.put("location", location)
                obj.put("function", function)
                obj.put("link_github", github)
                obj.put("link_linkedin", linkedin)
                obj.put("link_twitter", twitter)
                obj.put("description", description)
                obj.put("tags", skillObj)
                if (this.image !== null && this.image!!.isNotEmpty()) {
                    obj.put("picture", image)
                }

                Request.getInstance().send(self, "users/me", "PUT", JSONObject(), obj, object: Callback() {
                    override fun success(ret: Any?) {
                        val jsonObj = ret as JSONObject

                            ConnectedUser.refresh(self, object: Callback() {
                                override fun success(suser: Any?) {
                                    Alert.ok(self, getString(R.string.update_2), getString(R.string.success_account_update), Callback())
                                }
                            })
                    }

                    override fun fail(error: String) {
                        Alert.ok(self, getString(R.string.update_2), getString(R.string.error_account_update), Callback())
                    }
                })
            }
        }
    }

    private fun displayImage() {
        val decodedString = Base64.decode(this.image, Base64.NO_WRAP)
        val input = ByteArrayInputStream(decodedString)
        val extPic = BitmapFactory.decodeStream(input)

        findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.user_image).setImageBitmap(extPic)
        //findViewById<ImageView>(R.id.user_image_background).setImageBitmap(extPic)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            this.image = BitmapGetter.get(this, data, 1000)
            this.displayImage()
        } else {
            Alert.ok(this, getString(R.string.error), getString(R.string.image_too_heavy), Callback())
        }
    }

    private fun setImageUploadListener() {
        findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.user_image).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent()
                // Show only images, no videos or anything else
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, resources.getString(R.string.select_picture)), 1)
            }
        })
    }

    private fun setAddListeners() {
        val self = this

        findViewById<TextView>(R.id.add_experience_btn).setOnClickListener {
            IntentManager.goTo(self, AddExperienceActivity::class.java)
        }

        findViewById<TextView>(R.id.add_diploma_btn).setOnClickListener {
            IntentManager.goTo(self, AddDiplomaActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        setImageUploadListener()
        setUpdateListener()
        setAddListeners()
        updateDisplay()
    }
}
