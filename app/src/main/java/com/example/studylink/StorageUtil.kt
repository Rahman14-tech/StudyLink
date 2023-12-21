package com.example.studylink

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.util.UUID
class StorageUtil{


    companion object {

        fun uploadToStorage(uri: Uri, context: Context, type: String, email: String, fullName: String, cont: Context, pass: String, studyField:String) {
            var anjay = ""
            val storage = Firebase.storage

            // Create a storage reference from our app
            var storageRef = storage.reference

            val uniqueimagename = UUID.randomUUID()
            var spaceRef: StorageReference

            if (type == "image"){
                spaceRef = storageRef.child("UsersPhoto/$uniqueimagename.jpg")
            }else{
                spaceRef = storageRef.child("videos/$uniqueimagename.mp4")
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
                    if(email.isNotEmpty() && fullName.isNotEmpty() && pass.isNotEmpty() && studyField != "Choose here") {
                        spaceRef.downloadUrl.addOnSuccessListener {
                            db.collection("Users").add(hashMapOf(
                                "email" to email,
                                "fullName" to fullName,
                                "imageURL" to it.toString(),
                                "strongAt" to mutableListOf<String>(),
                                "wantStudy" to mutableListOf<String>(),
                                "bio" to "",
                                "studyField" to studyField
                            )).addOnSuccessListener {
                                Toast.makeText(cont, "Registration Success", Toast.LENGTH_LONG).show()
                                tempUrl.value = TextFieldValue("")
                            }.addOnFailureListener{
                                Toast.makeText(cont, "Registration Failed", Toast.LENGTH_LONG).show()
                                tempUrl.value = TextFieldValue("")
                            }
                        }
                    }else{
                        Toast.makeText(cont, "All forms must be filled first", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}