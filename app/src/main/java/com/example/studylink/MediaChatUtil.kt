package com.example.studylink

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class MediaChatUtil {
    companion object {

        fun uploadToStorage(uri: Uri, type: String,  context: Context, ChatId: String, isGroup:Boolean) {
            var anjay = ""
            val storage = Firebase.storage
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            val sdft = SimpleDateFormat("HH:mm:ss")
            val currentTime = sdft.format(Date())

            // Create a storage reference from our app
            var storageRef = storage.reference

            val uniqueimagename = UUID.randomUUID()
            var spaceRef: StorageReference

            if (type == "Image"){
                spaceRef = storageRef.child("ChatMedia/$uniqueimagename.jpg")
            }else{
                spaceRef = storageRef.child("ChatMedia/$uniqueimagename.mp4")
            }
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            var byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            if(type == "Image"){
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
                byteArray = outputStream.toByteArray()
            }


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
                        val totData = ChatData.size + 1
                        spaceRef.downloadUrl.addOnSuccessListener {
                            db.collection("Chatgroup").document(ChatId).collection("ChatData").add(hashMapOf(
                                "Content" to "",
                                "ContentMedia" to it.toString(),
                                "MediaType" to type,
                                "TheUser" to currUser.value.email,
                                "TimeSent" to currentTime,
                                "TimeDate" to currentDate,
                                "OrderNo" to totData
                            )).addOnSuccessListener {
                                Toast.makeText(context,"Image/Video Successfully sent",Toast.LENGTH_SHORT)
                            }.addOnFailureListener{
                                Toast.makeText(context,"Image/Video Failed to sent",Toast.LENGTH_SHORT)
                            }
                        }
                    }else{
                        val totData = ChatData.size + 1
                        spaceRef.downloadUrl.addOnSuccessListener {
                            db.collection("Chats").document(ChatId).collection("ChatData").add(hashMapOf(
                                "Content" to "",
                                "ContentMedia" to it.toString(),
                                "MediaType" to type,
                                "TheUser" to currUser.value.email,
                                "TimeSent" to currentDate,
                                "TimeSent" to currentTime,
                                "TimeDate" to currentDate,
                                "OrderNo" to totData
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