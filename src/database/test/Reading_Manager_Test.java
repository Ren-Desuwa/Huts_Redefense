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

public class Reading_Manager_Test {
    

    private static Reading_Manager reading_manager;
    private static Connection database_connection;
    private static User test_user;
    private static int test_count = 0;
    private static int passed_count = 0;
    
    // Main test method to be called from Database_Manager_Test
    public static void testReadingManager(Connection connection, Reading_Manager manager) {
        database_connection = connection;
        reading_manager = manager;
        
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
            Viewer.print("Adding reading for date: " + test_date);
            reading_manager.addReading(test_user, test_date, "electricity", 150.5, 12.5, 1881.25);

            Reading latest = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            Viewer.print(latest);
            assertNotNull(latest, "Reading should be added successfully");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGetLatestReadingByType() {
        System.out.println("\n=== Testing getLatest_Reading_By_Type() ===");
        try {
            Viewer.print("Adding readings for type: water");
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 10), "water", 25.0, 8.0, 200.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 2, 10), "water", 30.0, 8.0, 240.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 3, 10), "water", 35.0, 8.0, 280.0);

            Reading latest = reading_manager.getLatest_Reading_By_Type(test_user, "water");
            Viewer.print(latest);
            assertNotNull(latest, "Latest reading should be found");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGetAllReadingsByType() {
        System.out.println("\n=== Testing getAll_Readings_By_Type() ===");
        try {
            Viewer.print("Adding gas readings");
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 5), "gas", 100, 15.0, 1500.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 2, 5), "gas", 120, 15.0, 1800.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 3, 5), "gas", 110, 15.0, 1650.0);

            List<Reading> gas_readings = reading_manager.getAll_Readings_By_Type(test_user, "gas");
            Viewer.print(gas_readings);
            assertEqual(3, gas_readings.size(), "Should return all gas readings");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testDeleteReading() {
        System.out.println("\n=== Testing deleteReading() ===");
        try {
            Viewer.print("Adding a reading to delete");
            reading_manager.addReading(test_user, LocalDate.of(2024, 4, 1), "electricity", 200.0, 13.0, 2600.0);
            Reading to_delete = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            Viewer.print(to_delete);

            reading_manager.deleteReading(to_delete);
            Reading after_delete = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            Viewer.print(after_delete);
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGroupReadingsByMonth() {
        System.out.println("\n=== Testing groupReadings_By_Month() ===");
        try {
            Viewer.print("Adding readings for grouping by month");
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 15), "electricity", 100.0, 10.0, 1000.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 1, 25), "electricity", 50.0, 10.0, 500.0);
            reading_manager.addReading(test_user, LocalDate.of(2024, 2, 10), "electricity", 75.0, 10.0, 750.0);

            List<Reading> readings = reading_manager.getAll_Readings_By_Type(test_user, "electricity");
            Viewer.print(readings);
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGetMonthlyUtilityData() {
        System.out.println("\n=== Testing getMonthly_Utility_Data() ===");
        try {
            Map<Month, Double> monthly_data = reading_manager.getMonthly_Utility_Data(test_user, "electricity", 2024, "reading");
            Viewer.print(monthly_data);
            assertNotNull(monthly_data, "Monthly data should not be null");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGetTotalLatestCost() {
        System.out.println("\n=== Testing getTotal_Latest_Cost() ===");
        try {
            double total_cost = reading_manager.getTotal_Latest_Cost(test_user);
            Viewer.print("Total latest cost: " + total_cost);
            assertTrue(total_cost >= 0, "Total cost should be non-negative");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGetTrend() {
        System.out.println("\n=== Testing getTrend() ===");
        try {
            String trend = reading_manager.getTrend(test_user, "electricity", "total");
            Viewer.print("Trend: " + trend);
            assertNotNull(trend, "Trend should not be null");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGetTrendColor() {
        System.out.println("\n=== Testing getTrend_Color() ===");
        Color trend_color = reading_manager.getTrend_Color();
        Viewer.print("Trend color: " + trend_color);
        assertNotNull(trend_color, "Trend color should not be null");

    }

    private static void testGetTotalReadings() {
        System.out.println("\n=== Testing getTotal_Readings() ===");
        int total_readings = reading_manager.getTotal_Readings(test_user);
        Viewer.print("Total readings: " + total_readings);
        assertTrue(total_readings >= 0, "Total readings should be non-negative");
    }

    private static void testIsReadingExists() {
        System.out.println("\n=== Testing isReading_Exists() ===");
        try {
            boolean exists = reading_manager.isReading_Exists(test_user, "electricity");
            Viewer.print("Electricity readings exist: " + exists);
            assertTrue(exists, "Electricity readings should exist");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testGetReadingYears() {
        System.out.println("\n=== Testing getReading_Years() ===");
        try {
            int[] years = reading_manager.getReading_Years(test_user, "electricity");
            Viewer.print("Reading years: ");
            for (int year : years) {
                Viewer.print(year);
            }
            assertNotNull(years, "Years array should not be null");
        } catch (SQLException e) {
            Viewer.print("SQL Exception: " + e.getMessage());
        }
    }

    private static void testUpdateReadingLabel() {
        System.out.println("\n=== Testing updateReading_Label() ===");
        try {
            Reading test_reading = reading_manager.getLatest_Reading_By_Type(test_user, "electricity");
            JLabel value_label = new JLabel();
            JLabel trend_label = new JLabel();
            JLabel unit_label = new JLabel();

            reading_manager.updateReading_Label(test_user, test_reading, value_label, trend_label, unit_label, "electricity", "reading");
            Viewer.print("Value label: " + value_label.getText());
            Viewer.print("Trend label: " + trend_label.getText());
            Viewer.print("Unit label: " + unit_label.getText());
        } catch (Exception e) {
            Viewer.print("Exception: " + e.getMessage());
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