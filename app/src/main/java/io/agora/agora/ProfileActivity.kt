package io.agora.agora

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.*
import com.squareup.picasso.RequestCreator
import io.agora.agora.adapters.DiplomaArrayAdapter
import io.agora.agora.adapters.ExperienceArrayAdapter
import io.agora.agora.entities.serialization.Diploma
import io.agora.agora.entities.serialization.Experience
import io.agora.agora.entities.serialization.User
import io.agora.agora.rendering.RemoteImageLoader
import jp.wasabeef.picasso.transformations.BlurTransformation
import org.json.JSONObject
import io.agora.agora.adapters.SkillArrayAdapter
import io.agora.agora.entities.*

class ProfileActivity : Layout() {

    fun setListeners() {
        findViewById<FloatingActionButton>(R.id.edit_btn).setOnClickListener {
            IntentManager.goTo(this, ProfileEditActivity::class.java)
        }
    }

    private fun getUser(id: String?, callback: (User) -> Unit) {
        if (id == null) {
            ConnectedUser.get(this, object: Callback() {
                override fun success(obj: Any?) {
                    callback(obj as User)
                }
            })
        } else {
            Request.getInstance().send(this, "users/$id", "GET", JSONObject(), JSONObject(), object: Callback() {
                override fun success(obj: Any?) {
                    val objJSON = obj as JSONObject
                    val data = objJSON.getJSONObject("data")

                    callback(User(data))
                }
            })
        }

    }

    private fun updateSkillList(user: User) {
        val view = findViewById<GridView>(R.id.skill_list)
        view.adapter = SkillArrayAdapter(this, user.tags)

        LayoutManager.setGridViewHeight(view)
    }

    fun updateDisplay(user: User) {
        val self = this

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

        findViewById<TextView>(R.id.name_container).text = user.firstname + ' ' + user.lastname
        findViewById<TextView>(R.id.function_container).text = user.function
        findViewById<TextView>(R.id.description_container).text = user.description
        findViewById<TextView>(R.id.place_container).text = user.location

        updateSkillList(user)
    }

    private fun getExperienceAndDiplomaAndDisplay() {
        val self = this

        ConnectedUser.get(this, object: Callback() {
            override fun success(obj: Any?) {
                val user = obj as User

                Request.getInstance().send(self, "users/${user.id}/experiences", "GET", JSONObject(), JSONObject(), object: Callback() {
                    override fun success(obj: Any?) {
                        var arr = arrayOf<Experience>()
                        val experiences = (obj as JSONObject).getJSONArray("data")

                        for (i in 0 until experiences.length()) {
                            arr += Experience(experiences.getJSONObject(i))
                        }
                        val adapter = ExperienceArrayAdapter(self, arr)

                        val view = findViewById<ListView>(R.id.experiences_list)

                        view.adapter = adapter

                        LayoutManager.setListViewHeight(view)
                    }
                })

                Request.getInstance().send(self, "users/${user.id}/educations", "GET", JSONObject(), JSONObject(), object: Callback() {
                    override fun success(obj: Any?) {
                        var arr = arrayOf<Diploma>()
                        val diploma = (obj as JSONObject).getJSONArray("data")
                        val view = findViewById<ListView>(R.id.diploma_list)

                        for (i in 0 until diploma.length()) {
                            arr += Diploma(diploma.getJSONObject(i))
                        }
                        val adapter = DiplomaArrayAdapter(self, arr)


                        view.adapter = adapter

                        LayoutManager.setListViewHeight(view)
                    }
                })
            }
        })
    }

    private fun updateButtonDisplay(id: String?) {
        findViewById<FloatingActionButton>(R.id.edit_btn).visibility = if (id == null) View.VISIBLE else View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var id: String? = null

        if (intent.hasExtra("params")) {
            val params: JSONObject = Serializer.unserialize(intent.getStringExtra("params"))
            id = if (params.has("id")) params.getString("id") else null
        }



        updateButtonDisplay(id)
        setListeners()
        getUser(id){user: User ->
            updateDisplay(user)
        }

        getExperienceAndDiplomaAndDisplay()
    }
}
