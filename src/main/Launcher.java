package main;

import java.awt.EventQueue;

import database.Database_Manager;
import model.User;
import view.login.*;
import view.Main_Frame;

public class Launcher {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Launch the application
		
		// Set Up Needed Database Connections
		Database_Manager database_manager = Database_Manager.getInstance();
		
		User Test_User = new User(1,"Test_User", "Test_Password", "Test_Email");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Create and show the main window
//					Main_Frame window = new Main_Frame(database_manager,Test_User);
					Log_In_Window window = new Log_In_Window(database_manager);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
