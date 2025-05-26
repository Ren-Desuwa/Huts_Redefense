package database;

import java.sql.*;

public class Database_Manager {
	private Connection connection;
	private static Database_Manager instance;
	private static final String DataBase_URL = "jdbc:sqlite:database/Data.db";
	
	// Specialized managers
	private User_Manager userManager;
	private Reading_Manager readingManager;

	public static Database_Manager getInstance() {
		if (instance == null) {
			instance = new Database_Manager();
		}
		return instance;
	}
	
	private Database_Manager() {
		try {
			// Load the SQLite JDBC driver
			Class.forName("org.sqlite.JDBC");
			
			// Create connection
			connection = DriverManager.getConnection(DataBase_URL);
			
			try (Statement stmt = connection.createStatement()) {
			    stmt.execute("PRAGMA foreign_keys = ON;");
			}
			
			// Initialize managers
			initializeManagers();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}	
	
	
	private void initializeManagers() {
		this.userManager = new User_Manager(connection);
		this.readingManager = new Reading_Manager(connection);
	}
	
	public User_Manager getUserManager() {
		return userManager;
	}
	public Reading_Manager getReadingManager() {
		return readingManager;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
/*
 * File: Database_Manager.java
 *
 * Description:
 * This file defines the `Database_Manager` class, which is responsible for managing the application's database connection and providing access to specialized managers for user and reading operations. It implements the Singleton design pattern to ensure only one instance of the database manager exists throughout the application.
 *
 * Variables:
 * - `connection` (Connection): Represents the active connection to the SQLite database.
 * - `instance` (Database_Manager): A static instance of the `Database_Manager` class to enforce the Singleton pattern.
 * - `DataBase_URL` (String): The URL of the SQLite database file.
 * - `userManager` (User_Manager): A specialized manager for handling user-related database operations.
 * - `readingManager` (Reading_Manager): A specialized manager for handling reading-related database operations.
 *
 * Constructors:
 * 1. `Database_Manager()`:
 *    - Private constructor to initialize the database connection and specialized managers.
 *    - Loads the SQLite JDBC driver and establishes a connection to the database.
 *    - Enables foreign key constraints in the SQLite database.
 *    - Initializes the `User_Manager` and `Reading_Manager` instances.
 *
 * Methods:
 * 1. `getInstance()`:
 *    - Returns the Singleton instance of the `Database_Manager` class.
 *    - Creates the instance if it does not already exist.
 *
 * 2. `initializeManagers()`:
 *    - Initializes the `User_Manager` and `Reading_Manager` instances using the active database connection.
 *
 * 3. `getUserManager()`:
 *    - Returns the `User_Manager` instance for user-related database operations.
 *
 * 4. `getReadingManager()`:
 *    - Returns the `Reading_Manager` instance for reading-related database operations.
 *
 * 5. `getConnection()`:
 *    - Returns the active database connection.
 *
 * 6. `closeConnection()`:
 *    - Closes the active database connection to release resources.
 *    - Handles any `SQLException` that may occur during the closure.
 *
 * Usage:
 * The `Database_Manager` class is used to manage the application's database connection and provide access to specialized managers for user and reading operations. It ensures that the database connection is properly initialized and closed when no longer needed. This class is essential for all database-related functionality in the application.
 */
