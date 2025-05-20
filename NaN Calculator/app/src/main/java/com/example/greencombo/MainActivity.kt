package com.example.greencombo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

    // Declaring variables
    private lateinit var soilChipGroup: ChipGroup
    private lateinit var cropChipGroup: ChipGroup
    private lateinit var resultButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initiating variables by getting components by ID
        soilChipGroup = findViewById(R.id.chipGroupSoilTypes)
        cropChipGroup = findViewById(R.id.chipGroupCropTypes)
        resultButton = findViewById(R.id.resultButton)

        // Map to store (soil + crop) combinations and their corresponding crop data
        val resultMap = mapOf(
            Pair("Alluvial", "Grain") to Triple
                ("Wheat",
                "Wheat grows exceptionally well in nutrient-rich alluvial soil. This soil has high fertility and retains moisture, making it suitable for wheat cultivation, especially during the Rabi season in regions like Punjab and Uttar Pradesh.",
                R.drawable.wheat),

            Pair("Alluvial", "Fruit") to Triple
                ("Banana",
                "Bananas thrive in deep, well-drained alluvial soil. The moisture-holding capacity and fertility of alluvial soil help banana plants develop strong roots and produce high-quality fruit.",
                R.drawable.banana),

            Pair("Alluvial", "Vegetable") to Triple
                ("Potato",
                "Potatoes do well in fine, sandy loam alluvial soil that provides good drainage and supports tuber development. This makes alluvial regions ideal for potato farming during the cooler seasons.",
                R.drawable.potato),

            Pair("Alluvial", "Pulses") to Triple
                ("Lentil",
                "Lentils are a good match for alluvial soils due to their moderate water needs and nitrogen-fixing ability. These soils provide the perfect balance of drainage and fertility needed for pulse crops.",
                R.drawable.lenthil),

            Pair("Alluvial", "Spices") to Triple
                ("Coriander",
                "Coriander grows well in well-drained alluvial soils with good organic content. The mild climate and fertility of these soils make them suitable for spice cultivation.",
                R.drawable.coriander),

            Pair("Alluvial", "Oilseeds") to Triple
                ("Mustard",
                "Mustard thrives in fertile, loamy alluvial soils that retain moisture but drain excess water. These soils support healthy root growth and high oil content in the seeds.",
                R.drawable.mustard),

            Pair("Black", "Grain") to Triple
                ("Sorghum",
                "Sorghum thrives in moisture-retentive black soil, which is rich in iron, magnesium, and aluminum. These properties make it ideal for drought-resistant grain crops like sorghum, especially in semi-arid regions.",
                R.drawable.sorghum),

            Pair("Black", "Fruit") to Triple
                ("Pomegranate",
                "Pomegranates grow well in deep black soil that provides good aeration and retains moisture. This soil type supports root development and helps in the sweetening of fruits.",
                R.drawable.pomegranate),

            Pair("Black", "Vegetable") to Triple
                ("Tomato",
                "Tomatoes flourish in black cotton soil, which has excellent moisture retention and nutrient content. These properties support the continuous growth and fruiting required for tomato crops.",
                R.drawable.tomato),

            Pair("Black", "Pulses") to Triple
                ("Chickpea",
                "Chickpeas perform well in black soils with good drainage and high lime content. The soil's ability to retain nutrients helps chickpea plants grow strong and produce high yields.",
                R.drawable.chickpea),

            Pair("Black", "Spices") to Triple
                ("Fenugreek",
                "Fenugreek adapts well to the slightly alkaline and fertile nature of black soil. Its deep roots benefit from the soil’s moisture retention, making it suitable for spice farming.",
                R.drawable.fenugreek),

            Pair("Black", "Oilseeds") to Triple
                ("Groundnut",
                "Groundnuts grow best in well-drained black soils that are rich in calcium and phosphates. These nutrients are essential for pod development and oil-rich seeds.",
                R.drawable.groundnut),

            Pair("Red", "Grain") to Triple
                ("Millet",
                "Millets grow successfully in red soil due to its good drainage and moderate fertility. These drought-tolerant grains make use of the soil’s iron-rich composition to thrive in dry climates.",
                R.drawable.millet),

            Pair("Red", "Fruit") to Triple
                ("Guava",
                "Guavas thrive in well-drained red soils with a neutral to slightly acidic pH. The soil's aeration and moderate fertility promote steady fruit development and sweet flavor.",
                R.drawable.guava),

            Pair("Red", "Vegetable") to Triple
                ("Carrot",
                "Carrots prefer loose, well-drained red soils that allow root expansion. These soils are ideal for root vegetables, providing the space and nutrients needed for healthy growth.",
                R.drawable.carrot),

            Pair("Red", "Pulses") to Triple
                ("Pigeon Pea",
                "Red soil is suitable for pigeon peas due to its ability to retain moisture while also draining excess water. The soil's iron and potassium support nitrogen-fixing legume crops like pulses.",
                R.drawable.pigeon_pea),

            Pair("Red", "Spices") to Triple
                ("Chilli",
                "Chilli plants thrive in red loamy soil that warms quickly and drains well. The soil enhances the flavor and pungency of spice crops like chilli.",
                R.drawable.chilli),

            Pair("Red", "Oilseeds") to Triple
                ("Sunflower",
                "Sunflowers grow well in red soil with good drainage and moderate organic matter. The soil supports the tall growth and seed development required for oil extraction.",
                R.drawable.sunflower),

            Pair("Laterite", "Grain") to Triple
                ("Maize",
                "Maize grows adequately in laterite soil when properly fertilized. Though low in fertility, its texture and drainage suit maize in regions with heavy rainfall and added nutrients.",
                R.drawable.maize),

            Pair("Laterite", "Fruit") to Triple
                ("Pineapple",
                "Pineapples thrive in acidic laterite soils with high porosity and good drainage. These soils prevent root rot and support the tropical growth requirements of pineapples.",
                R.drawable.pineapple),

            Pair("Laterite", "Vegetable") to Triple
                ("Cabbage",
                "Cabbage performs well in fertile laterite soils with added compost. The soil structure helps the root system anchor and absorb nutrients efficiently for leafy vegetable growth.",
                R.drawable.cabbage),

            Pair("Laterite", "Pulses") to Triple
                ("Green Gram",
                "Green gram can grow in moderately fertile laterite soil with adequate organic content. The soil’s quick drainage prevents waterlogging, which is important for pulse crops.",
                R.drawable.green_gram),

            Pair("Laterite", "Spices") to Triple
                ("Turmeric",
                "Turmeric thrives in well-aerated laterite soils that retain enough moisture but do not become waterlogged. These conditions help the rhizomes develop fully and enhance flavor and color.",
                R.drawable.turmeric),

            Pair("Laterite", "Oilseeds") to Triple
                ("Sesame",
                "Sesame grows well in laterite soils, especially with low water availability. These soils encourage deep rooting and oil-rich seed development in sesame plants.",
                R.drawable.sesame),

            Pair("Sandy", "Grain") to Triple
                ("Barley",
                "Barley adapts to sandy soils due to their excellent drainage and low moisture retention. These conditions make barley a resilient grain choice in dry, arid zones.",
                R.drawable.barley),

            Pair("Sandy", "Fruit") to Triple
                ("Watermelon",
                "Watermelons flourish in sandy soil because of its warmth and fast drainage, which help the large fruit mature quickly and avoid root diseases.",
                R.drawable.watermelon),

            Pair("Sandy", "Vegetable") to Triple
                ("Onion",
                "Onions grow well in loose, sandy soil that allows bulb expansion and prevents water stagnation. This soil helps develop uniform, disease-free onions.",
                R.drawable.onion),

            Pair("Sandy", "Pulses") to Triple
                ("Cowpea",
                "Cowpeas are drought-tolerant and do well in sandy soil, which provides necessary drainage and supports nitrogen-fixing capabilities of legumes.",
                R.drawable.cowpea),

            Pair("Sandy", "Spices") to Triple
                ("Fennel",
                "Fennel thrives in sandy loam soil that offers quick warming and drainage. These properties enhance the essential oil concentration and growth of the spice.",
                R.drawable.fennel),

            Pair("Sandy", "Oilseeds") to Triple
                ("Safflower",
                "Safflower grows well in sandy soils where moisture retention is low but root development is supported. The soil helps produce oil-rich seeds with minimal water.",
                R.drawable.safflower),

        )

        // Creating on click event for result button
        resultButton.setOnClickListener {

            // Gets the IDs of the selected chips from both soil and crop chip groups
            val selectedSoilId = soilChipGroup.checkedChipId
            val selectedCropId = cropChipGroup.checkedChipId

            // Find the Chip components associated with the selected IDs
            val selectedSoilChip = findViewById<Chip>(selectedSoilId)
            val selectedCropChip = findViewById<Chip>(selectedCropId)

            // Extracts the text from the selected chips, or use an empty string if none is selected
            val selectedSoil = selectedSoilChip?.text?.toString() ?: ""
            val selectedCrop = selectedCropChip?.text?.toString() ?: ""

            // Shows a message if either soil or crop is not selected
            if (selectedSoil.isEmpty() || selectedCrop.isEmpty()) {
                Toast.makeText(this, "Please select both soil and crop types", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gets the result for the selected combination
            val result = resultMap[Pair(selectedSoil, selectedCrop)]
            if (result != null) {
                val (cropName, description, imageResId) = result

                // Results shows on popup window
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = inflater.inflate(R.layout.result, null)

                val titleView = popupView.findViewById<TextView>(R.id.resultName)
                val descView = popupView.findViewById<TextView>(R.id.resultDesc)
                val imageView = popupView.findViewById<ImageView>(R.id.resultImage)
                val closeButton = popupView.findViewById<Button>(R.id.closeButton)

                // Updates the text, description and images based on selected comination
                titleView.text = cropName
                descView.text = description
                imageView.setImageResource(imageResId)

                // Sets the size of popup window
                val resultWindow = PopupWindow(popupView, 900, 1800, true)
                resultWindow.showAtLocation(popupView, Gravity.BOTTOM, 20, 200)

                // Closes the popup window once 'close' is clicked
                closeButton.setOnClickListener {
                    resultWindow.dismiss()
                }
            }

            // shows a message if result is not available based on selected combination
            else {
                Toast.makeText(this, "No result found for this combination", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle instruction button click to show help/instructions popup window
        val instructionButton : ImageButton = findViewById(R.id.instructionButton)
        instructionButton.setOnClickListener{
            val inflater= getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView= inflater.inflate(R.layout.instruction, null)

            val width= 900
            val height= 1800

            // Sets the size of instruction popup window
            val instructionWindow = PopupWindow(popupView, width, height, true)
            instructionWindow.showAtLocation(popupView, Gravity.BOTTOM, 20, 200)

            // Closes the popup window once 'close' is clicked
            val closeButton2 : Button = popupView.findViewById(R.id.closeButton2)
            closeButton2.setOnClickListener{
                instructionWindow.dismiss()
            }
        }
    }
}
