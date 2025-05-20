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

class PantSelection : Fragment() {

    // Declaring variables
    private lateinit var chipGroupFabric: ChipGroup
    private lateinit var chipGroupCare: ChipGroup
    private lateinit var submitButton: Button

    // Map containing fabric-care combinations and their respective care tips
    private val careTipsMap: Map<Pair<String, String>, List<String>> = mapOf(
        Pair("Cotton", "wash") to listOf(
            "• Machine wash in cold or warm water",
            "• Use mild detergent",
            "• Wash with similar colors to avoid dye transfer"
        ),
        Pair("Cotton", "iron") to listOf(
            "• Iron on medium heat",
            "• Turn pants inside out to prevent shine",
            "• Lightly dampen fabric before ironing for best results"
        ),
        Pair("Denim (Jeans)", "wash") to listOf(
            "• Wash inside out to preserve color and reduce fading",
            "• Use cold water on a gentle cycle",
            "• Wash less frequently to maintain shape and texture"
        ),
        Pair("Denim (Jeans)", "iron") to listOf(
            "• Iron inside out on medium to high heat",
            "• Use steam to smooth out heavy wrinkles",
            "• Avoid ironing over embellishments or prints"
        ),
        Pair("Linen", "wash") to listOf(
            "• Hand wash or machine wash on a gentle cycle",
            "• Use cold or lukewarm water",
            "• Use mild detergent to protect the fibers"
        ),
        Pair("Linen", "iron") to listOf(
            "• Iron while the fabric is still slightly damp",
            "• Use medium to high heat",
            "• Turn inside out to avoid shine and preserve texture"
        ),
        Pair("Polyester", "wash") to listOf(
            "• Machine wash in cold water on a gentle cycle",
            "• Use mild detergent",
            "• Avoid using high heat in the dryer"
        ),
        Pair("Polyester", "iron") to listOf(
            "• Iron on low heat setting",
            "• Use a pressing cloth to prevent melting or shine",
            "• Steam gently to remove wrinkles instead of heavy ironing"
        ),
        Pair("Wool", "wash") to listOf(
            "• Hand wash in cold water or use the wool/delicate cycle on your machine",
            "• Use a wool-safe or mild detergent",
            "• Do not wring — press gently to remove excess water and lay flat to dry"
        ),
        Pair("Wool", "iron") to listOf(
            "• Iron on low heat using a pressing cloth",
            "• Use steam instead of direct heat to avoid scorching",
            "• Always iron inside out to protect the fabric surface"
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Loads the layout file (fragment_pant_selection.xml)
        val view = inflater.inflate(R.layout.fragment_pant_selection, container, false)

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

            // Displays message when chip is not selected from fabric
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
            val heading = "Care Instructions for: Pant – $fabric"

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
