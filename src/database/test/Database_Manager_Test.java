package database.test;

import database.Database_Manager;
import database.User_Manager;
import database.Reading_Manager;
import model.User;
import model.Reading;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Database_Manager_Test {
    
    public static void main(String[] args) {
        System.out.println("Starting Database Manager Test");
        System.out.println("==============================");
        
        // Get the database manager instance
        Database_Manager dbManager = Database_Manager.getInstance();
        
        // Get the specialized managers
        User_Manager userManager = dbManager.getUserManager();
        Reading_Manager readingManager = dbManager.getReadingManager();
        
        try {
            // Test User Manager operations
            testUserManager(userManager);
            
            // Test Reading Manager operations
            testReadingManager(userManager, readingManager);
            
            System.out.println("\nAll tests completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close database connection
            dbManager.closeConnection();
            System.out.println("\nDatabase connection closed.");
        }
    }
    
    private static void testUserManager(User_Manager userManager) throws SQLException {
        System.out.println("\n--- Testing User Manager ---");
        
        // Test user creation
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "password123";
        String testEmail = "test" + System.currentTimeMillis() + "@example.com";
        
        System.out.println("Creating test user: " + testUsername);
        userManager.addUser(testUsername, testPassword, testEmail);
        
        // Test retrieving user by username
        User retrievedUser = userManager.getUserByUsername(testUsername);
        System.out.println("Retrieved user by username: " + 
                (retrievedUser != null ? retrievedUser.getUsername() : "null"));
        assert retrievedUser != null && retrievedUser.getUsername().equals(testUsername) : 
                "User retrieval by username failed";
        
        // Test retrieving user by email
        User retrievedUserByEmail = userManager.getUserByEmail(testEmail);
        System.out.println("Retrieved user by email: " + 
                (retrievedUserByEmail != null ? retrievedUserByEmail.getEmail() : "null"));
        assert retrievedUserByEmail != null && retrievedUserByEmail.getEmail().equals(testEmail) : 
                "User retrieval by email failed";
        
        // Test username/password match
        boolean credentialsMatch = userManager.UsernamePasswordMatch(testUsername, testPassword);
        System.out.println("Username/password match: " + credentialsMatch);
        assert credentialsMatch : "Username/password match failed";
        
        // Test username/email match
        boolean usernameEmailMatch = userManager.UsernameEmailMatch(testUsername, testEmail);
        System.out.println("Username/email match: " + usernameEmailMatch);
        assert usernameEmailMatch : "Username/email match failed";
        
        // Test updating user password
        String newPassword = "newpassword456";
        System.out.println("Updating user password");
        userManager.updateUserPassword(retrievedUser, newPassword);
        
        // Verify password update
        boolean newCredentialsMatch = userManager.UsernamePasswordMatch(testUsername, newPassword);
        System.out.println("New username/password match: " + newCredentialsMatch);
        assert newCredentialsMatch : "Password update failed";
        
        // Set current user for Reading Manager tests
        userManager.setCurrentUser(retrievedUser);
        System.out.println("Current user set: " + userManager.getCurrentUser().getUsername());
    }
    
    private static void testReadingManager(User_Manager userManager, Reading_Manager readingManager) throws SQLException {
        System.out.println("\n--- Testing Reading Manager ---");
        
        User currentUser = userManager.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("Current user is not set, can't test Reading Manager");
        }
        
        // Test adding readings
        System.out.println("Adding test readings for user: " + currentUser.getUsername());
        
        // Add electricity reading
        LocalDate electricityDate = LocalDate.now().minusDays(30);
        double electricityReading = 500.5;
        double electricityRate = 0.15;
        double electricityTotal = electricityReading * electricityRate;
        
        System.out.println(String.valueOf(electricityTotal));
        readingManager.addReading(currentUser, electricityDate, "electricity", electricityReading, electricityRate, electricityTotal);
        System.out.println("Added electricity reading for date: " + electricityDate);
        
        // Add water reading
        LocalDate waterDate = LocalDate.now().minusDays(15);
        double waterReading = 20.5;
        double waterRate = 2.50;
        double waterTotal = waterReading * waterRate;
        
        System.out.println(String.valueOf(waterTotal));
        readingManager.addReading(currentUser, waterDate, "water", 
                waterReading, waterRate, waterTotal);
        System.out.println("Added water reading for date: " + waterDate);
        
        // Add gas reading
        LocalDate gasDate = LocalDate.now().minusDays(7);
        double gasReading = 100.0;
        double gasRate = 1.20;
        double gasTotal = gasReading * gasRate;
        
        System.out.println(String.valueOf(gasTotal));
        readingManager.addReading(currentUser, gasDate, "gas", 
                gasReading, gasRate, gasTotal);
        System.out.println("Added gas reading for date: " + gasDate);
        
        // Test retrieving all readings for the user
        List<Reading> allReadings = readingManager.getReadingsByUserId(currentUser);
        System.out.println("Retrieved " + allReadings.size() + " readings for user");
        assert allReadings.size() >= 3 : "Failed to retrieve all readings";
        
        // Test retrieving readings within a date range
        LocalDate startDate = LocalDate.now().minusDays(20);
        LocalDate endDate = LocalDate.now();
        List<Reading> rangeReadings = readingManager.getReadingsByUserId(currentUser, startDate, endDate);
        System.out.println("Retrieved " + rangeReadings.size() + " readings in date range");
        
        // Test getting latest reading by type
        Reading latestGasReading = readingManager.getLatestReadingByType(currentUser, "gas");
        System.out.println("Latest gas reading date: " + 
                (latestGasReading != null ? latestGasReading.getDate() : "null"));
        assert latestGasReading != null && 
               latestGasReading.getType().equals("gas") : 
               "Failed to retrieve latest gas reading";
        
        // Test updating a reading
        if (!allReadings.isEmpty()) {
            Reading readingToUpdate = allReadings.get(0);
            double newReading = readingToUpdate.getReading() + 10;
            double newTotal = newReading * readingToUpdate.getRate();
            
            System.out.println("Updating reading ID: " + readingToUpdate.getReading_Id());
            readingManager.updateReading(currentUser, readingToUpdate.getReading_Id(), 
                    readingToUpdate.getDate(), readingToUpdate.getType(), 
                    newReading, readingToUpdate.getRate(), newTotal);
            
            // Verify update
            Reading updatedReading = readingManager.getReadingById(readingToUpdate.getReading_Id());
            System.out.println("Updated reading value: " + updatedReading.getReading());
            assert updatedReading.getReading() == newReading : "Reading update failed";
        }
        
        // Optional: Test deleting a reading
        // Comment this out if you don't want to delete test data
        if (!allReadings.isEmpty()) {
            Reading readingToDelete = allReadings.get(allReadings.size() - 1);
            System.out.println("Deleting reading ID: " + readingToDelete.getReading_Id());
            readingManager.deleteReading(readingToDelete);
            
            // Verify deletion
            Reading deletedReading = readingManager.getReadingById(readingToDelete.getReading_Id());
            System.out.println("Deleted reading is now: " + (deletedReading == null ? "null" : "still exists"));
            assert deletedReading == null : "Reading deletion failed";
        }
        
        // Clean up test data - uncomment if you want to clean up
        
        System.out.println("\n--- Cleaning up test data ---");
        // Delete all readings for test user
        for (Reading reading : allReadings) {
            readingManager.deleteReading(reading);
        }
        
        // Delete test user
        userManager.deleteUser(currentUser);
        User deletedUser = userManager.getUserById(currentUser.getUser_Id());
        System.out.println("Deleted user is now: " + (deletedUser == null ? "null" : "still exists"));

    }
}