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

class TshirtSelection : Fragment() {

    // Declaring variables
    private lateinit var chipGroupFabric: ChipGroup
    private lateinit var chipGroupCare: ChipGroup
    private lateinit var submitButton: Button

    // Map containing fabric-care combinations and their respective care tips
    private val careTipsMap: Map<Pair<String, String>, List<String>> = mapOf(
        Pair("Cotton", "wash") to listOf(
            "• Machine wash in cold or warm water",
            "• Use mild detergent",
            "• Turn inside out to protect color and print"
        ),
        Pair("Cotton", "iron") to listOf(
            "• Iron on medium heat",
            "• Turn inside out to avoid damaging prints or graphics",
            "• Lightly dampen before ironing for best results"
        ),
        Pair("Polyester", "wash") to listOf(
            "• Machine wash in cold water on a gentle cycle",
            "• Use mild detergent — avoid fabric softeners",
            "• Turn inside out to protect any logos or designs"
        ),
        Pair("Polyester", "iron") to listOf(
            "• Iron on low heat setting",
            "• Use a pressing cloth to prevent melting or shine",
            "• Steam lightly to remove wrinkles instead of direct ironing"
        ),
        Pair("Cotton-Polyester Blend", "wash") to listOf(
            "• Machine wash in cold or warm water on a gentle cycle",
            "• Use mild detergent to maintain fabric quality",
            "• Turn inside out to protect prints and reduce pilling"
        ),
        Pair("Cotton-Polyester Blend", "iron") to listOf(
            "• Iron on low to medium heat",
            "• Use a pressing cloth, especially over printed areas",
            "• Lightly steam or iron while slightly damp for best results"
        ),
        Pair("Linen", "wash") to listOf(
            "• Hand wash or machine wash on a gentle cycle",
            "• Use cold or lukewarm water",
            "• Use mild detergent to avoid fiber damage"
        ),
        Pair("Linen", "iron") to listOf(
            "• Iron while the fabric is still slightly damp",
            "• Use medium to high heat with steam",
            "• Turn inside out to prevent shine and preserve the natural texture"
        ),
        Pair("Rayon", "wash") to listOf(
            "• Hand wash in cold water to prevent shrinking and stretching",
            "• Use a mild detergent",
            "• Avoid wringing — gently press out water and lay flat to dry"
        ),
        Pair("Rayon", "iron") to listOf(
            "• Iron inside out on low heat",
            "• Use a pressing cloth to avoid shine or scorching",
            "• Iron while the fabric is slightly damp for better results"
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Loads the layout file (fragment_tshirt_selection.xml)
        val view = inflater.inflate(R.layout.fragment_tshirt_selection, container, false)

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
            val heading = "Care Instructions for: T-Shirt – $fabric"

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
