package com.example.studylink

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class MediaChatUtil {
    companion object {

        fun uploadToStorage(uri: Uri, type: String,  context: Context, ChatId: String, isGroup:Boolean) {
            var anjay = ""
            val storage = Firebase.storage
            val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
            val currentDate = sdf.format(Date())

            // Create a storage reference from our app
            var storageRef = storage.reference

            val uniqueimagename = UUID.randomUUID()
            var spaceRef: StorageReference

            if (type == "Image"){
                spaceRef = storageRef.child("ChatMedia/$uniqueimagename.jpg")
            }else{
                spaceRef = storageRef.child("ChatMedia/$uniqueimagename.mp4")
            }
            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let{

                var uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnSuccessListener { taskSnapshot ->
                    if(isGroup){
                        spaceRef.downloadUrl.addOnSuccessListener {
                            db.collection("Chatgroup").document(ChatId).collection("ChatData").add(hashMapOf(
                                "Content" to "",
                                "ContentMedia" to it.toString(),
                                "MediaType" to type,
                                "TheUser" to currUser.value.email,
                                "TimeSent" to currentDate,
                            )).addOnSuccessListener {
                                Toast.makeText(context,"Image/Video Successfully sent",Toast.LENGTH_SHORT)
                            }.addOnFailureListener{
                                Toast.makeText(context,"Image/Video Failed to sent",Toast.LENGTH_SHORT)
                            }
                        }
                    }else{
                        spaceRef.downloadUrl.addOnSuccessListener {
                            db.collection("Chats").document(ChatId).collection("ChatData").add(hashMapOf(
                                "Content" to "",
                                "ContentMedia" to it.toString(),
                                "MediaType" to type,
                                "TheUser" to currUser.value.email,
                                "TimeSent" to currentDate,
                            )).addOnSuccessListener {
                                Toast.makeText(context,"Image/Video Successfully sent",Toast.LENGTH_SHORT)
                            }.addOnFailureListener{
                                Toast.makeText(context,"Image/Video Failed to sent",Toast.LENGTH_SHORT)
                            }
                        }
                    }

                    }
            }
        }

    }
}