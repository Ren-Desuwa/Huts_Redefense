package database;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utility_Tips_Manager {
    
    private Map<String, ArrayList<String>> tipsByUtility;
    private Random random;
    private String[] utilityTypes = {"electricity", "water", "gas"};
    private int randomTipType;
    
    protected Utility_Tips_Manager() {
    	Setup();
	}
    
    private void Setup() {
        random = new Random();
        tipsByUtility = new HashMap<>();

        ArrayList<String> electricity_tips = new ArrayList<>();
        electricity_tips.add("Unplug electronics and chargers when not in use to prevent 'phantom' energy use.");
        electricity_tips.add("Use smart power strips to reduce standby consumption of electronic devices.");
        electricity_tips.add("Clean or replace air filters regularly to improve HVAC system efficiency.");
        electricity_tips.add("Use natural light when possible and turn off lights in unoccupied rooms.");
        electricity_tips.add("Wash clothes in cold water to save on water heating costs.");
        electricity_tips.add("Air-dry clothes instead of using a dryer when weather permits.");
        electricity_tips.add("Use ceiling fans to circulate air and reduce the need for air conditioning.");
        tipsByUtility.put("electricity", electricity_tips);
        
        ArrayList<String> water_tips = new ArrayList<>();
        water_tips.add("Install low-flow showerheads and faucet aerators to reduce water usage.");
        water_tips.add("Take shorter showers to conserve hot water and energy.");
        water_tips.add("Turn off the water while brushing teeth or shaving.");
        water_tips.add("Only run dishwashers and washing machines with full loads.");
        water_tips.add("Use a broom instead of a hose to clean driveways and sidewalks.");
        water_tips.add("Collect rainwater for garden irrigation.");
        water_tips.add("Install dual-flush or low-flow toilets to reduce water usage per flush.");
        water_tips.add("Check for hidden water leaks by monitoring your water meter when no water is in use.");
        water_tips.add("Water plants and gardens in the early morning or evening to reduce evaporation.");
        tipsByUtility.put("water", water_tips);

        ArrayList<String> gas_tips = new ArrayList<>();
        gas_tips.add("Seal gaps around doors and windows to prevent heat loss.");
        gas_tips.add("Add insulation to your attic, walls, and floors to improve energy efficiency.");
        gas_tips.add("Service your heating system annually to ensure it's running efficiently.");
        gas_tips.add("Use a programmable thermostat to lower temperatures when you're away or sleeping.");
        gas_tips.add("Close the fireplace damper when not in use to prevent warm air from escaping.");
        gas_tips.add("Use oven and stovetop efficiently by matching pot size to burner size.");
        gas_tips.add("Consider replacing old heating systems with high-efficiency models.");
        gas_tips.add("Install thermal curtains to reduce heat loss through windows.");
        gas_tips.add("Use draft stoppers under doors to prevent cold air infiltration.");
        tipsByUtility.put("gas", gas_tips);
    }
    
    public String getType() {
    	switch (randomTipType) {
    		case 0:
			return "Electricity Tip";
			case 1:
			return "Water Tip";
			case 2:
			return "Gas Tip";
			default:
			return "Unknown Tip"; // this should never happen
    	}
    }
    
    public Color setcolor() {
		switch (randomTipType) {
			case 0:
			return new Color(255,167,0); // Yellow for Electricity
			case 1:
			return new Color(79, 129, 189); // Blue for Water
			case 2:
			return new Color(255,77,0); // Red for Gas
			default:
			return new Color(0, 0, 0); // Black for unknown
		}
    }
    
    public String getRandomTip() {
    	// number of utility types
    	int numberOfUtilities = utilityTypes.length;
    	
    	// Select a random utility type electricity, water, or gas
        randomTipType = random.nextInt(numberOfUtilities);
        
        // Get the corresponding utility type
        String utilityType = utilityTypes[randomTipType];
        
        // Retrieve a random tip from the selected utility type
        ArrayList<String> tips = tipsByUtility.get(utilityType);
        
        // gets the number of tips available for the selected utility type
        int numberOfTips = tips.size();
        
        // return a random tip from the list
        return tips.get(random.nextInt(numberOfTips));
    }
    public String getRandomTip(String utilityType) {
    	// get the utility type from the parameter
		ArrayList<String> tips = tipsByUtility.get(utilityType);
		
		// gets the number of tips available for the selected utility type
		int numberOfTips = tips.size();
		
		// return a random tip from the list
		return tips.get(random.nextInt(numberOfTips));
	}
}

/*
 * File: Utility_Tips_Manager.java
 *
 * Description:
 * This file defines the `Utility_Tips_Manager` class, which is responsible for managing and providing utility-saving tips for electricity, water, and gas. 
 * It stores a collection of tips for each utility type and provides methods to retrieve random tips. 
 * The class also includes functionality to assign colors and labels based on the type of utility tip.
 *
 * Variables:
 * - `instance` (Utility_Tips_Manager): A singleton instance of the `Utility_Tips_Manager` class to ensure only one instance exists.
 * - `tipsByUtility` (Map<String, ArrayList<String>>): A map that associates each utility type (electricity, water, gas) with a list of tips.
 * - `random` (Random): A random number generator used to select random tips and utility types.
 * - `utilityTypes` (String[]): An array of utility types ("electricity", "water", "gas").
 * - `randomIndex` (int): Stores the index of the randomly selected utility type.
 *
 * Constructors:
 * 1. `Utility_Tips_Manager()`:
 *    - Private constructor to initialize the singleton instance.
 *    - Populates the `tipsByUtility` map with predefined tips for electricity, water, and gas.
 *
 * Methods:
 * 1. `getInstance()`:
 *    - Returns the singleton instance of the `Utility_Tips_Manager` class.
 *    - Creates the instance if it does not already exist.
 *
 * 2. `getType()`:
 *    - Returns the label for the currently selected utility type based on the `randomIndex`.
 *    - Possible return values: "Electricity Tip", "Water Tip", "Gas Tip", or "Unknown Tip".
 *
 * 3. `setcolor()`:
 *    - Returns a `Color` object corresponding to the currently selected utility type based on the `randomIndex`.
 *    - Possible colors:
 *      - Yellow for electricity (`new Color(255, 167, 0)`).
 *      - Blue for water (`new Color(79, 129, 189)`).
 *      - Red for gas (`new Color(255, 77, 0)`).
 *      - Black for unknown types (`new Color(0, 0, 0)`).
 *
 * 4. `getRandomTip()`:
 *    - Selects a random utility type and retrieves a random tip from the corresponding list in `tipsByUtility`.
 *    - Updates the `randomIndex` to reflect the selected utility type.
 *
 * 5. `getRandomTip(String utilityType)`:
 *    - Retrieves a random tip for the specified utility type from the `tipsByUtility` map.
 *    - Assumes the provided utility type is valid and exists in the map.
 *
 * Usage:
 * The `Utility_Tips_Manager` class is used to provide utility-saving tips to users in the application. 
 * It ensures that tips are categorized by utility type and can be retrieved randomly or based on a specific type. 
 * The class also provides visual cues (colors and labels) to enhance the user interface when displaying tips.
 */