package database.test;
//
//import database.Database_Manager;
//import database.User_Manager;
//import database.Reading_Manager;
//import model.User;
//import model.Reading;
//
//import java.awt.Color;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.Month;
//import java.time.YearMonth;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JPanel;
//
public class Database_Manager_Test {
//    
//    private static final String ANSI_GREEN = "\u001B[32m";
//    private static final String ANSI_RED = "\u001B[31m";
//    private static final String ANSI_BLUE = "\u001B[34m";
//    private static final String ANSI_RESET = "\u001B[0m";
//    private static final String ANSI_YELLOW = "\u001B[33m";
//    
//    public static void main(String[] args) {
//        System.out.println(ANSI_BLUE + "Starting Comprehensive Database Manager Test" + ANSI_RESET);
//        System.out.println("=" .repeat(50));
//        
//        // Get the database manager instance
//        Database_Manager dbManager = Database_Manager.getInstance();
//        
//        // Get the specialized managers
//        User_Manager userManager = dbManager.getUserManager();
//        Reading_Manager readingManager = dbManager.getReadingManager();
//        
//        int passedTests = 0;
//        int totalTests = 0;
//        
//        try {
//            // Test User Manager operations
//            System.out.println(ANSI_YELLOW + "\n=== USER MANAGER TESTS ===" + ANSI_RESET);
//            int[] userTestResults = testUserManager(userManager);
//            passedTests += userTestResults[0];
//            totalTests += userTestResults[1];
//            
//            // Test Reading Manager basic operations
//            System.out.println(ANSI_YELLOW + "\n=== READING MANAGER BASIC TESTS ===" + ANSI_RESET);
//            int[] readingTestResults = testReadingManager(userManager, readingManager);
//            passedTests += readingTestResults[0];
//            totalTests += readingTestResults[1];
//            
//            // Test advanced Reading Manager operations
//            System.out.println(ANSI_YELLOW + "\n=== READING MANAGER ADVANCED TESTS ===" + ANSI_RESET);
//            int[] advancedReadingTestResults = testAdvancedReadingManager(userManager, readingManager);
//            passedTests += advancedReadingTestResults[0];
//            totalTests += advancedReadingTestResults[1];
//            
//            // Test edge cases and error handling
//            System.out.println(ANSI_YELLOW + "\n=== EDGE CASE AND ERROR HANDLING TESTS ===" + ANSI_RESET);
//            int[] edgeCaseResults = testEdgeCases(userManager, readingManager);
//            passedTests += edgeCaseResults[0];
//            totalTests += edgeCaseResults[1];
//            
//            // Test UI-related methods
//            System.out.println(ANSI_YELLOW + "\n=== UI INTEGRATION TESTS ===" + ANSI_RESET);
//            int[] uiTestResults = testUIIntegration(userManager, readingManager);
//            passedTests += uiTestResults[0];
//            totalTests += uiTestResults[1];
//            
//            // Final test summary
//            printTestSummary(passedTests, totalTests);
//            
//        } catch (Exception e) {
//            System.err.println(ANSI_RED + "Test suite failed with exception: " + e.getMessage() + ANSI_RESET);
//            e.printStackTrace();
//        } finally {
//            // Clean up and close database connection
//            cleanupTestData(userManager, readingManager);
//            dbManager.closeConnection();
//            System.out.println(ANSI_BLUE + "\nDatabase connection closed." + ANSI_RESET);
//        }
//    }
//    
//    private static void printTestSummary(int passedTests, int totalTests) {
//        System.out.println("\n" + "=".repeat(50));
//        System.out.println(ANSI_BLUE + "COMPREHENSIVE TEST SUMMARY:" + ANSI_RESET);
//        System.out.println("Passed tests: " + ANSI_GREEN + passedTests + ANSI_RESET + "/" + totalTests + 
//                " (" + (totalTests > 0 ? (passedTests * 100 / totalTests) : 0) + "%)");
//        
//        if (passedTests == totalTests) {
//            System.out.println(ANSI_GREEN + "🎉 ALL TESTS PASSED SUCCESSFULLY!" + ANSI_RESET);
//        } else {
//            int failedTests = totalTests - passedTests;
//            System.out.println(ANSI_RED + "⚠️  " + failedTests + " test(s) failed. See log for details." + ANSI_RESET);
//        }
//        System.out.println("=".repeat(50));
//    }
//    
//    private static int[] testUserManager(User_Manager userManager) throws SQLException {
//        System.out.println("Testing User Manager functionality...");
//        int passedTests = 0;
//        int totalTests = 0;
//
//        // Test 1: User creation with parameters
//        totalTests++;
//        String testUsername = "testuser_" + System.currentTimeMillis();
//        String testPassword = "SecurePassword123!";
//        String testEmail = "test" + System.currentTimeMillis() + "@example.com";
//
//        try {
//            userManager.addUser(testUsername, testPassword, testEmail);
//            User retrievedUser = userManager.getUserByUsername(testUsername);
//            
//            if (retrievedUser != null && retrievedUser.getUsername().equals(testUsername)) {
//                printSuccess("User creation and retrieval by username");
//                passedTests++;
//            } else {
//                printFailure("User creation and retrieval by username");
//            }
//        } catch (Exception e) {
//            printFailure("User creation with exception: " + e.getMessage());
//        }
//
//        // Test 2: User retrieval by email
//        totalTests++;
//        try {
//            User retrievedUserByEmail = userManager.getUserByEmail(testEmail);
//            if (retrievedUserByEmail != null && retrievedUserByEmail.getEmail().equals(testEmail)) {
//                printSuccess("User retrieval by email");
//                passedTests++;
//            } else {
//                printFailure("User retrieval by email");
//            }
//        } catch (Exception e) {
//            printFailure("User retrieval by email with exception: " + e.getMessage());
//        }
//
//        // Test 3: User retrieval by ID
//        totalTests++;
//        try {
//            User user = userManager.getUserByUsername(testUsername);
//            if (user != null) {
//                User retrievedUserById = userManager.getUserById(user.getUser_Id());
//                if (retrievedUserById != null && retrievedUserById.getUser_Id() == user.getUser_Id()) {
//                    printSuccess("User retrieval by ID");
//                    passedTests++;
//                } else {
//                    printFailure("User retrieval by ID");
//                }
//            } else {
//                printFailure("User retrieval by ID - base user not found");
//            }
//        } catch (Exception e) {
//            printFailure("User retrieval by ID with exception: " + e.getMessage());
//        }
//
//        // Test 4: Username/Password validation
//        totalTests++;
//        try {
//            boolean credentialsMatch = userManager.UsernamePasswordMatch(testUsername, testPassword);
//            if (credentialsMatch) {
//                printSuccess("Username/password validation");
//                passedTests++;
//            } else {
//                printFailure("Username/password validation - credentials don't match");
//            }
//        } catch (Exception e) {
//            printFailure("Username/password validation with exception: " + e.getMessage());
//        }
//
//        // Test 5: Username/Email validation
//        totalTests++;
//        try {
//            boolean usernameEmailMatch = userManager.UsernameEmailMatch(testUsername, testEmail);
//            if (usernameEmailMatch) {
//                printSuccess("Username/email validation");
//                passedTests++;
//            } else {
//                printFailure("Username/email validation - credentials don't match");
//            }
//        } catch (Exception e) {
//            printFailure("Username/email validation with exception: " + e.getMessage());
//        }
//
//        // Test 6: User update (all fields)
//        totalTests++;
//        try {
//            User user = userManager.getUserByUsername(testUsername);
//            String updatedUsername = "updated_" + testUsername;
//            String updatedPassword = "UpdatedPassword456!";
//            String updatedEmail = "updated_" + testEmail;
//            
//            userManager.updateUser(user, updatedUsername, updatedPassword, updatedEmail);
//            
//            User updatedUser = userManager.getUserById(user.getUser_Id());
//            boolean updateSuccess = updatedUser != null && 
//                    updatedUser.getUsername().equals(updatedUsername) && 
//                    updatedUser.getEmail().equals(updatedEmail) &&
//                    userManager.UsernamePasswordMatch(updatedUsername, updatedPassword);
//            
//            if (updateSuccess) {
//                printSuccess("User update (all fields)");
//                passedTests++;
//                // Update test variables for subsequent tests
//                testUsername = updatedUsername;
//                testPassword = updatedPassword;
//                testEmail = updatedEmail;
//            } else {
//                printFailure("User update (all fields)");
//            }
//        } catch (Exception e) {
//            printFailure("User update with exception: " + e.getMessage());
//        }
//
//        // Test 7: Password-only update
//        totalTests++;
//        try {
//            User user = userManager.getUserByUsername(testUsername);
//            String newPassword = "NewPassword789!";
//            
//            userManager.updateUserPassword(user, newPassword);
//            
//            boolean passwordUpdated = userManager.UsernamePasswordMatch(testUsername, newPassword);
//            if (passwordUpdated) {
//                printSuccess("Password-only update");
//                passedTests++;
//                testPassword = newPassword; // Update for cleanup
//            } else {
//                printFailure("Password-only update");
//            }
//        } catch (Exception e) {
//            printFailure("Password-only update with exception: " + e.getMessage());
//        }
//
//        // Test 8: Current user management
//        totalTests++;
//        try {
//            User user = userManager.getUserByUsername(testUsername);
//            userManager.setCurrentUser(user);
//            User currentUser = userManager.getCurrentUser();
//            
//            if (currentUser != null && currentUser.getUsername().equals(testUsername)) {
//                printSuccess("Current user set and retrieved");
//                passedTests++;
//            } else {
//                printFailure("Current user set and retrieved");
//            }
//        } catch (Exception e) {
//            printFailure("Current user management with exception: " + e.getMessage());
//        }
//
//        // Test 9: Current user null handling
//        totalTests++;
//        try {
//            userManager.setCurrentUserNull();
//            if (userManager.getCurrentUser() == null) {
//                printSuccess("Current user set to null");
//                passedTests++;
//            } else {
//                printFailure("Current user set to null");
//            }
//            
//            // Reset current user for subsequent tests
//            User user = userManager.getUserByUsername(testUsername);
//            userManager.setCurrentUser(user);
//        } catch (Exception e) {
//            printFailure("Current user null handling with exception: " + e.getMessage());
//        }
//
//        // Test 10: Add user with User object
//        totalTests++;
//        try {
//            User newUser = new User();
//            String secondUsername = "testuser2_" + System.currentTimeMillis();
//            String secondPassword = "SecondPassword123!";
//            String secondEmail = "test2_" + System.currentTimeMillis() + "@example.com";
//            
//            newUser.setUsername(secondUsername);
//            newUser.setPassword(secondPassword);
//            newUser.setEmail(secondEmail);
//            
//            userManager.addUser(newUser);
//            User retrievedSecondUser = userManager.getUserByUsername(secondUsername);
//            
//            if (retrievedSecondUser != null && retrievedSecondUser.getUsername().equals(secondUsername)) {
//                printSuccess("Add user with User object");
//                passedTests++;
//                
//                // Cleanup second user
//                userManager.deleteUser(retrievedSecondUser);
//            } else {
//                printFailure("Add user with User object");
//            }
//        } catch (Exception e) {
//            printFailure("Add user with User object exception: " + e.getMessage());
//        }
//
//        return new int[] {passedTests, totalTests};
//    }
//    
//    private static int[] testReadingManager(User_Manager userManager, Reading_Manager readingManager) throws SQLException {
//        System.out.println("Testing Reading Manager basic functionality...");
//        int passedTests = 0;
//        int totalTests = 0;
//        
//        User currentUser = userManager.getCurrentUser();
//        if (currentUser == null) {
//            throw new RuntimeException("Current user is not set, can't test Reading Manager");
//        }
//        
//        // Clean up existing readings for clean test
//        List<Reading> existingReadings = readingManager.getReadings_By_User_Id(currentUser);
//        for (Reading reading : existingReadings) {
//            readingManager.deleteReading(reading);
//        }
//        
//        // Test 1: Add reading with all parameters
//        totalTests++;
//        try {
//            LocalDate electricityDate = LocalDate.now().minusDays(30);
//            double electricityReading = 500.5;
//            double electricityRate = 0.15;
//            double electricityTotal = electricityReading * electricityRate;
//            
//            readingManager.addReading(currentUser, electricityDate, "electricity", 
//                    electricityReading, electricityRate, electricityTotal);
//            
//            Reading latestElectricity = readingManager.getLatest_Reading_By_Type(currentUser, "electricity");
//            if (validateReading(latestElectricity, electricityReading, electricityRate, electricityTotal)) {
//                printSuccess("Add electricity reading with all parameters");
//                passedTests++;
//            } else {
//                printFailure("Add electricity reading with all parameters");
//                debugReading("Expected", electricityReading, electricityRate, electricityTotal);
//                if (latestElectricity != null) {
//                    debugReading("Actual", latestElectricity.getReading(), 
//                            latestElectricity.getRate(), latestElectricity.getTotal_Price());
//                }
//            }
//        } catch (Exception e) {
//            printFailure("Add electricity reading exception: " + e.getMessage());
//        }
//        
//        // Test 2: Add reading without rate (overloaded method)
//        totalTests++;
//        try {
//            LocalDate waterDate = LocalDate.now().minusDays(15);
//            double waterReading = 25.0;
//            double waterTotal = 62.50;
//            
//            readingManager.addReading(currentUser, waterDate, "water", waterReading, waterTotal);
//            
//            List<Reading> waterReadings = readingManager.getAll_Readings_By_Type(currentUser, "water");
//            Reading addedReading = waterReadings.stream()
//                    .filter(r -> r.getDate().equals(waterDate))
//                    .findFirst().orElse(null);
//            
//            if (addedReading != null && 
//                Math.abs(addedReading.getReading() - waterReading) < 0.001 &&
//                Math.abs(addedReading.getTotal_Price() - waterTotal) < 0.001) {
//                printSuccess("Add water reading without rate");
//                passedTests++;
//            } else {
//                printFailure("Add water reading without rate");
//            }
//        } catch (Exception e) {
//            printFailure("Add water reading without rate exception: " + e.getMessage());
//        }
//        
//        // Test 3: Add reading with Reading object
//        totalTests++;
//        try {
//            Reading gasReading = new Reading();
//            gasReading.setUser_Id(currentUser.getUser_Id());
//            gasReading.setDate(LocalDate.now().minusDays(10));
//            gasReading.setType("gas");
//            gasReading.setReading(150.0);
//            gasReading.setRate(1.25);
//            gasReading.setTotal_Price(187.50);
//            
//            readingManager.addReading(currentUser, gasReading);
//            
//            Reading latestGas = readingManager.getLatest_Reading_By_Type(currentUser, "gas");
//            if (validateReading(latestGas, 150.0, 1.25, 187.50)) {
//                printSuccess("Add gas reading with Reading object");
//                passedTests++;
//            } else {
//                printFailure("Add gas reading with Reading object");
//            }
//        } catch (Exception e) {
//            printFailure("Add gas reading with Reading object exception: " + e.getMessage());
//        }
//        
//        // Test 4: Reading retrieval methods
//        totalTests++;
//        try {
//            List<Reading> allReadings = readingManager.getReadings_By_User_Id(currentUser);
//            if (allReadings.size() >= 3) {
//                printSuccess("Get all readings by user ID (" + allReadings.size() + " readings)");
//                passedTests++;
//            } else {
//                printFailure("Get all readings by user ID (expected >= 3, got " + allReadings.size() + ")");
//            }
//        } catch (Exception e) {
//            printFailure("Get all readings by user ID exception: " + e.getMessage());
//        }
//        
//        // Test 5: Reading retrieval by date range
//        totalTests++;
//        try {
//            LocalDate startDate = LocalDate.now().minusDays(35);
//            LocalDate endDate = LocalDate.now();
//            List<Reading> rangeReadings = readingManager.getReadings_By_Date(currentUser, startDate, endDate);
//            
//            if (rangeReadings.size() >= 2) { // Should include electricity and water readings
//                printSuccess("Get readings by date range (" + rangeReadings.size() + " readings)");
//                passedTests++;
//            } else {
//                printFailure("Get readings by date range (expected >= 2, got " + rangeReadings.size() + ")");
//            }
//        } catch (Exception e) {
//            printFailure("Get readings by date range exception: " + e.getMessage());
//        }
//        
//        // Test 6: Reading retrieval by date range and type
//        totalTests++;
//        try {
//            LocalDate startDate = LocalDate.now().minusDays(35);
//            LocalDate endDate = LocalDate.now();
//            List<Reading> electricityInRange = readingManager.getReadings_By_Date_And_Type(
//                    currentUser, startDate, endDate, "electricity");
//            
//            if (electricityInRange.size() == 1) {
//                printSuccess("Get readings by date range and type");
//                passedTests++;
//            } else {
//                printFailure("Get readings by date range and type (expected 1, got " + electricityInRange.size() + ")");
//            }
//        } catch (Exception e) {
//            printFailure("Get readings by date range and type exception: " + e.getMessage());
//        }
//        
//        // Test 7: Reading existence check
//        totalTests++;
//        try {
//            boolean electricityExists = readingManager.isReading_Exists(currentUser, "electricity");
//            boolean nonExistentExists = readingManager.isReading_Exists(currentUser, "nonexistent");
//            
//            if (electricityExists && !nonExistentExists) {
//                printSuccess("Reading existence check");
//                passedTests++;
//            } else {
//                printFailure("Reading existence check (electricity: " + electricityExists + 
//                        ", nonexistent: " + nonExistentExists + ")");
//            }
//        } catch (Exception e) {
//            printFailure("Reading existence check exception: " + e.getMessage());
//        }
//        
//        // Test 8: Reading retrieval by ID (multiple overloads)
//        List<Reading> allReadings = readingManager.getReadings_By_User_Id(currentUser);
//        if (!allReadings.isEmpty()) {
//            Reading testReading = allReadings.get(0);
//            int testId = testReading.getReading_Id();
//            
//            // Test getReading_By_Id(id)
//            totalTests++;
//            try {
//                Reading retrievedById = readingManager.getReading_By_Id(testId);
//                if (retrievedById != null && retrievedById.getReading_Id() == testId) {
//                    printSuccess("Get reading by ID");
//                    passedTests++;
//                } else {
//                    printFailure("Get reading by ID");
//                }
//            } catch (Exception e) {
//                printFailure("Get reading by ID exception: " + e.getMessage());
//            }
//            
//            // Test getReading_By_Id(user, id)
//            totalTests++;
//            try {
//                Reading retrievedByUserAndId = readingManager.getReading_By_Id(currentUser, testId);
//                if (retrievedByUserAndId != null && retrievedByUserAndId.getReading_Id() == testId) {
//                    printSuccess("Get reading by user and ID");
//                    passedTests++;
//                } else {
//                    printFailure("Get reading by user and ID");
//                }
//            } catch (Exception e) {
//                printFailure("Get reading by user and ID exception: " + e.getMessage());
//            }
//            
//            // Test getReading_By_Id(user, reading)
//            totalTests++;
//            try {
//                Reading retrievedByUserAndReading = readingManager.getReading_By_Id(currentUser, testReading);
//                if (retrievedByUserAndReading != null && retrievedByUserAndReading.getReading_Id() == testId) {
//                    printSuccess("Get reading by user and reading object");
//                    passedTests++;
//                } else {
//                    printFailure("Get reading by user and reading object");
//                }
//            } catch (Exception e) {
//                printFailure("Get reading by user and reading object exception: " + e.getMessage());
//            }
//            
//            // Test getReading_By_Id(reading)
//            totalTests++;
//            try {
//                Reading retrievedByReading = readingManager.getReading_By_Id(testReading);
//                if (retrievedByReading != null && retrievedByReading.getReading_Id() == testId) {
//                    printSuccess("Get reading by reading object");
//                    passedTests++;
//                } else {
//                    printFailure("Get reading by reading object");
//                }
//            } catch (Exception e) {
//                printFailure("Get reading by reading object exception: " + e.getMessage());
//            }
//        }
//        
//        // Test 9: Update reading (multiple overloads)
//        if (allReadings.size() >= 2) {
//            Reading readingToUpdate = allReadings.get(0);
//            double newReading = readingToUpdate.getReading() + 10;
//            double newTotal = newReading * readingToUpdate.getRate();
//            
//            // Test updateReading with all parameters
//            totalTests++;
//            try {
//                readingManager.updateReading(currentUser, readingToUpdate.getReading_Id(),
//                        readingToUpdate.getDate(), readingToUpdate.getType(),
//                        newReading, readingToUpdate.getRate(), newTotal);
//                
//                Reading updated = readingManager.getReading_By_Id(readingToUpdate.getReading_Id());
//                if (updated != null && Math.abs(updated.getReading() - newReading) < 0.001) {
//                    printSuccess("Update reading with all parameters");
//                    passedTests++;
//                } else {
//                    printFailure("Update reading with all parameters");
//                }
//            } catch (Exception e) {
//                printFailure("Update reading with all parameters exception: " + e.getMessage());
//            }
//            
//            // Test updateReading without rate
//            if (allReadings.size() >= 2) {
//                Reading secondReading = allReadings.get(1);
//                double newReading2 = secondReading.getReading() + 15;
//                double newTotal2 = 200.0; // Arbitrary total
//                
//                totalTests++;
//                try {
//                    readingManager.updateReading(currentUser, secondReading.getReading_Id(),
//                            secondReading.getDate(), secondReading.getType(),
//                            newReading2, newTotal2);
//                    
//                    Reading updated = readingManager.getReading_By_Id(secondReading.getReading_Id());
//                    if (updated != null && Math.abs(updated.getReading() - newReading2) < 0.001 &&
//                        Math.abs(updated.getTotal_Price() - newTotal2) < 0.001) {
//                        printSuccess("Update reading without rate");
//                        passedTests++;
//                    } else {
//                        printFailure("Update reading without rate");
//                    }
//                } catch (Exception e) {
//                    printFailure("Update reading without rate exception: " + e.getMessage());
//                }
//            }
//            
//            // Test updateReading with Reading object
//            if (allReadings.size() >= 3) {
//                Reading thirdReading = allReadings.get(2);
//                double originalReading = thirdReading.getReading();
//                double newReading3 = originalReading + 20;
//                thirdReading.setReading(newReading3);
//                thirdReading.setTotal_Price(newReading3 * thirdReading.getRate());
//                
//                totalTests++;
//                try {
//                    readingManager.updateReading(currentUser, thirdReading);
//                    
//                    Reading updated = readingManager.getReading_By_Id(thirdReading.getReading_Id());
//                    if (updated != null && Math.abs(updated.getReading() - newReading3) < 0.001) {
//                        printSuccess("Update reading with Reading object");
//                        passedTests++;
//                    } else {
//                        printFailure("Update reading with Reading object");
//                    }
//                } catch (Exception e) {
//                    printFailure("Update reading with Reading object exception: " + e.getMessage());
//                }
//            }
//        }
//        
//        // Test 10: Delete reading (multiple overloads)
//        List<Reading> currentReadings = readingManager.getReadings_By_User_Id(currentUser);
//        if (!currentReadings.isEmpty()) {
//            Reading readingToDelete = currentReadings.get(currentReadings.size() - 1);
//            
//            // Test deleteReading(reading)
//            totalTests++;
//            try {
//                readingManager.deleteReading(readingToDelete);
//                
//                Reading deleted = readingManager.getReading_By_Id(readingToDelete.getReading_Id());
//                if (deleted == null) {
//                    printSuccess("Delete reading with Reading object");
//                    passedTests++;
//                } else {
//                    printFailure("Delete reading with Reading object - reading still exists");
//                }
//            } catch (Exception e) {
//                printFailure("Delete reading with Reading object exception: " + e.getMessage());
//            }
//            
//            // Test deleteReading(user, reading)
//            if (currentReadings.size() >= 2) {
//                Reading secondReadingToDelete = currentReadings.get(currentReadings.size() - 2);
//                
//                totalTests++;
//                try {
//                    readingManager.deleteReading(currentUser, secondReadingToDelete);
//                    
//                    Reading deleted = readingManager.getReading_By_Id(secondReadingToDelete.getReading_Id());
//                    if (deleted == null) {
//                        printSuccess("Delete reading with user and Reading object");
//                        passedTests++;
//                    } else {
//                        printFailure("Delete reading with user and Reading object - reading still exists");
//                    }
//                } catch (Exception e) {
//                    printFailure("Delete reading with user and Reading object exception: " + e.getMessage());
//                }
//            }
//            
//            // Test deleteReading(user, id)
//            if (currentReadings.size() >= 3) {
//                Reading thirdReadingToDelete = currentReadings.get(currentReadings.size() - 3);
//                
//                totalTests++;
//                try {
//                    readingManager.deleteReading(currentUser, thirdReadingToDelete.getReading_Id());
//                    
//                    Reading deleted = readingManager.getReading_By_Id(thirdReadingToDelete.getReading_Id());
//                    if (deleted == null) {
//                        printSuccess("Delete reading with user and ID");
//                        passedTests++;
//                    } else {
//                        printFailure("Delete reading with user and ID - reading still exists");
//                    }
//                } catch (Exception e) {
//                    printFailure("Delete reading with user and ID exception: " + e.getMessage());
//                }
//            }
//        }
//        
//        return new int[] {passedTests, totalTests};
//    }
//    
//    private static int[] testAdvancedReadingManager(User_Manager userManager, Reading_Manager readingManager) throws SQLException {
//        System.out.println("Testing Reading Manager advanced functionality...");
//        int passedTests = 0;
//        int totalTests = 0;
//        
//        User currentUser = userManager.getCurrentUser();
//        if (currentUser == null) {
//            throw new RuntimeException("Current user is not set, can't test Advanced Reading Manager features");
//        }
//        
//        // Clean up and generate comprehensive test data
//        List<Reading> existingReadings = readingManager.getReadings_By_User_Id(currentUser);
//        for (Reading reading : existingReadings) {
//            readingManager.deleteReading(reading);
//        }
//        
//        // Generate 6 months of data for multiple utility types
//        generateTestData(currentUser, readingManager);
//        
//        // Test 1: Get all readings by type
//        totalTests++;
//        try {
//            List<Reading> electricityReadings = readingManager.getAll_Readings_By_Type(currentUser, "electricity");
//            if (electricityReadings.size() == 6) {
//                printSuccess("Get all readings by type (" + electricityReadings.size() + " electricity readings)");
//                passedTests++;
//            } else {
//                printFailure("Get all readings by type (expected 6, got " + electricityReadings.size() + ")");
//            }
//        } catch (Exception e) {
//            printFailure("Get all readings by type exception: " + e.getMessage());
//        }
//        
//        // Test 2: Group readings by month (usage)
//        totalTests++;
//        try {
//            List<Reading> electricityReadings = readingManager.getAll_Readings_By_Type(currentUser, "electricity");
//            int currentYear = LocalDate.now().getYear();
//            Map<Month, Double> groupedUsage = readingManager.groupReadings_By_Month(electricityReadings, currentYear, false);
//            
//            if (groupedUsage.size() <= 6 && !groupedUsage.isEmpty()) {
//                printSuccess("Group readings by month (usage) - " + groupedUsage.size() + " months");
//                passedTests++;
//            } else {
//                printFailure("Group readings by month (usage) - expected <= 6 months, got " + groupedUsage.size());
//            }
//        } catch (Exception e) {
//            printFailure("Group readings by month (usage) exception: " + e.getMessage());
//        }
//        
//     // Test 3: Group readings by month (prices) - CONTINUATION
//        totalTests++;
//        try {
//            List<Reading> electricityReadings = readingManager.getAll_Readings_By_Type(currentUser, "electricity");
//            int currentYear = LocalDate.now().getYear();
//            Map<Month, Double> groupedPrices = readingManager.groupReadings_By_Month(electricityReadings, currentYear, true);
//            
//            if (groupedPrices.size() <= 6 && !groupedPrices.isEmpty()) {
//                printSuccess("Group readings by month (prices) - " + groupedPrices.size() + " months");
//                passedTests++;
//            } else {
//                printFailure("Group readings by month (prices) - expected <= 6 months, got " + groupedPrices.size());
//            }
//        } catch (Exception e) {
//            printFailure("Group readings by month (prices) exception: " + e.getMessage());
//        }
//        
//        // Test 4: Get monthly utility data (usage)
//        totalTests++;
//        try {
//            int currentYear = LocalDate.now().getYear();
//            Map<Month, Double> monthlyElectricityUsage = readingManager.getMonthly_Utility_Data(
//                    currentUser, "electricity", currentYear, false);
//            
//            if (!monthlyElectricityUsage.isEmpty() && monthlyElectricityUsage.size() <= 6) {
//                printSuccess("Get monthly utility data (usage) - " + monthlyElectricityUsage.size() + " months");
//                passedTests++;
//            } else {
//                printFailure("Get monthly utility data (usage) - got " + monthlyElectricityUsage.size() + " months");
//            }
//        } catch (Exception e) {
//            printFailure("Get monthly utility data (usage) exception: " + e.getMessage());
//        }
//        
//        // Test 5: Get monthly utility data (prices)
//        totalTests++;
//        try {
//            int currentYear = LocalDate.now().getYear();
//            Map<Month, Double> monthlyElectricityPrices = readingManager.getMonthly_Utility_Data(
//                    currentUser, "electricity", currentYear, true);
//            
//            if (!monthlyElectricityPrices.isEmpty() && monthlyElectricityPrices.size() <= 6) {
//                printSuccess("Get monthly utility data (prices) - " + monthlyElectricityPrices.size() + " months");
//                passedTests++;
//            } else {
//                printFailure("Get monthly utility data (prices) - got " + monthlyElectricityPrices.size() + " months");
//            }
//        } catch (Exception e) {
//            printFailure("Get monthly utility data (prices) exception: " + e.getMessage());
//        }
//        
//        // Test 6: Get monthly total expenses
//        totalTests++;
//        try {
//            int currentYear = LocalDate.now().getYear();
//            Map<Month, Double> monthlyTotalExpenses = readingManager.getMonthly_Total_Expenses(currentUser, currentYear);
//            
//            if (!monthlyTotalExpenses.isEmpty()) {
//                double totalExpenses = monthlyTotalExpenses.values().stream().mapToDouble(Double::doubleValue).sum();
//                if (totalExpenses > 0) {
//                    printSuccess("Get monthly total expenses - total: $" + String.format("%.2f", totalExpenses));
//                    passedTests++;
//                } else {
//                    printFailure("Get monthly total expenses - total is 0");
//                }
//            } else {
//                printFailure("Get monthly total expenses - no data returned");
//            }
//        } catch (Exception e) {
//            printFailure("Get monthly total expenses exception: " + e.getMessage());
//        }
//        
//        // Test 7: Get total expenses in date range
//        totalTests++;
//        try {
//            LocalDate startDate = LocalDate.now().minusDays(180);
//            LocalDate endDate = LocalDate.now();
//            double totalExpenses = readingManager.getTotal_Expenses_In_Range(currentUser, startDate, endDate);
//            
//            if (totalExpenses > 0) {
//                printSuccess("Get total expenses in range: $" + String.format("%.2f", totalExpenses));
//                passedTests++;
//            } else {
//                printFailure("Get total expenses in range - returned 0");
//            }
//        } catch (Exception e) {
//            printFailure("Get total expenses in range exception: " + e.getMessage());
//        }
//        
//        // Test 8: Get latest readings for all types
//        totalTests++;
//        try {
//            Map<String, Reading> latestReadings = readingManager.getLatest_Readings_For_All_Types(currentUser);
//            
//            if (latestReadings.size() >= 3) { // Should have electricity, water, gas
//                printSuccess("Get latest readings for all types (" + latestReadings.size() + " types)");
//                passedTests++;
//            } else {
//                printFailure("Get latest readings for all types (expected >= 3, got " + latestReadings.size() + ")");
//            }
//        } catch (Exception e) {
//            printFailure("Get latest readings for all types exception: " + e.getMessage());
//        }
//        
//        // Test 9: Get total latest cost
//        totalTests++;
//        try {
//            double totalLatestCost = readingManager.getTotal_Latest_Cost(currentUser);
//            
//            if (totalLatestCost > 0) {
//                printSuccess("Get total latest cost: $" + String.format("%.2f", totalLatestCost));
//                passedTests++;
//            } else {
//                printFailure("Get total latest cost - returned 0");
//            }
//        } catch (Exception e) {
//            printFailure("Get total latest cost exception: " + e.getMessage());
//        }
//        
//        // Test 10: Trend analysis for specific type
//        totalTests++;
//        try {
//            String electricityTrend = readingManager.getTrend(currentUser, "electricity", "reading");
//            
//            if (electricityTrend != null && !electricityTrend.isEmpty()) {
//                // Check if it matches the expected format: "X.X% from previous month" or "Not enough monthly data"
//                if (electricityTrend.matches("-?\\d+\\.\\d+% from previous month") || 
//                    electricityTrend.equals("Not enough monthly data") ||
//                    electricityTrend.equals("Previous month's reading is 0")) {
//                    printSuccess("Get trend for electricity: " + electricityTrend);
//                    passedTests++;
//                } else {
//                    printFailure("Get trend for electricity - unexpected format: " + electricityTrend);
//                }
//            } else {
//                printFailure("Get trend for electricity - empty or null result");
//            }
//        } catch (Exception e) {
//            printFailure("Get trend for electricity exception: " + e.getMessage());
//        }
//        
//     // Test 11: Overall trend analysis
//        totalTests++;
//        try {
//            String overallTrend = readingManager.getTrend_Overall(currentUser, "reading");
//            
//            if (overallTrend != null && !overallTrend.isEmpty()) {
//                // Check if it matches the expected format: "X.X% from last month" or "No previous data"
//                if (overallTrend.matches("-?\\d+\\.\\d+% from last month") || 
//                    overallTrend.equals("No previous data")) {
//                    printSuccess("Get overall trend: " + overallTrend);
//                    passedTests++;
//                } else {
//                    printFailure("Get overall trend - unexpected format: " + overallTrend);
//                }
//            } else {
//                printFailure("Get overall trend - empty or null result");
//            }
//        } catch (Exception e) {
//            printFailure("Get overall trend exception: " + e.getMessage());
//        }
//        
//        // Test 12: Trend color for UI
//        totalTests++;
//        try {
//            Color electricityTrendColor = readingManager.getTrend_Color(currentUser, "electricity");
//            
//            if (electricityTrendColor != null) {
//                printSuccess("Get trend color for electricity - RGB(" + 
//                        electricityTrendColor.getRed() + "," + 
//                        electricityTrendColor.getGreen() + "," + 
//                        electricityTrendColor.getBlue() + ")");
//                passedTests++;
//            } else {
//                printFailure("Get trend color for electricity - returned null");
//            }
//        } catch (Exception e) {
//            printFailure("Get trend color for electricity exception: " + e.getMessage());
//        }
//        
//        // Test 13: Get reading years
//        totalTests++;
//        try {
//            int[] electricityYears = readingManager.getReading_Years(currentUser, "electricity");
//            
//            if (electricityYears != null && electricityYears.length > 0) {
//                printSuccess("Get reading years for electricity: " + java.util.Arrays.toString(electricityYears));
//                passedTests++;
//            } else {
//                printFailure("Get reading years for electricity - no years returned");
//            }
//        } catch (Exception e) {
//            printFailure("Get reading years for electricity exception: " + e.getMessage());
//        }
//        
//        // Test 14: Get total readings count
//        totalTests++;
//        try {
//            int totalReadingsCount = readingManager.getTotal_Readings(currentUser);
//            
//            if (totalReadingsCount >= 18) { // 6 months × 3 types = 18 readings
//                printSuccess("Get total readings count: " + totalReadingsCount);
//                passedTests++;
//            } else {
//                printFailure("Get total readings count (expected >= 18, got " + totalReadingsCount + ")");
//            }
//        } catch (Exception e) {
//            printFailure("Get total readings count exception: " + e.getMessage());
//        }
//        
//        return new int[] {passedTests, totalTests};
//    }
//    
//    private static int[] testEdgeCases(User_Manager userManager, Reading_Manager readingManager) throws SQLException {
//        System.out.println("Testing edge cases and error handling...");
//        int passedTests = 0;
//        int totalTests = 0;
//        
//        User currentUser = userManager.getCurrentUser();
//        
//        // Test 1: Null user handling
//        totalTests++;
//        try {
//            List<Reading> nullUserReadings = readingManager.getReadings_By_User_Id(null);
//            if (nullUserReadings.isEmpty()) {
//                printSuccess("Null user handling - returns empty list");
//                passedTests++;
//            } else {
//                printFailure("Null user handling - should return empty list");
//            }
//        } catch (Exception e) {
//            // Exception is acceptable for null user
//            printSuccess("Null user handling - throws exception as expected");
//            passedTests++;
//        }
//        
//        // Test 2: Non-existent reading ID
//        totalTests++;
//        try {
//            Reading nonExistentReading = readingManager.getReading_By_Id(999999);
//            if (nonExistentReading == null) {
//                printSuccess("Non-existent reading ID handling");
//                passedTests++;
//            } else {
//                printFailure("Non-existent reading ID handling - should return null");
//            }
//        } catch (Exception e) {
//            printFailure("Non-existent reading ID handling exception: " + e.getMessage());
//        }
//        
//        // Test 3: Empty date range
//        totalTests++;
//        try {
//            LocalDate futureDate = LocalDate.now().plusDays(30);
//            LocalDate pastDate = LocalDate.now().minusDays(30);
//            List<Reading> emptyRangeReadings = readingManager.getReadings_By_Date(currentUser, futureDate, pastDate);
//            
//            if (emptyRangeReadings.isEmpty()) {
//                printSuccess("Empty date range handling");
//                passedTests++;
//            } else {
//                printFailure("Empty date range handling - should return empty list");
//            }
//        } catch (Exception e) {
//            printFailure("Empty date range handling exception: " + e.getMessage());
//        }
//        
//        // Test 4: Invalid utility type
//        totalTests++;
//        try {
//            boolean invalidTypeExists = readingManager.isReading_Exists(currentUser, "invalidtype");
//            if (!invalidTypeExists) {
//                printSuccess("Invalid utility type handling");
//                passedTests++;
//            } else {
//                printFailure("Invalid utility type handling - should return false");
//            }
//        } catch (Exception e) {
//            printFailure("Invalid utility type handling exception: " + e.getMessage());
//        }
//        
//        // Test 5: Duplicate user creation
//        totalTests++;
//        try {
//            String duplicateUsername = "duplicate_" + System.currentTimeMillis();
//            String password = "TestPassword123!";
//            String email = "duplicate" + System.currentTimeMillis() + "@example.com";
//            
//            userManager.addUser(duplicateUsername, password, email);
//            
//            try {
//                userManager.addUser(duplicateUsername, password + "_different", email + "_different");
//                printFailure("Duplicate username handling - should not allow duplicate");
//            } catch (Exception duplicateException) {
//                printSuccess("Duplicate username handling - throws exception as expected");
//                passedTests++;
//            }
//            
//            // Cleanup
//            User duplicateUser = userManager.getUserByUsername(duplicateUsername);
//            if (duplicateUser != null) {
//                userManager.deleteUser(duplicateUser);
//            }
//        } catch (Exception e) {
//            printFailure("Duplicate user creation test exception: " + e.getMessage());
//        }
//        
//        // Test 6: Very large numbers
//        totalTests++;
//        try {
//            Reading largeReading = new Reading();
//            largeReading.setUser_Id(currentUser.getUser_Id());
//            largeReading.setDate(LocalDate.now());
//            largeReading.setType("other");
//            largeReading.setReading(999999999.99);
//            largeReading.setRate(99.99);
//            largeReading.setTotal_Price(largeReading.getReading() * largeReading.getRate());
//            
//            readingManager.addReading(currentUser, largeReading);
//            Reading retrievedLarge = readingManager.getLatest_Reading_By_Type(currentUser, "test_large");
//            
//            if (retrievedLarge != null && Math.abs(retrievedLarge.getReading() - 999999999.99) < 0.01) {
//                printSuccess("Large number handling");
//                passedTests++;
//                readingManager.deleteReading(retrievedLarge); // Cleanup
//            } else {
//                printFailure("Large number handling");
//            }
//        } catch (Exception e) {
//            printFailure("Large number handling exception: " + e.getMessage());
//        }
//        
//        // Test 7: Invalid utility type handling
//        totalTests++;
//        try {
//            Reading invalidTypeReading = new Reading();
//            invalidTypeReading.setUser_Id(currentUser.getUser_Id());
//            invalidTypeReading.setDate(LocalDate.now());
//            invalidTypeReading.setType("invalid_type"); // Not electricity/water/gas
//            invalidTypeReading.setReading(100.0);
//            invalidTypeReading.setRate(1.0);
//            invalidTypeReading.setTotal_Price(100.0);
//            
//            try {
//                readingManager.addReading(currentUser, invalidTypeReading);
//                printFailure("Invalid utility type - should not allow non-standard types");
//            } catch (SQLException e) {
//                // Expected behavior - should reject invalid type
//                printSuccess("Invalid utility type properly rejected");
//                passedTests++;
//            }
//        } catch (Exception e) {
//            printFailure("Invalid utility type test exception: " + e.getMessage());
//        }
//
//        
//        // Test 8: Password validation with invalid credentials
//        totalTests++;
//        try {
//            boolean invalidPasswordMatch = userManager.UsernamePasswordMatch(
//                    currentUser.getUsername(), "WrongPassword123!");
//            if (!invalidPasswordMatch) {
//                printSuccess("Invalid password validation");
//                passedTests++;
//            } else {
//                printFailure("Invalid password validation - should return false");
//            }
//        } catch (Exception e) {
//            printFailure("Invalid password validation exception: " + e.getMessage());
//        }
//        
//        // Test 9: Email validation with invalid credentials
//        totalTests++;
//        try {
//            boolean invalidEmailMatch = userManager.UsernameEmailMatch(
//                    currentUser.getUsername(), "wrong@email.com");
//            if (!invalidEmailMatch) {
//                printSuccess("Invalid email validation");
//                passedTests++;
//            } else {
//                printFailure("Invalid email validation - should return false");
//            }
//        } catch (Exception e) {
//            printFailure("Invalid email validation exception: " + e.getMessage());
//        }
//        
//        // Test 10: Update non-existent reading
//        totalTests++;
//        try {
//            readingManager.updateReading(currentUser, 999999, LocalDate.now(), "test", 100.0, 1.0, 100.0);
//            printFailure("Update non-existent reading - should throw exception");
//        } catch (Exception e) {
//            printSuccess("Update non-existent reading - throws exception as expected");
//            passedTests++;
//        }
//        
//        return new int[] {passedTests, totalTests};
//    }
//    
//    private static int[] testUIIntegration(User_Manager userManager, Reading_Manager readingManager) throws SQLException {
//        System.out.println("Testing UI integration functionality...");
//        int passedTests = 0;
//        int totalTests = 0;
//        
//        User currentUser = userManager.getCurrentUser();
//        
//        // Ensure we have some test data
//        if (readingManager.getReadings_By_User_Id(currentUser).isEmpty()) {
//            // Add some test data
//            readingManager.addReading(currentUser, LocalDate.now().minusDays(30), "electricity", 500.0, 0.15, 75.0);
//            readingManager.addReading(currentUser, LocalDate.now().minusDays(15), "water", 25.0, 62.50);
//            readingManager.addReading(currentUser, LocalDate.now().minusDays(10), "gas", 150.0, 1.25, 187.50);
//        }
//        
//        // Test 1: Update reading label
//        totalTests++;
//        try {
//            Reading testReading = readingManager.getLatest_Reading_By_Type(currentUser, "electricity");
//            if (testReading != null) {
//                JLabel valueLabel = new JLabel();
//                JLabel trendLabel = new JLabel();
//                
//                readingManager.updateReading_Label(currentUser, testReading, valueLabel, trendLabel, "electricity","reading");
//                
//                if (valueLabel.getText() != null && !valueLabel.getText().isEmpty() &&
//                    trendLabel.getText() != null && !trendLabel.getText().isEmpty()) {
//                    printSuccess("Update reading label - Value: '" + valueLabel.getText() + 
//                            "', Trend: '" + trendLabel.getText() + "'");
//                    passedTests++;
//                } else {
//                    printFailure("Update reading label - labels not updated properly");
//                }
//            } else {
//                printFailure("Update reading label - no test reading available");
//            }
//        } catch (Exception e) {
//            printFailure("Update reading label exception: " + e.getMessage());
//        }
//        
//        // Test 2: Get readings as JList
//        totalTests++;
//        try {
//            JPanel testPanel = new JPanel();
//            Database_Manager dbManager = Database_Manager.getInstance();
//            
//            JList<String> readingsList = readingManager.getReadings_As_JList(
//                    testPanel, dbManager, currentUser, "electricity");
//            
//            if (readingsList != null && readingsList.getModel().getSize() > 0) {
//                printSuccess("Get readings as JList - " + readingsList.getModel().getSize() + " items");
//                passedTests++;
//            } else {
//                printFailure("Get readings as JList - no items or null list");
//            }
//        } catch (Exception e) {
//            printFailure("Get readings as JList exception: " + e.getMessage());
//        }
//        
//        // Test 3: UI integration with multiple utility types
//        totalTests++;
//        try {
//            JPanel testPanel = new JPanel();
//            Database_Manager dbManager = Database_Manager.getInstance();
//            
//            String[] utilityTypes = {"electricity", "water", "gas"};
//            int totalListItems = 0;
//            
//            for (String type : utilityTypes) {
//                JList<String> list = readingManager.getReadings_As_JList(testPanel, dbManager, currentUser, type);
//                if (list != null) {
//                    totalListItems += list.getModel().getSize();
//                }
//            }
//            
//            if (totalListItems > 0) {
//                printSuccess("UI integration with multiple utility types - " + totalListItems + " total items");
//                passedTests++;
//            } else {
//                printFailure("UI integration with multiple utility types - no items found");
//            }
//        } catch (Exception e) {
//            printFailure("UI integration with multiple utility types exception: " + e.getMessage());
//        }
//        
//        return new int[] {passedTests, totalTests};
//    }
//    
//    private static void generateTestData(User currentUser, Reading_Manager readingManager) throws SQLException {
//        System.out.println("Generating comprehensive test data...");
//        
//        LocalDate currentDate = LocalDate.now();
//        String[] utilityTypes = {"electricity", "water", "gas"};
//        double[] baseReadings = {500.0, 25.0, 150.0};
//        double[] rates = {0.15, 2.50, 1.25};
//        
//        // Generate 6 months of data
//        for (int month = 0; month < 6; month++) {
//            LocalDate testDate = currentDate.minusMonths(month).withDayOfMonth(1);
//            
//            for (int i = 0; i < utilityTypes.length; i++) {
//                double reading = baseReadings[i] + (month * 10) + (Math.random() * 20);
//                double rate = rates[i];
//                double total = reading * rate;
//                
//                readingManager.addReading(currentUser, testDate, utilityTypes[i], reading, rate, total);
//            }
//        }
//        
//        System.out.println("Generated " + (6 * utilityTypes.length) + " test readings");
//    }
//    
//    private static void cleanupTestData(User_Manager userManager, Reading_Manager readingManager) {
//        System.out.println(ANSI_BLUE + "\nCleaning up test data..." + ANSI_RESET);
//        
//        try {
//            User currentUser = userManager.getCurrentUser();
//            if (currentUser != null) {
//                // Clean up readings
//                List<Reading> allReadings = readingManager.getReadings_By_User_Id(currentUser);
//                for (Reading reading : allReadings) {
//                    readingManager.deleteReading(reading);
//                }
//                System.out.println("Cleaned up " + allReadings.size() + " readings");
//                
//                // Clean up test user (but keep the original user if it wasn't a test user)
//                if (currentUser.getUsername() != null && 
//                    (currentUser.getUsername().startsWith("testuser_") || 
//                     currentUser.getUsername().startsWith("updated_"))) {
//                    userManager.deleteUser(currentUser);
//                    System.out.println("Cleaned up test user: " + currentUser.getUsername());
//                }
//            }
//        } catch (Exception e) {
//            System.err.println(ANSI_RED + "Error during cleanup: " + e.getMessage() + ANSI_RESET);
//        }
//    }
//    
//    private static boolean validateReading(Reading reading, double expectedReading, 
//                                         double expectedRate, double expectedTotal) {
//        if (reading == null) return false;
//        
//        return Math.abs(reading.getReading() - expectedReading) < 0.001 &&
//               Math.abs(reading.getRate() - expectedRate) < 0.001 &&
//               Math.abs(reading.getTotal_Price() - expectedTotal) < 0.001;
//    }
//    
//    private static void debugReading(String label, double reading, double rate, double total) {
//        System.out.println("  " + label + " - Reading: " + reading + ", Rate: " + rate + ", Total: " + total);
//    }
//    
//    private static void printSuccess(String testName) {
//        System.out.println(ANSI_GREEN + "✓ PASS: " + testName + ANSI_RESET);
//    }
//    
//    private static void printFailure(String testName) {
//        System.out.println(ANSI_RED + "✗ FAIL: " + testName + ANSI_RESET);
//    }
}