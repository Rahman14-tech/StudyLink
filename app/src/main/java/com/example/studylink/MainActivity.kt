package com.example.studylink

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.studylink.ui.theme.StudyLinkTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

lateinit var auth: FirebaseAuth
lateinit var db: FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var tempAccess = false
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        db.collection("Users").get().addOnSuccessListener { queryDocumentSnapshots ->
            if(!queryDocumentSnapshots.isEmpty){
                val list = queryDocumentSnapshots.documents
                for(datum in list){
                    val c: ProfileFirestore? = datum.toObject(ProfileFirestore::class.java)
                    if (c != null){
                        Realusers.add(c)
                        Filteredusers.add(c)
                    }
                }
                println("ANJAY2 $Realusers")
                tempAccess = true
            }
        }

        
        setContent {
            StudyLinkTheme {
                val navController = rememberNavController()
                BottomBar(navController = navController)
            }
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            println("Already logged in")

        }
    }
}