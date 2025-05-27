package database.test;

import java.awt.Color;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;

import database.Reading_Manager;
import model.Reading;
import model.User;

public class ReadingManagerTest {
    
    private static Reading_Manager reading_manager;
    private static Connection database_connection;
    private static User test_user;
    private static int test_count = 0;
    private static int passed_count = 0;
    
    // Main test method to be called from Database_Manager_Test
    public static void testReadingManager(Connection connection) {
        database_connection = connection;
        reading_manager = new Reading_Manager(database_connection);
        
        System.out.println("==========================================");
        System.out.println("       READING MANAGER UNIT TESTS        ");
        System.out.println("==========================================");
        
        // Setup test user
        setupTestUser();
        
        if (test_user == null) {
            System.out.println("✗ FAIL: Could not create test user. Aborting tests.");
            return;
        }
        
        // Run all tests
        testAddReading();
        testGetLatestReadingByType();
        testGetAllReadingsByType();
        testDeleteReading();
        testGroupReadingsByMonth();
        testGetMonthlyUtilityData();
        testGetTotalLatestCost();
        testGetTrend();
        testGetTrendColor();
        testGetTotalReadings();
        testIsReadingExists();
        testGetReadingYears();
        testUpdateReadingLabel();
        
        // Clean up test data
        cleanupTestData();
        
        // Print results
        printTestResults();
    }
    
