package view.panel.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utility_Tips_Manager {
    
    private Map<String, ArrayList<String>> tipsByUtility;
    private Random random;
    private String[] utilityTypes = {"electricity", "water", "gas"};
    private int randomIndex;
    
    public Utility_Tips_Manager() {
        random = new Random();
        tipsByUtility = new HashMap<>();

        ArrayList<String> electricity_tips = new ArrayList<>();
        electricity_tips.add("Unplug electronics and chargers when not in use to prevent 'phantom' energy use.");
        electricity_tips.add("Use smart power strips to reduce standby consumption of electronic devices.");
        electricity_tips.add("Set your refrigerator temperature to 38°F (3°C) and freezer to 5°F (-15°C) for optimal efficiency.");
        electricity_tips.add("Clean or replace air filters regularly to improve HVAC system efficiency.");
        electricity_tips.add("Use natural light when possible and turn off lights in unoccupied rooms.");
        electricity_tips.add("Wash clothes in cold water to save on water heating costs.");
        electricity_tips.add("Air-dry clothes instead of using a dryer when weather permits.");
        electricity_tips.add("Install a programmable thermostat to regulate temperature based on your schedule.");
        electricity_tips.add("Use ceiling fans to circulate air and reduce the need for air conditioning.");
        tipsByUtility.put("electricity", electricity_tips);
        
        ArrayList<String> water_tips = new ArrayList<>();
        water_tips.add("Fix leaky faucets promptly. Even a small drip can waste several gallons of water per day.");
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
    	switch (randomIndex) {
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
    
    public String getRandomTip() {
        randomIndex = random.nextInt(utilityTypes.length);
        String utilityType = utilityTypes[randomIndex];
        ArrayList<String> tips = tipsByUtility.get(utilityType);
        return tips.get(random.nextInt(tips.size()));
    }
    public String getRandomTip(String utilityType) {
		ArrayList<String> tips = tipsByUtility.get(utilityType);
		return tips.get(random.nextInt(tips.size()));
	}
}