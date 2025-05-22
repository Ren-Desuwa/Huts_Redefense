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
