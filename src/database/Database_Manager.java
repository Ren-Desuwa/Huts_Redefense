package database;

public class Database_Manager {
	private static Database_Manager instance;
	
	private Database_Manager() {
		// Initialize the database connection here
	}
	
	public static Database_Manager getInstance() {
		if (instance == null) {
			instance = new Database_Manager();
		}
		return instance;
	}
}
