package com.example.clothcare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginPage : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Declare variables for user input fields and login button
        usernameInput = findViewById(R.id.userInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)

        // SharedPreferences to store login data
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Sets a click listener on the login button
        loginButton.setOnClickListener {

            // Gets trimmed input from EditText fields
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // Checks if both username and password fields are not empty
            if (username.isNotEmpty() && password.isNotEmpty()) {

                // Saves username and password into SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password) // Optional
                editor.apply()

                // Displays confirmation message that login is successful
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                // Navigate to the Home Fragment
                val intent = Intent(this, MainActivity::class.java) // Replace with your actual page
                startActivity(intent)
            }

            // displays a message if any of the fields are empty
            else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
