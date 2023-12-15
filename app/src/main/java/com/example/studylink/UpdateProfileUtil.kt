package com.example.studylink

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID
class UpdateProfileUtil{


    companion object {
        fun uploadToStorage(
            uri: Uri,
            context: Context,
            cont: Context,
        ) {
            val storage = Firebase.storage

            // Create a storage reference from our app
            var storageRef = storage.reference

            val uniqueimagename = UUID.randomUUID()
            var spaceRef: StorageReference

            spaceRef = storageRef.child("UsersPhoto/$uniqueimagename.jpg")

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
                }.addOnSuccessListener {snaps ->
                    spaceRef.downloadUrl.addOnSuccessListener {
                        db.collection("Users").document(currUser.value.id).update("imageURL", it.toString()).addOnSuccessListener {
                            tempUrl.value = TextFieldValue("")
                        }.addOnFailureListener{
                            Toast.makeText(cont, "Upload Failed", Toast.LENGTH_LONG).show()
                            tempUrl.value = TextFieldValue("")
                        }
                        currUser.value.imageURL = it.toString()
                    }
                }
            }
        }

    }
}