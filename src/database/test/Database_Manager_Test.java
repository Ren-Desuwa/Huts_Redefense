package database.test;

import database.Database_Manager;

public class Database_Manager_Test {
	public static void main(String[] args) {
		Database_Manager database_manager = Database_Manager.getInstance();
		UserManagerTest.testUserManager(database_manager.getConnection());
		ReadingManagerTest.testReadingManager(database_manager.getConnection());
	}
}