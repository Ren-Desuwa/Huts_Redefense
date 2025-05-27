package database;

import java.sql.*;

import model.User;

public class User_Manager {
	
	private Connection connection;
	
	private User currentUser;
	
	public User_Manager(Connection connection) {
		this.connection = connection;
	}
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUserNull() {
		this.currentUser = null;
	}
	
	// Adds a new user to the database, checking for existing username or email
	public void addUser(String username, String password, String email) throws SQLException {
		if (checkUserEmail(username, email)) {
			throw new SQLException("User Already Exist");
		}
		if (!validEmail(email)) {
			throw new SQLException("Invalid Email Format");
		}
		String sqlscript = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript, Statement.RETURN_GENERATED_KEYS)) {
			prepared_statement.setString(1, username);
			prepared_statement.setString(2, password);
			prepared_statement.setString(3, email);
			prepared_statement.executeUpdate();
			
			// Get the generated ID
			try (ResultSet resultset = prepared_statement.getGeneratedKeys()) { 
				if (resultset.next()) {
					int id = resultset.getInt(1);
					System.out.println("Inserted user with ID: " + id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	// Updates an existing user in the database
	public void updateUser(User user, String username, String password, String email) throws SQLException {
		String sqlscript = "UPDATE users SET username = ?, password = ?, email = ? WHERE user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, username);
			prepared_statement.setString(2, password);
			prepared_statement.setString(3, email);
			prepared_statement.setInt(4, user.getUser_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	// Deletes a user from the database
	public void deleteUser(User user) throws SQLException {
		String sqlscript = "DELETE FROM users WHERE user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, user.getUser_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	// Retrieves a user by their username, using COLLATE BINARY for case-sensitive comparison
	public User getUserByUsername(String username) {
		// Using GLOB operator for case-sensitive comparison
		String sqlscript = "SELECT * FROM users WHERE username = ? COLLATE BINARY";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, username);
			ResultSet resultSet = prepared_statement.executeQuery();
			if (resultSet.next()) {
				return new User(
					resultSet.getInt("user_id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("email")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	// Retrieves a user by their email address, using COLLATE NOCASE for case-insensitive comparison
	public User getUserByEmail(String email) {
		String sqlscript = "SELECT * FROM users WHERE email = ? COLLATE NOCASE";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, email);
			ResultSet resultSet = prepared_statement.executeQuery();
			if (resultSet.next()) {
				return new User(
					resultSet.getInt("user_id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("email")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	// Checks if the provided username or email already exists in the database
	public boolean checkUserEmail(String username, String email) {
	    if (getUserByUsername(username) != null || getUserByEmail(email) != null) {
	        return true;
	    }   
	    return false;
	}
	
	// Checks if the provided username and password match a user in the database
	public boolean UsernamePasswordMatch(String username, String password) {
		String sqlscript = "SELECT * FROM users WHERE username = ? COLLATE BINARY AND password = ? COLLATE BINARY";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, username);
			prepared_statement.setString(2, password);
			ResultSet resultSet = prepared_statement.executeQuery();
			return resultSet.next(); // If a row is returned, the username and password match // true else false
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}
	
	// Retrieves a user by their ID
	public User getUserById(int userId) throws SQLException {
		String sqlscript = "SELECT * FROM users WHERE user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, userId);
			ResultSet resultSet = prepared_statement.executeQuery();
			if (resultSet.next()) {
				return new User(
					resultSet.getInt("user_id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("email")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public boolean validEmail(String email) {
		// Regex to validate a standard email address:
		// ^                 - Start of the string
		// [A-Za-z0-9+_.-]+  - One or more characters that are letters, digits, plus (+), underscore (_), dot (.), or hyphen (-)
		// @                 - Exactly one @ symbol
		// [A-Za-z0-9.-]+    - One or more characters that are letters, digits, dot (.), or hyphen (-) — the domain part
		// $                 - End of the string
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		
		// Returns true if the email string matches the pattern, false otherwise
	    return email.matches(emailRegex);
	}
}
/*
 * File: User_Manager.java
 *
 * Description:
 * This file defines the `User_Manager` class, which is responsible for managing user-related database operations. 
 * It provides methods for adding, updating, retrieving, and validating user information in the database. 
 * The class interacts with an SQLite database through a `Connection` object and ensures that user data is handled securely and efficiently.
 *
 * Variables:
 * - `connection` (Connection): Represents the active connection to the SQLite database.
 * - `currentUser` (User): Stores the currently logged-in user for session management.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `User_Manager(Connection connection)`:
 *      - Initializes the `User_Manager` with the provided database connection.
 *      - Ensures that all database operations are performed using this connection.
 *
 * 2. **setCurrentUser(User user)**:
 *    - Sets the `currentUser` variable to the provided `User` object.
 *    - Used to manage the session of the currently logged-in user.
 *
 * 3. **getCurrentUser()**:
 *    - Returns the `currentUser` object.
 *    - Allows other components to access the details of the currently logged-in user.
 *
 * 4. **setCurrentUserNull()**:
 *    - Sets the `currentUser` variable to `null`.
 *    - Used to clear the session when the user logs out.
 *
 * 5. **addUser(String username, String password, String email)**:
 *    - Adds a new user to the database.
 *    - Core Mechanics:
 *      - Checks if the username or email already exists using `checkUserEmail()`.
 *      - If the user does not exist, inserts the new user into the database.
 *      - Uses a prepared statement to prevent SQL injection.
 *      - Retrieves and prints the generated user ID after insertion.
 *    - Throws a `SQLException` if the user already exists or if there is a database error.
 *
 * 6. **updateUser(User user, String username, String password, String email)**:
 *    - Updates the details of an existing user in the database.
 *    - Core Mechanics:
 *      - Uses a prepared statement to update the username, password, and email of the user identified by their `user_id`.
 *      - Ensures that the changes are applied only to the specified user.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 7. **getUserByUsername(String username)**:
 *    - Retrieves a user from the database by their username.
 *    - Core Mechanics:
 *      - Uses a case-sensitive query with `COLLATE BINARY` to match the username.
 *      - Returns a `User` object if a match is found, or `null` if no match exists.
 *    - Handles any `SQLException` that occurs during the query.
 *
 * 8. **getUserByEmail(String email)**:
 *    - Retrieves a user from the database by their email address.
 *    - Core Mechanics:
 *      - Uses a case-insensitive query with `COLLATE NOCASE` to match the email.
 *      - Returns a `User` object if a match is found, or `null` if no match exists.
 *    - Handles any `SQLException` that occurs during the query.
 *
 * 9. **checkUserEmail(String username, String email)**:
 *    - Checks if a user with the given username or email already exists in the database.
 *    - Core Mechanics:
 *      - Calls `getUserByUsername()` and `getUserByEmail()` to check for existing records.
 *      - Returns `true` if either the username or email exists, otherwise `false`.
 *
 * 10. **UsernamePasswordMatch(String username, String password)**:
 *     - Verifies if the provided username and password match a user in the database.
 *     - Core Mechanics:
 *       - Uses a case-sensitive query with `COLLATE BINARY` to match both the username and password.
 *       - Returns `true` if a match is found, otherwise `false`.
 *     - Handles any `SQLException` that occurs during the query.
 *
 * 11. **getUserById(int userId)**:
 *     - Retrieves a user from the database by their unique ID.
 *     - Core Mechanics:
 *       - Queries the database for a user with the specified `user_id`.
 *       - Returns a `User` object if a match is found, or `null` if no match exists.
 *     - Handles any `SQLException` that occurs during the query.
 *
 * 12. **validEmail(String email)**:
 *     - Validates the format of an email address.
 *     - Core Mechanics:
 *       - Uses a regular expression to check if the email matches the standard format.
 *       - Returns `true` if the email is valid, otherwise `false`.
 *
 * Usage:
 * The `User_Manager` class is used to handle all user-related database operations, including adding new users, updating user details, validating login credentials, and retrieving user information. It ensures that user data is securely managed and provides utility methods for common operations like email validation and session management.
 */
