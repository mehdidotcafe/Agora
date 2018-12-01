package io.agora.agora

import android.os.Bundle
import android.widget.GridView
import io.agora.agora.adapters.InvitationArrayAdapter
import io.agora.agora.entities.Callback
import io.agora.agora.entities.ConnectedUser
import io.agora.agora.entities.Request
import io.agora.agora.entities.serialization.Invitation
import io.agora.agora.entities.serialization.User
import org.json.JSONObject

class AskingActivity : Layout() {

    fun getDataAndDisplay() {
        val self = this
        ConnectedUser.get(this, object: Callback() {
            override fun success(obj: Any?) {
                val user = obj as User

                Request.getInstance().send(self, "users/${user.id}/invitations", "GET", JSONObject(), JSONObject(), object: Callback() {
                    override fun success(obj: Any?) {
                        val fullInvitation = (obj as JSONObject).getJSONArray("data")
                        var invitations = arrayOf<Invitation>()

                        for (i in 0 until fullInvitation.length()) {
                            if (fullInvitation.getJSONObject(i).getInt("sender_id") == user.id) {
                                invitations += Invitation(fullInvitation.getJSONObject(i))
                            }
                        }

                        findViewById<GridView>(R.id.invitation_list).adapter = InvitationArrayAdapter(self, invitations, false)
                    }
                })

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_asking, false)
        getDataAndDisplay()
    }
}
