package database.test;

import java.sql.*;

import database.User_Manager;
import model.User;

public class UserManagerTest {
    
    public static void testUserManager(Connection connection) {
        System.out.println("=== Starting User_Manager Function Tests ===\n");
        
        User_Manager userManager = new User_Manager(connection);
        int testsPassed = 0;
        int totalTests = 0;
        
        // Test 1: Test validEmail function
        totalTests++;
        System.out.println("Test 1: Testing validEmail function");
        try {
            boolean validResult1 = userManager.validEmail("test@example.com");
            boolean validResult2 = userManager.validEmail("user.name+tag@domain.co.uk");
            boolean invalidResult1 = userManager.validEmail("invalid-email");
            boolean invalidResult2 = userManager.validEmail("@domain.com");
            boolean invalidResult3 = userManager.validEmail("user@");
            
            System.out.println("  validEmail('test@example.com') = " + validResult1 + " (should be true)");
            System.out.println("  validEmail('user.name+tag@domain.co.uk') = " + validResult2 + " (should be true)");
            System.out.println("  validEmail('invalid-email') = " + invalidResult1 + " (should be false)");
            System.out.println("  validEmail('@domain.com') = " + invalidResult2 + " (should be false)");
            System.out.println("  validEmail('user@') = " + invalidResult3 + " (should be false)");
            
            if (validResult1 && validResult2 && !invalidResult1 && !invalidResult2 && !invalidResult3) {
                System.out.println("✓ validEmail function works correctly");
                testsPassed++;
            } else {
                System.out.println("✗ validEmail function failed");
                System.out.println("  NOTE: Check if addUser method uses '!validEmail(email)' instead of 'validEmail(email)'");
            }
        } catch (Exception e) {
            System.out.println("✗ validEmail function threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 2: Test setCurrentUser and getCurrentUser
        totalTests++;
        System.out.println("Test 2: Testing setCurrentUser and getCurrentUser");
        try {
            User testUser = new User(999, "testuser", "testpass", "test@test.com");
            userManager.setCurrentUser(testUser);
            User retrievedUser = userManager.getCurrentUser();
            
            if (retrievedUser != null && retrievedUser.getUser_Id() == 999 && 
                "testuser".equals(retrievedUser.getUsername()) &&
                "testpass".equals(retrievedUser.getPassword()) &&
                "test@test.com".equals(retrievedUser.getEmail())) {
                System.out.println("✓ setCurrentUser and getCurrentUser work correctly");
                testsPassed++;
            } else {
                System.out.println("✗ setCurrentUser/getCurrentUser failed");
                if (retrievedUser != null) {
                    System.out.println("  Retrieved user: ID=" + retrievedUser.getUser_Id() + 
                                     ", Username=" + retrievedUser.getUsername());
                } else {
                    System.out.println("  Retrieved user is null");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ setCurrentUser/getCurrentUser threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 3: Test setCurrentUserNull
        totalTests++;
        System.out.println("Test 3: Testing setCurrentUserNull");
        try {
            User testUser = new User(999, "testuser", "testpass", "test@test.com");
            userManager.setCurrentUser(testUser);
            userManager.setCurrentUserNull();
            User retrievedUser = userManager.getCurrentUser();
            
            if (retrievedUser == null) {
                System.out.println("✓ setCurrentUserNull works correctly");
                testsPassed++;
            } else {
                System.out.println("✗ setCurrentUserNull failed - user is not null");
            }
        } catch (Exception e) {
            System.out.println("✗ setCurrentUserNull threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 4: Test addUser with valid data
        totalTests++;
        System.out.println("Test 4: Testing addUser with valid data");
        try {
            String testUsername = "testusertemp" + System.currentTimeMillis();
            String testEmail = "test" + System.currentTimeMillis() + "@example.com";
            
            userManager.addUser(testUsername, "testpassword", testEmail);
            
            // Verify user was added by trying to retrieve it
            User addedUser = userManager.getUserByUsername(testUsername);
            if (addedUser != null && testUsername.equals(addedUser.getUsername()) && 
                testEmail.equals(addedUser.getEmail())) {
                System.out.println("✓ addUser works correctly");
                testsPassed++;
                
                // Clean up - delete the test user
                userManager.deleteUser(addedUser);
            } else {
                System.out.println("✗ addUser failed - user not found after insertion");
            }
        } catch (SQLException e) {
            System.out.println("✗ addUser threw SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ addUser threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 5: Test addUser with duplicate username
        totalTests++;
        System.out.println("Test 5: Testing addUser with duplicate data");
        try {
            String testUsername = "duplicatetest" + System.currentTimeMillis();
            String testEmail = "duplicate" + System.currentTimeMillis() + "@example.com";
            
            // Add user first time
            userManager.addUser(testUsername, "testpassword", testEmail);
            
            // Try to add same user again
            try {
                userManager.addUser(testUsername, "testpassword", testEmail);
                System.out.println("✗ addUser should have thrown SQLException for duplicate user");
            } catch (SQLException e) {
                if ("User Already Exist".equals(e.getMessage())) {
                    System.out.println("✓ addUser correctly prevents duplicate users");
                    testsPassed++;
                } else {
                    System.out.println("✗ addUser threw wrong SQLException message: " + e.getMessage());
                }
            }
            
            // Clean up
            User userToDelete = userManager.getUserByUsername(testUsername);
            if (userToDelete != null) {
                userManager.deleteUser(userToDelete);
            }
        } catch (Exception e) {
            System.out.println("✗ addUser duplicate test threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 6: Test addUser with invalid email
        totalTests++;
        System.out.println("Test 6: Testing addUser with invalid email");
        try {
            String testUsername = "invalidemailtest" + System.currentTimeMillis();
            String invalidEmail = "invalid-email-format" + System.currentTimeMillis();
            
            // First, let's check what validEmail returns for this invalid email
            boolean emailValidResult = userManager.validEmail(invalidEmail);
            System.out.println("  validEmail('" + invalidEmail + "') = " + emailValidResult + " (should be false)");
            
            // Check what checkUserEmail returns
            boolean userCheckResult = userManager.checkUserEmail(testUsername, invalidEmail);
            System.out.println("  checkUserEmail('" + testUsername + "', '" + invalidEmail + "') = " + userCheckResult);
            System.out.println("  NOTE: If checkUserEmail returns true with invalid email, the OR logic in checkUserEmail is wrong");
            
            try {
                userManager.addUser(testUsername, "testpassword", invalidEmail);
                System.out.println("✗ addUser should have thrown SQLException for invalid email");
            } catch (SQLException e) {
                if ("Invalid Email Format".equals(e.getMessage())) {
                    System.out.println("✓ addUser correctly rejects invalid email");
                    testsPassed++;
                } else {
                    System.out.println("✗ addUser threw wrong SQLException message: " + e.getMessage());
                    System.out.println("  This suggests checkUserEmail has wrong logic (OR instead of AND)");
                    System.out.println("  Or validEmail check has wrong logic (missing ! operator)");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ addUser invalid email test threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 7: Test getUserByUsername
        totalTests++;
        System.out.println("Test 7: Testing getUserByUsername");
        try {
            String testUsername = "getbyusernametest" + System.currentTimeMillis();
            String testEmail = "getbyusername" + System.currentTimeMillis() + "@example.com";
            
            // Add a test user
            userManager.addUser(testUsername, "testpassword", testEmail);
            
            // Retrieve by username
            User retrievedUser = userManager.getUserByUsername(testUsername);
            
            if (retrievedUser != null && testUsername.equals(retrievedUser.getUsername()) &&
                testEmail.equals(retrievedUser.getEmail())) {
                System.out.println("✓ getUserByUsername works correctly");
                testsPassed++;
            } else {
                System.out.println("✗ getUserByUsername failed");
            }
            
            // Test non-existent user
            User nonExistentUser = userManager.getUserByUsername("nonexistentuser12345");
            if (nonExistentUser == null) {
                System.out.println("✓ getUserByUsername correctly returns null for non-existent user");
            } else {
                System.out.println("✗ getUserByUsername should return null for non-existent user");
            }
            
            // Clean up
            if (retrievedUser != null) {
                userManager.deleteUser(retrievedUser);
            }
        } catch (Exception e) {
            System.out.println("✗ getUserByUsername threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 8: Test getUserByEmail
        totalTests++;
        System.out.println("Test 8: Testing getUserByEmail");
        try {
            String testUsername = "getbyemailtest" + System.currentTimeMillis();
            String testEmail = "getbyemail" + System.currentTimeMillis() + "@example.com";
            
            // Add a test user
            userManager.addUser(testUsername, "testpassword", testEmail);
            
            // Retrieve by email
            User retrievedUser = userManager.getUserByEmail(testEmail);
            
            if (retrievedUser != null && testUsername.equals(retrievedUser.getUsername()) &&
                testEmail.equals(retrievedUser.getEmail())) {
                System.out.println("✓ getUserByEmail works correctly");
                testsPassed++;
            } else {
                System.out.println("✗ getUserByEmail failed");
            }
            
            // Test non-existent email
            User nonExistentUser = userManager.getUserByEmail("nonexistent@nonexistent.com");
            if (nonExistentUser == null) {
                System.out.println("✓ getUserByEmail correctly returns null for non-existent email");
            } else {
                System.out.println("✗ getUserByEmail should return null for non-existent email");
            }
            
            // Clean up
            if (retrievedUser != null) {
                userManager.deleteUser(retrievedUser);
            }
        } catch (Exception e) {
            System.out.println("✗ getUserByEmail threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 9: Test checkUserEmail
        totalTests++;
        System.out.println("Test 9: Testing checkUserEmail");
        try {
            String testUsername = "checkuseremailtest" + System.currentTimeMillis();
            String testEmail = "checkuseremail" + System.currentTimeMillis() + "@example.com";
            
            // Check non-existent user (should return true)
            boolean availableResult = userManager.checkUserEmail(testUsername, testEmail);
            if (availableResult) {
                System.out.println("✓ checkUserEmail correctly returns true for available username/email");
            } else {
                System.out.println("✗ checkUserEmail should return true for available username/email");
            }
            
            // Add the user
            userManager.addUser(testUsername, "testpassword", testEmail);
            
            // Check existing user with same username and email (should return false)
            boolean existingResult = userManager.checkUserEmail(testUsername, testEmail);
            if (!existingResult) {
                System.out.println("✓ checkUserEmail correctly returns false for existing username/email");
            } else {
                System.out.println("✗ checkUserEmail should return false for existing username/email");
            }
            
            // Check with existing username but different email (should return false)
            String differentEmail = "different" + System.currentTimeMillis() + "@example.com";
            boolean existingUsernameResult = userManager.checkUserEmail(testUsername, differentEmail);
            if (!existingUsernameResult) {
                System.out.println("✓ checkUserEmail correctly returns false for existing username with different email");
            } else {
                System.out.println("✗ checkUserEmail should return false for existing username (current logic may be wrong - check if using OR instead of AND)");
            }
            
            // Check with different username but existing email (should return false)
            String differentUsername = "different" + testUsername;
            boolean existingEmailResult = userManager.checkUserEmail(differentUsername, testEmail);
            if (!existingEmailResult) {
                System.out.println("✓ checkUserEmail correctly returns false for existing email with different username");
                testsPassed++;
            } else {
                System.out.println("✗ checkUserEmail should return false for existing email (current logic may be wrong - check if using OR instead of AND)");
            }
            
            // Clean up
            User userToDelete = userManager.getUserByUsername(testUsername);
            if (userToDelete != null) {
                userManager.deleteUser(userToDelete);
            }
        } catch (Exception e) {
            System.out.println("✗ checkUserEmail threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 10: Test UsernamePasswordMatch
        totalTests++;
        System.out.println("Test 10: Testing UsernamePasswordMatch");
        try {
            String testUsername = "passwordmatchtest" + System.currentTimeMillis();
            String testPassword = "correctpassword";
            String testEmail = "passwordmatch" + System.currentTimeMillis() + "@example.com";
            
            // Add a test user
            userManager.addUser(testUsername, testPassword, testEmail);
            
            // Test correct password
            boolean correctMatch = userManager.UsernamePasswordMatch(testUsername, testPassword);
            if (correctMatch) {
                System.out.println("✓ UsernamePasswordMatch correctly returns true for correct credentials");
            } else {
                System.out.println("✗ UsernamePasswordMatch should return true for correct credentials");
            }
            
            // Test incorrect password
            boolean incorrectMatch = userManager.UsernamePasswordMatch(testUsername, "wrongpassword");
            if (!incorrectMatch) {
                System.out.println("✓ UsernamePasswordMatch correctly returns false for incorrect credentials");
                testsPassed++;
            } else {
                System.out.println("✗ UsernamePasswordMatch should return false for incorrect credentials");
            }
            
            // Clean up
            User userToDelete = userManager.getUserByUsername(testUsername);
            if (userToDelete != null) {
                userManager.deleteUser(userToDelete);
            }
        } catch (Exception e) {
            System.out.println("✗ UsernamePasswordMatch threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 11: Test getUserById
        totalTests++;
        System.out.println("Test 11: Testing getUserById");
        try {
            String testUsername = "getbyidtest" + System.currentTimeMillis();
            String testEmail = "getbyid" + System.currentTimeMillis() + "@example.com";
            
            // Add a test user
            userManager.addUser(testUsername, "testpassword", testEmail);
            
            // Get the user to find their ID
            User addedUser = userManager.getUserByUsername(testUsername);
            if (addedUser != null) {
                int userId = addedUser.getUser_Id();
                
                // Retrieve by ID
                User retrievedUser = userManager.getUserById(userId);
                
                if (retrievedUser != null && userId == retrievedUser.getUser_Id() &&
                    testUsername.equals(retrievedUser.getUsername())) {
                    System.out.println("✓ getUserById works correctly");
                    testsPassed++;
                } else {
                    System.out.println("✗ getUserById failed");
                }
                
                // Clean up
                userManager.deleteUser(addedUser);
            } else {
                System.out.println("✗ Could not add test user for getUserById test");
            }
            
            // Test non-existent ID
            User nonExistentUser = userManager.getUserById(999999);
            if (nonExistentUser == null) {
                System.out.println("✓ getUserById correctly returns null for non-existent ID");
            } else {
                System.out.println("✗ getUserById should return null for non-existent ID");
            }
        } catch (Exception e) {
            System.out.println("✗ getUserById threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 12: Test updateUser
        totalTests++;
        System.out.println("Test 12: Testing updateUser");
        try {
            String testUsername = "updatetest" + System.currentTimeMillis();
            String testEmail = "update" + System.currentTimeMillis() + "@example.com";
            
            // Add a test user
            userManager.addUser(testUsername, "originalpassword", testEmail);
            User originalUser = userManager.getUserByUsername(testUsername);
            
            if (originalUser != null) {
                // Update the user
                String newUsername = "updated" + testUsername;
                String newPassword = "updatedpassword";
                String newEmail = "updated" + testEmail;
                
                userManager.updateUser(originalUser, newUsername, newPassword, newEmail);
                
                // Retrieve updated user by ID
                User updatedUser = userManager.getUserById(originalUser.getUser_Id());
                
                if (updatedUser != null && newUsername.equals(updatedUser.getUsername()) &&
                    newPassword.equals(updatedUser.getPassword()) && 
                    newEmail.equals(updatedUser.getEmail())) {
                    System.out.println("✓ updateUser works correctly");
                    testsPassed++;
                } else {
                    System.out.println("✗ updateUser failed");
                    if (updatedUser != null) {
                        System.out.println("  Expected: " + newUsername + ", " + newPassword + ", " + newEmail);
                        System.out.println("  Got: " + updatedUser.getUsername() + ", " + 
                                         updatedUser.getPassword() + ", " + updatedUser.getEmail());
                    }
                }
                
                // Clean up
                userManager.deleteUser(updatedUser != null ? updatedUser : originalUser);
            } else {
                System.out.println("✗ Could not add test user for updateUser test");
            }
        } catch (Exception e) {
            System.out.println("✗ updateUser threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 13: Test deleteUser
        totalTests++;
        System.out.println("Test 13: Testing deleteUser");
        try {
            String testUsername = "deletetest" + System.currentTimeMillis();
            String testEmail = "delete" + System.currentTimeMillis() + "@example.com";
            
            // Add a test user
            userManager.addUser(testUsername, "testpassword", testEmail);
            User userToDelete = userManager.getUserByUsername(testUsername);
            
            if (userToDelete != null) {
                int userId = userToDelete.getUser_Id();
                
                // Delete the user
                userManager.deleteUser(userToDelete);
                
                // Try to retrieve the deleted user
                User deletedUser = userManager.getUserById(userId);
                
                if (deletedUser == null) {
                    System.out.println("✓ deleteUser works correctly");
                    testsPassed++;
                } else {
                    System.out.println("✗ deleteUser failed - user still exists");
                }
            } else {
                System.out.println("✗ Could not add test user for deleteUser test");
            }
        } catch (Exception e) {
            System.out.println("✗ deleteUser threw exception: " + e.getMessage());
        }
        System.out.println();
        
        // Print summary
        System.out.println("=== Test Summary ===");
        System.out.println("Tests passed: " + testsPassed + "/" + totalTests);
        System.out.println("Success rate: " + String.format("%.1f", (testsPassed * 100.0 / totalTests)) + "%");
        
        if (testsPassed == totalTests) {
            System.out.println("🎉 All tests passed! User_Manager is working correctly.");
        } else {
            System.out.println("⚠️  Some tests failed. Please review the User_Manager implementation.");
        }
    }
}