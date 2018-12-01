package io.agora.agora.entities.serialization

import org.json.JSONObject

class Invitation(json: JSONObject) {
    val id = json.getInt("invitations_id")
    val obj = json.getString("object")
    val message = json.getString("message")
    val status = json.getInt("status")
    val created = json.getString("created")
    val projectId = json.getString("project_id")
    val projectName = json.getString("project_name")
    val projectDescription = json.getString("project_description")
    val location = json.getString("location")
    val senderId = json.getString("sender_id")
    val senderFirstName = json.getString("sender_first_name")
    val receiverId = json.getString("receiver_id")
    val receiverName = json.getString("receiver_name")
}