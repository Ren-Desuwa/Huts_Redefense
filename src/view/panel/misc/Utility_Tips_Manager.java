package view.panel.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Utility class to manage and retrieve random money-saving tips for different utilities
 */
public class Utility_Tips_Manager {
    
    private Map<String, ArrayList<String>> tipsByUtility;
    private Random random;
    private String[] utilityTypes = {"electricity", "water", "gas"};
    private int randomIndex;
    
    /**
     * Constructor that initializes all available tips
     */
    public Utility_Tips_Manager() {
        random = new Random();
        tipsByUtility = new HashMap<>();
        
        // Initialize tips for each utility type
        initializeElectricityTips();
        initializeWaterTips();
        initializeGasTips();
    }
    
    /**
     * Initialize electricity saving tips
     */
    private void initializeElectricityTips() {
        ArrayList<String> tips = new ArrayList<>();
        
        tips.add("Unplug electronics and chargers when not in use to prevent 'phantom' energy use.");
        tips.add("Use smart power strips to reduce standby consumption of electronic devices.");
        tips.add("Set your refrigerator temperature to 38°F (3°C) and freezer to 5°F (-15°C) for optimal efficiency.");
        tips.add("Clean or replace air filters regularly to improve HVAC system efficiency.");
        tips.add("Use natural light when possible and turn off lights in unoccupied rooms.");
        tips.add("Wash clothes in cold water to save on water heating costs.");
        tips.add("Air-dry clothes instead of using a dryer when weather permits.");
        tips.add("Install a programmable thermostat to regulate temperature based on your schedule.");
        tips.add("Use ceiling fans to circulate air and reduce the need for air conditioning.");
        
        tipsByUtility.put("electricity", tips);
    }
    
    /**
     * Initialize water saving tips
     */
    private void initializeWaterTips() {
        ArrayList<String> tips = new ArrayList<>();
        
        tips.add("Fix leaky faucets promptly. Even a small drip can waste several gallons of water per day.");
        tips.add("Install low-flow showerheads and faucet aerators to reduce water usage.");
        tips.add("Take shorter showers to conserve hot water and energy.");
        tips.add("Turn off the water while brushing teeth or shaving.");
        tips.add("Only run dishwashers and washing machines with full loads.");
        tips.add("Use a broom instead of a hose to clean driveways and sidewalks.");
        tips.add("Collect rainwater for garden irrigation.");
        tips.add("Install dual-flush or low-flow toilets to reduce water usage per flush.");
        tips.add("Check for hidden water leaks by monitoring your water meter when no water is in use.");
        tips.add("Water plants and gardens in the early morning or evening to reduce evaporation.");
        
        tipsByUtility.put("water",tips);
    }
    
    /**
     * Initialize gas saving tips
     */
    private void initializeGasTips() {
        ArrayList<String> tips = new ArrayList<>();
        
        tips.add("Seal gaps around doors and windows to prevent heat loss.");
        tips.add("Add insulation to your attic, walls, and floors to improve energy efficiency.");
        tips.add("Service your heating system annually to ensure it's running efficiently.");
        tips.add("Use a programmable thermostat to lower temperatures when you're away or sleeping.");
        tips.add("Close the fireplace damper when not in use to prevent warm air from escaping.");
        tips.add("Use oven and stovetop efficiently by matching pot size to burner size.");
        tips.add("Consider replacing old heating systems with high-efficiency models.");
        tips.add("Install thermal curtains to reduce heat loss through windows.");
        tips.add("Use draft stoppers under doors to prevent cold air infiltration.");
        
        tipsByUtility.put("gas", tips);
    }
    
    /**
     * Get a random tip for a randomly selected utility type
     * 
     * @return A formatted tip with utility type and tip text
     */
    
    public String getType() {
    	switch (randomIndex) {
    		case 0:
			return "Electricity Tip";
			case 1:
			return "Water Tip";
			case 2:
			return "Gas Tip";
			default:
				return "Unknown Tip";
    	}
    }
    
    public String getRandomTip() {
        // Select a random utility type
        randomIndex = random.nextInt(utilityTypes.length);
        String utilityType = utilityTypes[randomIndex];
        
        // Get tips for the selected utility
        ArrayList<String> tips = tipsByUtility.get(utilityType);
        
        // Pick and return a random tip without formatting
        return tips.get(random.nextInt(tips.size()));
    }
}