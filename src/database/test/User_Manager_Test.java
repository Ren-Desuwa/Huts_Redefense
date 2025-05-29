package database.test;

import java.sql.SQLException;

import database.User_Manager;
import model.User;

public class User_Manager_Test {

    private static User_Manager userManager;
    private static User test_user;
    private static int test_count = 0;
    private static int passed_count = 0;

    public static void testUserManager(User_Manager user_manager) {
        userManager = user_manager;

        System.out.println("==========================================");
        System.out.println("         USER MANAGER UNIT TESTS          ");
        System.out.println("==========================================");

        // Setup test user
        setupTestUser();

        if (test_user == null) {
            System.out.println("✗ FAIL: Could not create test user. Aborting tests.");
            return;
        }

        // Run all tests
        testValidEmail();
        testSetAndGetCurrentUser();
        testSetCurrentUserNull();
        testAddUser();
        testAddUserDuplicate();
        testAddUserInvalidEmail();
        testGetUserByUsername();
        testGetUserByEmail();
        testCheckUserEmail();
        testUsernamePasswordMatch();
        testGetUserById();
        testUpdateUser();
        testDeleteUser();

        // Clean up test data
        cleanupTestData();

        // Print results
        printTestResults();
    }

    private static void setupTestUser() {
        try {
            String testUsername = "test_user_manager";
            String testEmail = "test_user_manager@example.com";
            userManager.addUser(testUsername, "testpassword", testEmail);
            test_user = userManager.getUserByUsername(testUsername);
        } catch (SQLException e) {
            System.out.println("Error setting up test user: " + e.getMessage());
        }
    }

    private static void cleanupTestData() {
        try {
            if (test_user != null) {
                userManager.deleteUser(test_user);
            }
        } catch (SQLException e) {
            System.out.println("Error cleaning up test data: " + e.getMessage());
        }
    }

