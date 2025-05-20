package com.example.clothcare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class Result : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Loads the layout file (fragment_result.xml)
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        // Function for back button to return to the previous screen
        val backButton = view.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Initiating variables by getting components by ID
        val resultHeading = view.findViewById<TextView>(R.id.resultHeading)
        val washHeading = view.findViewById<TextView>(R.id.washHeading)
        val washImage = view.findViewById<ImageView>(R.id.washImage)
        val washText = view.findViewById<TextView>(R.id.washText)
        val ironHeading = view.findViewById<TextView>(R.id.ironHeading)
        val ironImage = view.findViewById<ImageView>(R.id.ironImage)
        val ironText = view.findViewById<TextView>(R.id.ironText)

        // Retrieves the data that was sent to this fragment
        val heading = arguments?.getString("heading") ?: "Care Instructions"
        val washingTips = arguments?.getString("washingTips")?.trim() ?: ""
        val ironingTips = arguments?.getString("ironingTips")?.trim() ?: ""

        // Updates the heading of result page
        resultHeading.text = heading

        // Shows both washing and ironing tips if both are selected
        if (washingTips.isNotEmpty() && ironingTips.isNotEmpty()) {
            // Both selected - show both normally
            washHeading.text = "Washing Tips:"
            washImage.setImageResource(R.drawable.wash)
            washText.text = washingTips
            washHeading.visibility = View.VISIBLE
            washImage.visibility = View.VISIBLE
            washText.visibility = View.VISIBLE

            ironHeading.text = "Ironing Tips:"
            ironImage.setImageResource(R.drawable.iron)
            ironText.text = ironingTips
            ironHeading.visibility = View.VISIBLE
            ironImage.visibility = View.VISIBLE
            ironText.visibility = View.VISIBLE

        }

        // if ironing chip only selected then it shows only ironing tips in place of wash section and ironing section will be hided.
        else if (ironingTips.isNotEmpty()) {
            washHeading.text = "Ironing Tips:"
            washImage.setImageResource(R.drawable.iron)
            washText.text = ironingTips
            washHeading.visibility = View.VISIBLE
            washImage.visibility = View.VISIBLE
            washText.visibility = View.VISIBLE

            ironHeading.visibility = View.GONE
            ironImage.visibility = View.GONE
            ironText.visibility = View.GONE

        }

        // if washing chip only selected then it shows only washing tips and ironing section will be hided.
        else if (washingTips.isNotEmpty()) {

            washHeading.text = "Washing Tips:"
            washImage.setImageResource(R.drawable.wash)
            washText.text = washingTips
            washHeading.visibility = View.VISIBLE
            washImage.visibility = View.VISIBLE
            washText.visibility = View.VISIBLE

            ironHeading.visibility = View.GONE
            ironImage.visibility = View.GONE
            ironText.visibility = View.GONE

        }

        // If there are no tips, it hides all
        else {

            washHeading.visibility = View.GONE
            washImage.visibility = View.GONE
            washText.visibility = View.GONE
            ironHeading.visibility = View.GONE
            ironImage.visibility = View.GONE
            ironText.visibility = View.GONE
        }

        return view
    }

    companion object {
        // Static method to create a new instance of Result fragment with required data to display the output
        // based on selected option in result fragment (result page)
        fun newInstance(heading: String, washingTips: String, ironingTips: String): Result {
            val fragment = Result()
            val bundle = Bundle().apply {
                putString("heading", heading)
                putString("washingTips", washingTips)
                putString("ironingTips", ironingTips)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}