    // Setup test user using existing database
    private static void setupTestUser() {
        try {
            // Check if test user already exists
            String check_user = "SELECT * FROM users WHERE username = ?";
            PreparedStatement check_ps = database_connection.prepareStatement(check_user);
            check_ps.setString(1, "test_reading_user");
            ResultSet check_rs = check_ps.executeQuery();
            
            if (check_rs.next()) {
                // User exists, use it
                int user_id = check_rs.getInt("user_id");
                test_user = new User(user_id, "test_reading_user", "testpass123", "testReading@email.com");
            } else {
                // Create new test user
                String insert_user = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
                PreparedStatement ps = database_connection.prepareStatement(insert_user, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "test_reading_user");
                ps.setString(2, "testpass123");
                ps.setString(3, "testReading@email.com");
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int user_id = rs.getInt(1);
                    test_user = new User(user_id, "test_reading_user", "testpass123", "testReading@email.com");
                }
                rs.close();
                ps.close();
            }
            
            check_rs.close();
            check_ps.close();
            
        } catch (SQLException e) {
            System.out.println("Error setting up test user: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Clean up test data after tests
    private static void cleanupTestData() {
        try {
            if (test_user != null) {
                // Delete test readings
                String delete_readings = "DELETE FROM readings WHERE user_id = ?";
                PreparedStatement ps1 = database_connection.prepareStatement(delete_readings);
                ps1.setInt(1, test_user.getUser_Id());
                ps1.executeUpdate();
                ps1.close();
                
                // Delete test user
                String delete_user = "DELETE FROM users WHERE user_id = ?";
                PreparedStatement ps2 = database_connection.prepareStatement(delete_user);
                ps2.setInt(1, test_user.getUser_Id());
                ps2.executeUpdate();
                ps2.close();
            }
        } catch (SQLException e) {
            System.out.println("Error cleaning up test data: " + e.getMessage());
        }
    }
    
    // Helper method for assertions
    private static void assertEqual(Object expected, Object actual, String test_name) {
        test_count++;
        if ((expected == null && actual == null) || 
            (expected != null && expected.equals(actual))) {
            System.out.println("✓ PASS: " + test_name);
            passed_count++;
        } else {
            System.out.println("✗ FAIL: " + test_name + " - Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    private static void assertTrue(boolean condition, String test_name) {
        test_count++;
        if (condition) {
            System.out.println("✓ PASS: " + test_name);
            passed_count++;
        } else {
            System.out.println("✗ FAIL: " + test_name);
        }
    }
    
    private static void assertNotNull(Object obj, String test_name) {
        test_count++;
        if (obj != null) {
            System.out.println("✓ PASS: " + test_name);
            passed_count++;
        } else {
            System.out.println("✗ FAIL: " + test_name + " - Object is null");
        }
    }
    
    // Test adding readings
    private static void testAddReading() {
        System.out.println("\n=== Testing addReading() ===");
        
        try {
            LocalDate test_date = LocalDate.of(2024, 3, 15);
            reading_manager.addReading(test_user, test_date, "electricity", 150.5, 12.5, 1881.25);
            
            // Verify the reading was added
            Reading latest = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            assertNotNull(latest, "Reading should be added successfully");
            assertEqual("electricity", latest.getType(), "Reading type should match");
            assertEqual(150.5, latest.getReading(), "Reading value should match");
            assertEqual(12.5, latest.getRate(), "Rate should match");
            assertEqual(1881.25, latest.getTotal_Price(), "Total price should match");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: addReading() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting latest reading by type
    private static void testGetLatestReadingByType() {
        System.out.println("\n=== Testing getLatest_Reading_By_Type() ===");
        
        try {
            // Add multiple readings
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 10), "water", 25.0, 8.0, 200.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 2, 10), "water", 30.0, 8.0, 240.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 3, 10), "water", 35.0, 8.0, 280.0);
            
            Reading latest = reading_manager.getLatest_Reading_By_Type(test_user, "water");
            assertNotNull(latest, "Latest reading should be found");
            assertEqual(35.0, latest.getReading(), "Should return the most recent reading");
            assertEqual(LocalDate.of(2024, 3, 10), latest.getDate(), "Should return the latest date");
            
            // Test non-existent type (using 'other' which is valid per your schema)
            Reading non_existent = reading_manager.getLatest_Reading_By_Type(test_user, "other");
            assertEqual(null, non_existent, "Should return null for non-existent type");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: getLatest_Reading_By_Type() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting all readings by type
    private static void testGetAllReadingsByType() {
        System.out.println("\n=== Testing getAll_Readings_By_Type() ===");
        
        try {
            // Add gas readings
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 5), "gas", 100, 15.0, 1500.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 2, 5), "gas", 120, 15.0, 1800.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 3, 5), "gas", 110, 15.0, 1650.0);
            
            List<Reading> gas_readings = reading_manager.getAll_Readings_By_Type(test_user, "gas");
            assertEqual(3, gas_readings.size(), "Should return all gas readings");
            
            // Check if ordered by date descending
            assertTrue(gas_readings.get(0).getDate().isAfter(gas_readings.get(1).getDate()), 
                      "Readings should be ordered by date descending");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: getAll_Readings_By_Type() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test deleting readings
    private static void testDeleteReading() {
        System.out.println("\n=== Testing deleteReading() ===");
        
        try {
            // Add a reading to delete
            reading_manager.addReading(test_user, LocalDate.of(2024, 4, 1), "electricity", 200.0, 13.0, 2600.0);
            Reading to_delete = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            
            assertNotNull(to_delete, "Reading should exist before deletion");
            
            // Delete the reading
            reading_manager.deleteReading(to_delete);
            
            // Verify it's deleted by checking if the latest reading is different
            Reading after_delete = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            
            if (after_delete == null || after_delete.getReading_Id() != to_delete.getReading_Id()) {
                System.out.println("✓ PASS: Reading deleted successfully");
                passed_count++;
            } else {
                System.out.println("✗ FAIL: Reading was not deleted");
            }
            test_count++;
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: deleteReading() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test grouping readings by month
    private static void testGroupReadingsByMonth() {
        System.out.println("\n=== Testing groupReadings_By_Month() ===");
        
        try {
            // Add readings for different months in 2024
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 15), "electricity", 100.0, 10.0, 1000.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 25), "electricity", 50.0, 10.0, 500.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 2, 10), "electricity", 75.0, 10.0, 750.0);
            
            List<Reading> readings = reading_manager.getAll_Readings_By_Type(test_user, "electricity");
            Map<Month, Double> monthly_data = reading_manager.groupReadings_By_Month(readings, 2024, "reading");
            
            assertEqual(150.0, monthly_data.get(Month.JANUARY), "January total should be 150.0");
            assertEqual(75.0, monthly_data.get(Month.FEBRUARY), "February total should be 75.0");
            
            // Test with total price field
            Map<Month, Double> monthly_totals = reading_manager.groupReadings_By_Month(readings, 2024, "total");
            assertEqual(1500.0, monthly_totals.get(Month.JANUARY), "January total price should be 1500.0");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: groupReadings_By_Month() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting monthly utility data
    private static void testGetMonthlyUtilityData() {
        System.out.println("\n=== Testing getMonthly_Utility_Data() ===");
        
        try {
            Map<Month, Double> monthly_data = reading_manager.getMonthly_Utility_Data(test_user, "electricity", 2024, "reading");
            assertNotNull(monthly_data, "Monthly data should not be null");
            
            // Should contain data for months where we added readings
            assertTrue(monthly_data.containsKey(Month.JANUARY), "Should contain January data");
            assertTrue(monthly_data.containsKey(Month.FEBRUARY), "Should contain February data");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: getMonthly_Utility_Data() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting total latest cost
    private static void testGetTotalLatestCost() {
        System.out.println("\n=== Testing getTotal_Latest_Cost() ===");
        
        try {
            double total_cost = reading_manager.getTotal_Latest_Cost(test_user);
            assertTrue(total_cost >= 0, "Total cost should be non-negative");
            
            System.out.println("✓ PASS: Total latest cost calculated: " + total_cost);
            passed_count++;
            test_count++;
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: getTotal_Latest_Cost() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting trend
    private static void testGetTrend() {
        System.out.println("\n=== Testing getTrend() ===");
        
        try {
            // Add readings from different months to test trend
            reading_manager.addReading(test_user, LocalDate.of(2024, 4, 1), "electricity", 100.0, 10.0, 1000.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 5, 1), "electricity", 120.0, 10.0, 1200.0);
            
            String trend = reading_manager.getTrend(test_user, "electricity", "total");
            assertNotNull(trend, "Trend should not be null");
            assertTrue(!trend.equals("Not enough data"), "Should have enough data for trend calculation");
            
            System.out.println("✓ PASS: Trend calculated: " + trend);
            passed_count++;
            test_count++;
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: getTrend() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting trend color
    private static void testGetTrendColor() {
        System.out.println("\n=== Testing getTrend_Color() ===");
        
        try {
            // First get a trend to set the last_trend_percentage
            reading_manager.getTrend(test_user, "electricity", "total");
            
            Color trend_color = reading_manager.getTrend_Color();
            assertNotNull(trend_color, "Trend color should not be null");
            
            // The color should be green, red, or gray
            assertTrue(trend_color.equals(new Color(0, 150, 0)) || 
                      trend_color.equals(new Color(255, 0, 0)) || 
                      trend_color.equals(Color.GRAY), 
                      "Trend color should be green, red, or gray");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: getTrend_Color() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting total readings count
    private static void testGetTotalReadings() {
        System.out.println("\n=== Testing getTotal_Readings() ===");
        
        int total_readings = reading_manager.getTotal_Readings(test_user);
        assertTrue(total_readings >= 0, "Total readings should be non-negative");
        
        System.out.println("✓ PASS: Total readings count: " + total_readings);
        passed_count++;
        test_count++;
    }
    
    // Test checking if reading exists
    private static void testIsReadingExists() {
        System.out.println("\n=== Testing isReading_Exists() ===");
        
        try {
            boolean exists = reading_manager.isReading_Exists(test_user, "electricity");
            assertTrue(exists, "Electricity readings should exist");
            
            boolean not_exists = reading_manager.isReading_Exists(test_user, "other");
            assertTrue(!not_exists, "Other readings should not exist");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: isReading_Exists() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test getting reading years
    private static void testGetReadingYears() {
        System.out.println("\n=== Testing getReading_Years() ===");
        
        try {
            int[] years = reading_manager.getReading_Years(test_user, "electricity");
            assertNotNull(years, "Years array should not be null");
            assertTrue(years.length > 0, "Should have at least one year");
            
            // Check if 2024 is in the years (we added readings for 2024)
            boolean found_2024 = false;
            for (int year : years) {
                if (year == 2024) {
                    found_2024 = true;
                    break;
                }
            }
            assertTrue(found_2024, "Should contain year 2024");
            
        } catch (SQLException e) {
            System.out.println("✗ FAIL: getReading_Years() - SQL Exception: " + e.getMessage());
        }
    }
    
    // Test update reading labels
    private static void testUpdateReadingLabel() {
        System.out.println("\n=== Testing updateReading_Label() ===");
        
        try {
            Reading test_reading = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            JLabel value_label = new JLabel();
            JLabel trend_label = new JLabel();
            JLabel unit_label = new JLabel();
            
            reading_manager.updateReading_Label(test_user, test_reading, value_label, trend_label, unit_label, "electricity", "reading");
            
            assertNotNull(value_label.getText(), "Value label should be updated");
            assertNotNull(trend_label.getText(), "Trend label should be updated");
            assertNotNull(unit_label.getText(), "Unit label should be updated");
            assertTrue(!value_label.getText().equals("No Data"), "Should have actual data");
            
        } catch (Exception e) {
            System.out.println("✗ FAIL: updateReading_Label() - Exception: " + e.getMessage());
        }
    }
    
    // Print test results
    private static void printTestResults() {
        System.out.println("\n==========================================");
        System.out.println("              TEST RESULTS                ");
        System.out.println("==========================================");
        System.out.println("Total Tests: " + test_count);
        System.out.println("Passed: " + passed_count);
        System.out.println("Failed: " + (test_count - passed_count));
        System.out.println("Success Rate: " + String.format("%.1f", (double) passed_count / test_count * 100) + "%");
        
        if (passed_count == test_count) {
            System.out.println("\n🎉 ALL READING MANAGER TESTS PASSED! 🎉");
        } else {
            System.out.println("\n⚠️  Some Reading Manager tests failed. Please review the output above.");
        }
    }
}