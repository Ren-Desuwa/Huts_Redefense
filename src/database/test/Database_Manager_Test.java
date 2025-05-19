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
        
        int passedTests = 0;
        int totalTests = 0;
        
        try {
            // Test User Manager operations
            int[] userTestResults = testUserManager(userManager);
            passedTests += userTestResults[0];
            totalTests += userTestResults[1];
            
            // Test Reading Manager operations
            int[] readingTestResults = testReadingManager(userManager, readingManager);
            passedTests += readingTestResults[0];
            totalTests += readingTestResults[1];
            
            System.out.println("\nTest Summary:");
            System.out.println("Passed tests: " + passedTests + "/" + totalTests + " (" + 
                    (totalTests > 0 ? (passedTests * 100 / totalTests) : 0) + "%)");
            
            if (passedTests == totalTests) {
                System.out.println("All tests passed successfully!");
            } else {
                System.out.println("Some tests failed. See log for details.");
            }
            
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close database connection
            dbManager.closeConnection();
            System.out.println("\nDatabase connection closed.");
        }
    }
    
    private static int[] testUserManager(User_Manager userManager) throws SQLException {
        System.out.println("\n--- Testing User Manager ---");
        int passedTests = 0;
        int totalTests = 0;

        // Test user creation
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "password123";
        String testEmail = "test" + System.currentTimeMillis() + "@example.com";

        System.out.println("Creating test user: " + testUsername);
        userManager.addUser(testUsername, testPassword, testEmail);

        // Test retrieving user by username
        totalTests++;
        User retrievedUser = userManager.getUserByUsername(testUsername);
        if (retrievedUser != null && retrievedUser.getUsername().equals(testUsername)) {
            System.out.println("✅ SUCCESS: Retrieved user by username: " + retrievedUser.getUsername());
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: User retrieval by username failed");
        }

        // Test retrieving user by email
        totalTests++;
        User retrievedUserByEmail = userManager.getUserByEmail(testEmail);
        if (retrievedUserByEmail != null && retrievedUserByEmail.getEmail().equals(testEmail)) {
            System.out.println("✅ SUCCESS: Retrieved user by email: " + retrievedUserByEmail.getEmail());
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: User retrieval by email failed");
        }

        // Test username/password match
        totalTests++;
        boolean credentialsMatch = userManager.UsernamePasswordMatch(testUsername, testPassword);
        if (credentialsMatch) {
            System.out.println("✅ SUCCESS: Username/password match");
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: Username/password match failed");
        }

        // Test username/email match
        totalTests++;
        boolean usernameEmailMatch = userManager.UsernameEmailMatch(testUsername, testEmail);
        if (usernameEmailMatch) {
            System.out.println("✅ SUCCESS: Username/email match");
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: Username/email match failed");
        }

        // Test updating username and email
        totalTests++;
        String updatedUsername = "updated_" + testUsername;
        String updatedEmail = "updated_" + testEmail;
        System.out.println("Updating username and email");
        userManager.updateUser(retrievedUser, updatedUsername, testPassword, updatedEmail);
        
        // Verify username and email update
        User updatedUser = userManager.getUserById(retrievedUser.getUser_Id());
        if (updatedUser != null && 
            updatedUser.getUsername().equals(updatedUsername) && 
            updatedUser.getEmail().equals(updatedEmail)) {
            System.out.println("✅ SUCCESS: Username and email update successful");
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: Username and email update failed");
        }

        // Test updating user password
        totalTests++;
        String newPassword = "newpassword456";
        System.out.println("Updating user password");
        userManager.updateUserPassword(updatedUser, newPassword);

        // Verify password update
        boolean newCredentialsMatch = userManager.UsernamePasswordMatch(updatedUsername, newPassword);
        if (newCredentialsMatch) {
            System.out.println("✅ SUCCESS: Password update successful");
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: Password update failed");
        }

        // Set current user for Reading Manager tests
        userManager.setCurrentUser(updatedUser);
        if (userManager.getCurrentUser() != null && userManager.getCurrentUser().getUsername().equals(updatedUsername)) {
            System.out.println("✅ SUCCESS: Current user set: " + userManager.getCurrentUser().getUsername());
        } else {
            System.out.println("❌ FAILURE: Failed to set current user");
        }

        return new int[] {passedTests, totalTests};
    }
    
    private static int[] testReadingManager(User_Manager userManager, Reading_Manager readingManager) throws SQLException {
        System.out.println("\n--- Testing Reading Manager ---");
        int passedTests = 0;
        int totalTests = 0;
        
        User currentUser = userManager.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("Current user is not set, can't test Reading Manager");
        }
        
        // Test adding readings
        System.out.println("Adding test readings for user: " + currentUser.getUsername());
        
        // Add electricity reading
        totalTests++;
        LocalDate electricityDate = LocalDate.now().minusDays(30);
        double electricityReading = 500.5;
        double electricityRate = 0.15;
        double electricityTotal = electricityReading * electricityRate;
        
        try {
            readingManager.addReading(currentUser, electricityDate, "electricity", 
                    electricityReading, electricityRate, electricityTotal);
            
            // Verify the electricity reading was added
            Reading latestElectricity = readingManager.getLatestReadingByType(currentUser, "electricity");
            if (latestElectricity != null && 
                Math.abs(latestElectricity.getReading() - electricityReading) < 0.001 &&
                Math.abs(latestElectricity.getRate() - electricityRate) < 0.001 &&
                Math.abs(latestElectricity.getTotal_Price() - electricityTotal) < 0.001) {
                System.out.println("✅ SUCCESS: Added electricity reading correctly");
                passedTests++;
            } else {
                System.out.println("❌ FAILURE: Electricity reading not added correctly");
                if (latestElectricity != null) {
                    System.out.println("  Expected reading: " + electricityReading + ", got: " + latestElectricity.getReading());
                    System.out.println("  Expected rate: " + electricityRate + ", got: " + latestElectricity.getRate());
                    System.out.println("  Expected total: " + electricityTotal + ", got: " + latestElectricity.getTotal_Price());
                }
            }
        } catch (Exception e) {
            System.out.println("❌ FAILURE: Exception when adding electricity reading: " + e.getMessage());
        }
        
        // Add water reading
        totalTests++;
        LocalDate waterDate = LocalDate.now().minusDays(15);
        double waterReading = 20.5;
        double waterRate = 2.50;
        double waterTotal = waterReading * waterRate;
        
        try {
            readingManager.addReading(currentUser, waterDate, "water", 
                    waterReading, waterRate, waterTotal);
            
            // Verify the water reading was added
            Reading latestWater = readingManager.getLatestReadingByType(currentUser, "water");
            if (latestWater != null && 
                Math.abs(latestWater.getReading() - waterReading) < 0.001 &&
                Math.abs(latestWater.getRate() - waterRate) < 0.001 &&
                Math.abs(latestWater.getTotal_Price() - waterTotal) < 0.001) {
                System.out.println("✅ SUCCESS: Added water reading correctly");
                passedTests++;
            } else {
                System.out.println("❌ FAILURE: Water reading not added correctly");
                if (latestWater != null) {
                    System.out.println("  Expected reading: " + waterReading + ", got: " + latestWater.getReading());
                    System.out.println("  Expected rate: " + waterRate + ", got: " + latestWater.getRate());
                    System.out.println("  Expected total: " + waterTotal + ", got: " + latestWater.getTotal_Price());
                }
            }
        } catch (Exception e) {
            System.out.println("❌ FAILURE: Exception when adding water reading: " + e.getMessage());
        }
        
        
        // Test overloaded addReading method
        totalTests++;
        LocalDate overloadDate = LocalDate.now().minusDays(10);
        double overloadReading = 30.0;
        double overloadTotal = 75.0;
        
        try {
            readingManager.addReading(currentUser, overloadDate, "other", overloadReading, overloadTotal);
            
            // Verify the overloaded reading was added
            List<Reading> otherReadings = readingManager.getAllReadingsByType(currentUser, "other");
            boolean foundOverloadedReading = false;
            
            for (Reading r : otherReadings) {
                if (r.getDate().equals(overloadDate) && 
                    Math.abs(r.getReading() - overloadReading) < 0.001) {
                    foundOverloadedReading = true;
                    
                    // Check if total price is stored correctly
                    if (Math.abs(r.getTotal_Price() - overloadTotal) < 0.001) {
                        System.out.println("✅ SUCCESS: Added overloaded reading correctly");
                        passedTests++;
                    } else {
                        System.out.println("❌ FAILURE: Total price mismatch in overloaded reading");
                        System.out.println("  Expected total: " + overloadTotal + ", got: " + r.getTotal_Price());
                    }
                    break;
                }
            }
            
            if (!foundOverloadedReading) {
                System.out.println("❌ FAILURE: Overloaded reading not found");
            }
        } catch (Exception e) {
            System.out.println("❌ FAILURE: Exception when testing overloaded addReading: " + e.getMessage());
        }
        
        // Add gas reading
        totalTests++;
        LocalDate gasDate = LocalDate.now().minusDays(7);
        double gasReading = 100.0;
        double gasRate = 1.20;
        double gasTotal = gasReading * gasRate;
        
        try {
            readingManager.addReading(currentUser, gasDate, "gas", 
                    gasReading, gasRate, gasTotal);
            
            // Verify the gas reading was added
            Reading latestGas = readingManager.getLatestReadingByType(currentUser, "gas");
            if (latestGas != null && 
                Math.abs(latestGas.getReading() - gasReading) < 0.001 &&
                Math.abs(latestGas.getRate() - gasRate) < 0.001 &&
                Math.abs(latestGas.getTotal_Price() - gasTotal) < 0.001) {
                System.out.println("✅ SUCCESS: Added gas reading correctly");
                passedTests++;
            } else {
                System.out.println("❌ FAILURE: Gas reading not added correctly");
                if (latestGas != null) {
                    System.out.println("  Expected reading: " + gasReading + ", got: " + latestGas.getReading());
                    System.out.println("  Expected rate: " + gasRate + ", got: " + latestGas.getRate());
                    System.out.println("  Expected total: " + gasTotal + ", got: " + latestGas.getTotal_Price());
                }
            }
        } catch (Exception e) {
            System.out.println("❌ FAILURE: Exception when adding gas reading: " + e.getMessage());
        }
        
        // Test retrieving all readings for the user
        totalTests++;
        List<Reading> allReadings = readingManager.getReadingsByUserId(currentUser);
        if (allReadings.size() >= 3) {
            System.out.println("✅ SUCCESS: Retrieved " + allReadings.size() + " readings for user");
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: Retrieved only " + allReadings.size() + " readings, expected at least 3");
        }
        
        // Test retrieving readings within a date range
        totalTests++;
        LocalDate startDate = LocalDate.now().minusDays(20);
        LocalDate endDate = LocalDate.now();
        List<Reading> rangeReadings = readingManager.getReadingsByDate(currentUser, startDate, endDate);
        int expectedInRange = 0;
        for (Reading r : allReadings) {
            if (!r.getDate().isBefore(startDate) && !r.getDate().isAfter(endDate)) {
                expectedInRange++;
            }
        }
        
        if (rangeReadings.size() == expectedInRange) {
            System.out.println("✅ SUCCESS: Retrieved correct number of readings in date range: " + rangeReadings.size());
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: Date range filter incorrect. Expected: " + expectedInRange + ", got: " + rangeReadings.size());
        }
        
        // Test getting latest reading by type
        totalTests++;
        Reading latestGasReading = readingManager.getLatestReadingByType(currentUser, "gas");
        if (latestGasReading != null && latestGasReading.getType().equals("gas")) {
            System.out.println("✅ SUCCESS: Retrieved latest gas reading from date: " + latestGasReading.getDate());
            passedTests++;
        } else {
            System.out.println("❌ FAILURE: Failed to retrieve latest gas reading");
        }
        
        // Test updating a reading
        if (!allReadings.isEmpty()) {
            totalTests++;
            Reading readingToUpdate = allReadings.get(0);
            double originalReading = readingToUpdate.getReading();
            double newReading = originalReading + 10;
            double newTotal = newReading * readingToUpdate.getRate();
            
            try {
                System.out.println("Updating reading ID: " + readingToUpdate.getReading_Id());
                readingManager.updateReading(currentUser, readingToUpdate.getReading_Id(), 
                        readingToUpdate.getDate(), readingToUpdate.getType(), 
                        newReading, readingToUpdate.getRate(), newTotal);
                
                // Verify update
                Reading updatedReading = readingManager.getReadingById(readingToUpdate.getReading_Id());
                if (updatedReading != null && Math.abs(updatedReading.getReading() - newReading) < 0.001) {
                    System.out.println("✅ SUCCESS: Updated reading value: " + updatedReading.getReading());
                    passedTests++;
                } else {
                    System.out.println("❌ FAILURE: Reading update failed");
                    if (updatedReading != null) {
                        System.out.println("  Expected reading: " + newReading + ", got: " + updatedReading.getReading());
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ FAILURE: Exception when updating reading: " + e.getMessage());
            }
        }
        
        // Test overloaded updateReading method
        if (allReadings.size() > 1) {
            totalTests++;
            Reading readingToUpdate = allReadings.get(1);
            double originalReading = readingToUpdate.getReading();
            double newReading = originalReading + 15;
            double newTotal = newReading * 2; // Arbitrary total
            
            try {
                System.out.println("Testing overloaded updateReading with reading ID: " + readingToUpdate.getReading_Id());
                readingManager.updateReading(currentUser, readingToUpdate.getReading_Id(), 
                        readingToUpdate.getDate(), readingToUpdate.getType(), 
                        newReading, newTotal);
                
                // Verify update
                Reading updatedReading = readingManager.getReadingById(readingToUpdate.getReading_Id());
                if (updatedReading != null && 
                    Math.abs(updatedReading.getReading() - newReading) < 0.001 &&
                    Math.abs(updatedReading.getTotal_Price() - newTotal) < 0.001) {
                    System.out.println("✅ SUCCESS: Updated reading with overloaded method");
                    passedTests++;
                } else {
                    System.out.println("❌ FAILURE: Overloaded reading update failed");
                    if (updatedReading != null) {
                        System.out.println("  Expected reading: " + newReading + ", got: " + updatedReading.getReading());
                        System.out.println("  Expected total: " + newTotal + ", got: " + updatedReading.getTotal_Price());
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ FAILURE: Exception when testing overloaded updateReading: " + e.getMessage());
            }
        }
        
        // Test deleting a reading
        if (!allReadings.isEmpty()) {
            totalTests++;
            Reading readingToDelete = allReadings.get(allReadings.size() - 1);
            try {
                System.out.println("Deleting reading ID: " + readingToDelete.getReading_Id());
                readingManager.deleteReading(readingToDelete);
                
                // Verify deletion
                Reading deletedReading = readingManager.getReadingById(readingToDelete.getReading_Id());
                if (deletedReading == null) {
                    System.out.println("✅ SUCCESS: Reading deleted successfully");
                    passedTests++;
                } else {
                    System.out.println("❌ FAILURE: Reading deletion failed, reading still exists");
                }
            } catch (Exception e) {
                System.out.println("❌ FAILURE: Exception when deleting reading: " + e.getMessage());
            }
        }
        
        // Test the overloaded deleteReading method
        if (allReadings.size() > 1) {
            totalTests++;
            Reading readingToDelete = allReadings.get(0);
            try {
                System.out.println("Testing overloaded deleteReading with reading ID: " + readingToDelete.getReading_Id());
                readingManager.deleteReading(currentUser, readingToDelete.getReading_Id());
                
                // Verify deletion
                Reading deletedReading = readingManager.getReadingById(readingToDelete.getReading_Id());
                if (deletedReading == null) {
                    System.out.println("✅ SUCCESS: Reading deleted using overloaded method");
                    passedTests++;
                } else {
                    System.out.println("❌ FAILURE: Overloaded reading deletion failed, reading still exists");
                }
            } catch (Exception e) {
                System.out.println("❌ FAILURE: Exception when testing overloaded deleteReading: " + e.getMessage());
            }
        }
        
        // Clean up remaining test data
        System.out.println("\n--- Cleaning up test data ---");
        
        // Delete all readings for test user
        allReadings = readingManager.getReadingsByUserId(currentUser);
        for (Reading reading : allReadings) {
            readingManager.deleteReading(reading);
        }
        
        // Delete test user
        userManager.deleteUser(currentUser);
        User deletedUser = userManager.getUserById(currentUser.getUser_Id());
        if (deletedUser == null) {
            System.out.println("✅ SUCCESS: Test user deleted successfully");
        } else {
            System.out.println("❌ FAILURE: User deletion failed, user still exists");
        }
        
        return new int[] {passedTests, totalTests};
    }
}