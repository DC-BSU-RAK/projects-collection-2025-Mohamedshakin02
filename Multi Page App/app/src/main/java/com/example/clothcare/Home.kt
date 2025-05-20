package com.example.clothcare

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.PathParser.PathDataNode
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Load the layout file (fragment_home.xml)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Access the TextView from home fragment
        val userNameTextView = view.findViewById<TextView>(R.id.userName)

        // Retrieve the saved username from login page
        val sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "User")

        // Show the welcome message with the username
        userNameTextView.text = "Welcome,\n$username"

        // Setup click listeners for each clothing item
        setupShirtClick(view, R.id.shirt_button)
        setupTshirtClick(view, R.id.tshirt_button)
        setupPantClick(view, R.id.pant_button)
        setupJacketClick(view, R.id.jacket_button)
        setupHoodieClick(view, R.id.hoodie_button)

        return view
    }

    // When shirt button is clicked, redirects to ShirtSelection fragment
    private fun setupShirtClick(view: View, id: Int) {
        val layout = view.findViewById<LinearLayout>(id)
        layout.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.frameLayout, ShirtSelection())
                addToBackStack(null)
            }
        }
    }

    // When t-shirt button is clicked, redirects to T-shirtSelection fragment
    private fun setupTshirtClick(view: View, id: Int) {
        val layout = view.findViewById<LinearLayout>(id)
        layout.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.frameLayout, TshirtSelection())
                addToBackStack(null)
            }
        }
    }

    // When pant button is clicked, redirects to PantSelection fragment
    private fun setupPantClick(view: View, id: Int) {
        val layout = view.findViewById<LinearLayout>(id)
        layout.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.frameLayout, PantSelection())
                addToBackStack(null)
            }
        }
    }

    // When jacket button is clicked, redirects to JacketSelection fragment
    private fun setupJacketClick(view: View, id: Int) {
        val layout = view.findViewById<LinearLayout>(id)
        layout.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.frameLayout, JacketSelection())
                addToBackStack(null)
            }
        }
    }

    // When jacket button is clicked, redirects to JacketSelection fragment
    private fun setupHoodieClick(view: View, id: Int) {
        val layout = view.findViewById<LinearLayout>(id)
        layout.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.frameLayout, HoodieSelection())
                addToBackStack(null)
            }
        }
    }


}