    private static void assertEqual(Object expected, Object actual, String testName) {
        test_count++;
        if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
            System.out.println("✓ PASS: " + testName);
            passed_count++;
        } else {
            System.out.println("✗ FAIL: " + testName + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(boolean condition, String testName) {
        test_count++;
        if (condition) {
            System.out.println("✓ PASS: " + testName);
            passed_count++;
        } else {
            System.out.println("✗ FAIL: " + testName);
        }
    }

    private static void assertNotNull(Object obj, String testName) {
        test_count++;
        if (obj != null) {
            System.out.println("✓ PASS: " + testName);
            passed_count++;
        } else {
            System.out.println("✗ FAIL: " + testName + " - Object is null");
        }
    }

    private static void testValidEmail() {
        System.out.println("\n=== Testing validEmail() ===");
        assertTrue(userManager.validEmail("test@example.com"), "Valid email should return true");
        assertTrue(!userManager.validEmail("invalid-email"), "Invalid email should return false");
    }

    private static void testSetAndGetCurrentUser() {
        System.out.println("\n=== Testing setCurrentUser() and getCurrentUser() ===");
        userManager.setCurrentUser(test_user);
        assertEqual(test_user, userManager.getCurrentUser(), "Current user should match the set user");
    }

    private static void testSetCurrentUserNull() {
        System.out.println("\n=== Testing setCurrentUserNull() ===");
        userManager.setCurrentUserNull();
        assertTrue(userManager.getCurrentUser() == null, "Current user should be null after setCurrentUserNull()");
    }

    private static void testAddUser() {
        System.out.println("\n=== Testing addUser() ===");
        try {
            String username = "new_user";
            String email = "new_user@example.com";
            userManager.addUser(username, "password", email);
            User addedUser = userManager.getUserByUsername(username);
            assertNotNull(addedUser, "User should be added successfully");
            userManager.deleteUser(addedUser); // Cleanup
        } catch (SQLException e) {
            System.out.println("✗ FAIL: addUser() - SQL Exception: " + e.getMessage());
        }
    }

    private static void testAddUserDuplicate() {
        System.out.println("\n=== Testing addUser() with duplicate data ===");
        try {
            userManager.addUser(test_user.getUsername(), "password", test_user.getEmail());
            System.out.println("✗ FAIL: addUser() should throw exception for duplicate user");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("User Already Exist"), "Duplicate user should not be allowed");
        }
    }

    private static void testAddUserInvalidEmail() {
        System.out.println("\n=== Testing addUser() with invalid email ===");
        try {
            userManager.addUser("invalid_email_user", "password", "invalid-email");
            System.out.println("✗ FAIL: addUser() should throw exception for invalid email");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Invalid Email Format"), "Invalid email should not be allowed");
        }
    }

    private static void testGetUserByUsername() {
        System.out.println("\n=== Testing getUserByUsername() ===");
        User retrievedUser = userManager.getUserByUsername(test_user.getUsername());
        database.test.Viewer.print(retrievedUser);
        database.test.Viewer.print(test_user);

        if (retrievedUser != null) {
            assertEqual(test_user.getUser_Id(), retrievedUser.getUser_Id(), "User ID should match");
            assertEqual(test_user.getUsername(), retrievedUser.getUsername(), "Username should match");
            assertEqual(test_user.getPassword(), retrievedUser.getPassword(), "Password should match");
            assertEqual(test_user.getEmail(), retrievedUser.getEmail(), "Email should match");
        } else {
            System.out.println("✗ FAIL: Retrieved user is null");
        }
    }


    private static void testGetUserByEmail() {
        System.out.println("\n=== Testing getUserByEmail() ===");
        User retrievedUser = userManager.getUserByEmail(test_user.getEmail());
        database.test.Viewer.print(retrievedUser);
        database.test.Viewer.print(test_user);

        if (retrievedUser != null) {
            assertEqual(test_user.getUser_Id(), retrievedUser.getUser_Id(), "User ID should match");
            assertEqual(test_user.getUsername(), retrievedUser.getUsername(), "Username should match");
            assertEqual(test_user.getPassword(), retrievedUser.getPassword(), "Password should match");
            assertEqual(test_user.getEmail(), retrievedUser.getEmail(), "Email should match");
        } else {
            System.out.println("✗ FAIL: Retrieved user is null");
        }
    }

    private static void testCheckUserEmail() {
        System.out.println("\n=== Testing checkUserEmail() ===");
        assertTrue(userManager.checkUserEmail(test_user.getUsername(), test_user.getEmail()), "Existing user should return true");
        assertTrue(!userManager.checkUserEmail("nonexistent", "nonexistent@example.com"), "Nonexistent user should return false");
    }

    private static void testUsernamePasswordMatch() {
        System.out.println("\n=== Testing UsernamePasswordMatch() ===");
        assertTrue(userManager.UsernamePasswordMatch(test_user.getUsername(), "testpassword"), "Correct credentials should return true");
        assertTrue(!userManager.UsernamePasswordMatch(test_user.getUsername(), "wrongpassword"), "Incorrect credentials should return false");
    }

    private static void testGetUserById() {
        System.out.println("\n=== Testing getUserById() ===");
        User retrievedUser = userManager.getUserById(test_user.getUser_Id());
        database.test.Viewer.print(retrievedUser);
        database.test.Viewer.print(test_user);

        if (retrievedUser != null) {
            assertEqual(test_user.getUser_Id(), retrievedUser.getUser_Id(), "User ID should match");
            assertEqual(test_user.getUsername(), retrievedUser.getUsername(), "Username should match");
            assertEqual(test_user.getPassword(), retrievedUser.getPassword(), "Password should match");
            assertEqual(test_user.getEmail(), retrievedUser.getEmail(), "Email should match");
        } else {
            System.out.println("✗ FAIL: Retrieved user is null");
        }
    }

    private static void testUpdateUser() {
        System.out.println("\n=== Testing updateUser() ===");
        try {
            String newUsername = "updated_user";
            String newEmail = "updated_user@example.com";
            userManager.updateUser(test_user, newUsername, "newpassword", newEmail);
            User updatedUser = userManager.getUserById(test_user.getUser_Id());
            assertEqual(newUsername, updatedUser.getUsername(), "Username should be updated");
            assertEqual(newEmail, updatedUser.getEmail(), "Email should be updated");
            userManager.updateUser(updatedUser, test_user.getUsername(), "testpassword", test_user.getEmail()); // Revert changes
        } catch (SQLException e) {
            System.out.println("✗ FAIL: updateUser() - SQL Exception: " + e.getMessage());
        }
    }

    private static void testDeleteUser() {
        System.out.println("\n=== Testing deleteUser() ===");
        try {
            String username = "delete_user";
            String email = "delete_user@example.com";
            userManager.addUser(username, "password", email);
            User userToDelete = userManager.getUserByUsername(username);
            userManager.deleteUser(userToDelete);
            assertTrue(userManager.getUserByUsername(username) == null, "User should be deleted successfully");
        } catch (SQLException e) {
            System.out.println("✗ FAIL: deleteUser() - SQL Exception: " + e.getMessage());
        }
    }

    private static void printTestResults() {
        System.out.println("\n==========================================");
        System.out.println("              TEST RESULTS                ");
        System.out.println("==========================================");
        System.out.println("Total Tests: " + test_count);
        System.out.println("Passed: " + passed_count);
        System.out.println("Failed: " + (test_count - passed_count));
        System.out.println("Success Rate: " + String.format("%.1f", (double) passed_count / test_count * 100) + "%");

        if (passed_count == test_count) {
            System.out.println("\n🎉 ALL USER MANAGER TESTS PASSED! 🎉");
        } else {
            System.out.println("\n⚠️  Some User Manager tests failed. Please review the output above.");
        }
    }
}
