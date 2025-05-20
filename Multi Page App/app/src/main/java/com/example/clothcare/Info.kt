package com.example.clothcare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class Info : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Loads the layout file (fragment_info.xml)
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        // Access the TextView from info fragment
        val userNameTextView = view.findViewById<TextView>(R.id.userName)

        // Retrieve the saved username from login page
        val sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "User")

        // Shows the hello message with the username
        userNameTextView.text = "Hello,\n$username"

        // When logout button is clicked
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Clears saved login data
            sharedPreferences.edit().clear().apply()

            // Go back to the login page
            val intent = Intent(requireContext(), LoginPage::class.java)
            startActivity(intent)
        }

        return view
    }
}
