package main;

import java.awt.EventQueue;

import database.Database_Manager;
import view.login.Log_In_Window;

public class Launcher {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Launch the application
		
		// Set Up Needed Database Connections
		Database_Manager database_manager = Database_Manager.getInstance();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Create and show the main window
					Log_In_Window window = new Log_In_Window(database_manager);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
