package com.example.clothcare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class JacketSelection : Fragment() {

    // Declaring variables
    private lateinit var chipGroupFabric: ChipGroup
    private lateinit var chipGroupCare: ChipGroup
    private lateinit var submitButton: Button

    // Map containing fabric-care combinations and their respective care tips
    private val careTipsMap: Map<Pair<String, String>, List<String>> = mapOf(
        Pair("Denim", "wash") to listOf(
            "• Wash infrequently to maintain shape and color — spot clean when possible",
            "• When washing, turn the jacket inside out and use cold water",
            "• Use a mild detergent on a gentle cycle or hand wash"
        ),
        Pair("Denim", "iron") to listOf(
            "• Iron inside out on medium to high heat",
            "• Use steam to help smooth out thick areas or creases",
            "• Avoid ironing over embellishments, buttons, or printed patches"
        ),
        Pair("Leather", "wash") to listOf(
            "• Do not machine wash — wipe gently with a damp cloth for surface cleaning",
            "• Use a leather cleaner or mild soap solution for deeper cleaning",
            "• Always air dry away from direct heat or sunlight"
        ),
        Pair("Leather", "iron") to listOf(
            "• Apply leather conditioner occasionally to keep it soft and prevent cracking",
            "• Store on a wide, padded hanger to maintain shape",
            "• Keep in a cool, dry place — avoid plastic bags that trap moisture"
        ),
        Pair("Wool", "wash") to listOf(
            "• Dry clean only for best results — avoid regular machine washing",
            "• If washing at home, use cold water and a gentle wool-specific detergent",
            "• Spot clean minor stains gently with a damp cloth"
        ),
        Pair("Wool", "iron") to listOf(
            "• Use a steam iron on low heat or a wool setting",
            "• Place a pressing cloth between the iron and fabric to prevent shine",
            "• Always iron inside out and gently press, don’t drag the iron"
        ),
        Pair("Polyester", "wash") to listOf(
            "• Machine wash in cold on a gentle cycle",
            "• Use a mild detergent and avoid bleach or fabric softeners",
            "• Turn inside out before washing to protect any prints or finishes"
        ),
        Pair("Polyester", "iron") to listOf(
            "• Iron on low heat only if needed",
            "• Use a pressing cloth to prevent melting or shine",
            "• Steam gently to remove wrinkles instead of direct ironing"
        ),
        Pair("Cotton Twill", "wash") to listOf(
            "• Machine wash in cold or warm water on a gentle cycle",
            "• Use mild detergent — avoid bleach to preserve color",
            "• Turn inside out before washing to reduce wear and fading"
        ),
        Pair("Cotton Twill", "iron") to listOf(
            "• Iron on medium heat while slightly damp for best results",
            "• Turn inside out to avoid shine on the surface",
            "• Use steam to remove tough wrinkles without damaging the weave"
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Loads the layout file (fragment_jacket_selection.xml)
        val view = inflater.inflate(R.layout.fragment_jacket_selection, container, false)

        // Initiating variables by getting components by ID
        chipGroupFabric = view.findViewById(R.id.chipGroupFabricTypes)
        chipGroupCare = view.findViewById(R.id.chipGroupInstructionRequirement)
        submitButton = view.findViewById(R.id.showButton)

        // Function for back button to return to the previous screen
        val backButton = view.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Setting onClickListener for the Show button
        submitButton.setOnClickListener {
            // Gets the selected fabric chip
            val selectedFabricChipId = chipGroupFabric.checkedChipId

            // Displays message when chip is not selected from fabric
            if (selectedFabricChipId == -1) {
                Toast.makeText(requireContext(), "Please select a fabric type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gets the text from selected fabric chip
            val fabric = view.findViewById<Chip>(selectedFabricChipId).text.toString().trim()

            // Gets selected care instruction chips
            val selectedCareChipIds = chipGroupCare.checkedChipIds

            // Displays message when chip is not selected from care instruction
            if (selectedCareChipIds.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one care instruction", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Converted chip text to the word which is used in mapping (for example, "Washing" → "wash")
            val selectedCareTypes = selectedCareChipIds.map { id ->
                val text = view.findViewById<Chip>(id).text.toString().trim().lowercase()
                when (text) {
                    "washing" -> "wash"
                    "ironing" -> "iron"
                    else -> text
                }
            }

            // Retrieves care tips from the map for washing (if selected)
            val washingTips = if ("wash" in selectedCareTypes) {
                careTipsMap[Pair(fabric, "wash")]?.joinToString("\n") ?: ""
            } else ""

            // Retrieves care tips from the map for ironing (if selected)
            val ironingTips = if ("iron" in selectedCareTypes) {
                careTipsMap[Pair(fabric, "iron")]?.joinToString("\n") ?: ""
            } else ""

            // Updates the title of result fragment (Result Page)
            val heading = "Care Instructions for: Jacket – $fabric"

            // Passing data to result fragment (Result Page)
            val resultFragment = Result.newInstance(
                heading,
                washingTips,
                ironingTips
            )

            // Displays the result fragment with care instruction
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, resultFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}