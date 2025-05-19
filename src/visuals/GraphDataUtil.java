package visuals;

import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Utility class for working with graph data
 * Provides methods for generating test data and formatting month names
 */
public class GraphDataUtil {
    
    /**
     * Generate random test data for the given number of months
     * 
     * @param monthCount Number of months of data to generate
     * @param maxValue Maximum value for the random data
     * @return Map of months to values
     */
    public static Map<Month, Double> generateTestData(int monthCount, double maxValue) {
        Map<Month, Double> testData = new HashMap<>();
        Random random = new Random();
        
        // Get current month
        Month currentMonth = YearMonth.now().getMonth();
        
        // Generate data for the specified number of months, going backwards from current
        for (int i = 0; i < monthCount; i++) {
            // Calculate month (going backward from current)
            int monthIndex = currentMonth.getValue() - i - 1;
            if (monthIndex <= 0) {
                monthIndex += 12;  // Wrap around to previous year
            }
            Month month = Month.of(monthIndex);
            
            // Generate random value
            double value = random.nextDouble() * maxValue;
            testData.put(month, value);
        }
        
        return testData;
    }
    
    /**
     * Get a shortened display name for a month
     * 
     * @param month The month
     * @return 3-letter month abbreviation
     */
    public static String getShortMonthName(Month month) {
        return month.getDisplayName(TextStyle.SHORT, Locale.getDefault());
    }
    
    /**
     * Generate test datasets for all utility types
     * 
     * @param monthCount Number of months to generate
     * @return Map containing datasets for electricity, water, gas, and overall
     */
    public static Map<String, Map<Month, Double>> generateAllTestData(int monthCount) {
        Map<String, Map<Month, Double>> allData = new HashMap<>();
        
        // Generate data for each utility type
        Map<Month, Double> electricityData = generateTestData(monthCount, 1000);
        Map<Month, Double> waterData = generateTestData(monthCount, 50);
        Map<Month, Double> gasData = generateTestData(monthCount, 200);
        
        // Generate overall data (sum of all other data)
        Map<Month, Double> overallData = new HashMap<>();
        for (Month month : electricityData.keySet()) {
            double totalValue = electricityData.getOrDefault(month, 0.0) +
                               waterData.getOrDefault(month, 0.0) +
                               gasData.getOrDefault(month, 0.0);
            overallData.put(month, totalValue);
        }
        
        // Add all datasets to the map
        allData.put("electricity", electricityData);
        allData.put("water", waterData);
        allData.put("gas", gasData);
        allData.put("overall", overallData);
        
        return allData;
    }
}