package main;

import java.awt.EventQueue;
import database.Database_Manager;
import view.login.*;

public class Launcher {
	public static void main(String[] args) {
		// Set Up Needed Database Connections
		Database_Manager database_manager = Database_Manager.getInstance();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Log_In_Window window = new Log_In_Window(database_manager);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

/*
 * File: Launcher.java
 *
 * Description:
 * This file contains the `Launcher` class, which serves as the entry point for the application. 
 * It initializes the necessary database connections and launches the login window to start the application.
 * The `Launcher` class ensures that the application is properly set up before any user interaction occurs.
 *
 * Variables:
 * - `database_manager` (Database_Manager): A singleton instance of the `Database_Manager` class, responsible for managing database connections and operations.
 *
 * Functions:
 * 
 * 1. `main(String[] args)`:
 *    - The main method serves as the entry point for the application.
 *    - Key steps:
 *      - Initializes the `Database_Manager` instance to set up database connections.
 *      - Uses the `EventQueue.invokeLater` method to ensure that the GUI is created and updated on the Event Dispatch Thread (EDT).
 *      - Creates and displays the `Log_In_Window` to allow the user to log in to the application.
 *      - Catches and handles any exceptions that occur during the initialization or GUI creation process.
 *
 * Usage:
 * The `Launcher` class is used to start the application. It ensures that the database is ready and the login window is displayed to the user.
 * This class is the first point of execution when the application is run.
 */
