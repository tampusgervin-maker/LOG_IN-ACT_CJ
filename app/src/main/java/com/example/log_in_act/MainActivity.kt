package com.example.log_in_act

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Hardcoded credentials for validation
    private val correctUsername = "admin"
    private val correctPassword = "password123"

    // UI Variables
    private lateinit var formGroup: LinearLayout
    private lateinit var tvWelcomeMessage: TextView
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link variables to the XML layout from earlier
        formGroup = findViewById(R.id.form_group)
        tvWelcomeMessage = findViewById(R.id.tv_welcome_message)
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validate fields are filled in
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // IDEA 1: Send intent to route to the same singleTop Activity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USERNAME", username)
            intent.putExtra("PASSWORD", password)
            startActivity(intent)
        }
    }

    // This function intercepts the Intent sent by the button click
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        // Extract the passed credentials
        val enteredUsername = intent.getStringExtra("USERNAME") ?: ""
        val enteredPassword = intent.getStringExtra("PASSWORD") ?: ""

        // Check against hardcoded values
        if (enteredUsername == correctUsername && enteredPassword == correctPassword) {
            // Success: Hide form, show welcome message
            formGroup.visibility = View.GONE
            tvWelcomeMessage.visibility = View.VISIBLE
            tvWelcomeMessage.text = "Welcome, $enteredUsername!"
        } else {
            // Failure: Show error, clear password field
            Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_LONG).show()
            etPassword.text.clear()
        }
    }
}