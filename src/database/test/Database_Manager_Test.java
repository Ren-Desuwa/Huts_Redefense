package database.test;

import database.Database_Manager;

public class Database_Manager_Test {
	public static void main(String[] args) {
		Database_Manager database_manager = Database_Manager.getInstance();
		User_Manager_Test.testUserManager(database_manager.getConnection());
		Reading_Manager_Test.testReadingManager(database_manager.getConnection());
	}
}