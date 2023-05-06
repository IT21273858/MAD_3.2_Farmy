package com.example.farmy

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*


class ProfileActivity : AppCompatActivity() {
    var profileName: TextView? = null
    var profileEmail: TextView? = null
    var profileUsername: TextView? = null
    var profilePassword: TextView? = null
    var titleName: TextView? = null
    var titleUsername: TextView? = null
    private lateinit var editProfile: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        profileUsername = findViewById(R.id.profileUsername)
        profilePassword = findViewById(R.id.profilePassword)
        titleName = findViewById(R.id.titleName)
        titleUsername = findViewById(R.id.titleUsername)
        editProfile = findViewById(R.id.editButton)
        showAllUserData()
        editProfile.setOnClickListener(View.OnClickListener { passUserData() })

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_product
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, Activityprofile::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_scan -> {
                    startActivity(Intent(applicationContext, Scan::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_chat -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_product -> {
                    startActivity(Intent(applicationContext, Grocery::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()

                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

    private fun showAllUserData() {
        val intent = intent
        val nameUser = intent.getStringExtra("name")
        val emailUser = intent.getStringExtra("email")
        val usernameUser = intent.getStringExtra("username")
        val passwordUser = intent.getStringExtra("password")
        titleName!!.text = nameUser
        titleUsername!!.text = usernameUser
        profileName!!.text = nameUser
        profileEmail!!.text = emailUser
        profileUsername!!.text = usernameUser
        profilePassword!!.text = passwordUser
    }

    private fun passUserData() {
        val userUsername = profileUsername!!.text.toString().trim { it <= ' ' }
        val reference = FirebaseDatabase.getInstance().getReference("users")
        val checkUserDatabase = reference.orderByChild("username").equalTo(userUsername)
        checkUserDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nameFromDB = snapshot.child(userUsername).child("name").getValue(
                        String::class.java
                    )
                    val emailFromDB = snapshot.child(userUsername).child("email").getValue(
                        String::class.java
                    )
                    val usernameFromDB = snapshot.child(userUsername).child("username").getValue(
                        String::class.java
                    )
                    val passwordFromDB = snapshot.child(userUsername).child("password").getValue(
                        String::class.java
                    )
                    val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
                    intent.putExtra("name", nameFromDB)
                    intent.putExtra("email", emailFromDB)
                    intent.putExtra("username", usernameFromDB)
                    intent.putExtra("password", passwordFromDB)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}