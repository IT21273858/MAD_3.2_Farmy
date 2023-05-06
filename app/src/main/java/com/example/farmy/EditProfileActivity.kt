package com.example.farmy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditProfileActivity : AppCompatActivity() {
    var editName: EditText? = null
    var editEmail: EditText? = null
    var editUsername: EditText? = null
    var editPassword: EditText? = null
    private lateinit var saveButton: Button
    var nameUser: String? = null
    var emailUser: String? = null
    var usernameUser: String? = null
    var passwordUser: String? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        reference = FirebaseDatabase.getInstance().getReference("users")
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editUsername = findViewById(R.id.editUsername)
        editPassword = findViewById(R.id.editPassword)
        saveButton = findViewById(R.id.saveButton)
        showData()
        saveButton.setOnClickListener {
            if (isNameChanged || isPasswordChanged || isEmailChanged) {
                Toast.makeText(this@EditProfileActivity, "Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@EditProfileActivity, Activityprofile::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@EditProfileActivity, "No Changes Found", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private val isNameChanged: Boolean
        private get() = if (nameUser != editName!!.text.toString()) {
            reference!!.child(usernameUser!!).child("name").setValue(editName!!.text.toString())
            nameUser = editName!!.text.toString()
            true
        } else {
            false
        }
    private val isEmailChanged: Boolean
        private get() {
            return if (emailUser != editEmail!!.text.toString()) {
                reference!!.child(usernameUser!!).child("email")
                    .setValue(editEmail!!.text.toString())
                emailUser = editEmail!!.text.toString()
                true
            } else {
                false
            }
        }
    private val isPasswordChanged: Boolean
        private get() {
            return if (passwordUser != editPassword!!.text.toString()) {
                reference!!.child(usernameUser!!).child("password")
                    .setValue(editPassword!!.text.toString())
                passwordUser = editPassword!!.text.toString()
                true
            } else {
                false
            }
        }

    private fun showData() {
        val intent = intent
        nameUser = intent.getStringExtra("name")
        emailUser = intent.getStringExtra("email")
        usernameUser = intent.getStringExtra("username")
        passwordUser = intent.getStringExtra("password")
        editName!!.setText(nameUser)
        editEmail!!.setText(emailUser)
        editUsername!!.setText(usernameUser)
        editPassword!!.setText(passwordUser)
    }
}