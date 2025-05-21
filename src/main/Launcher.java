package main;

import java.awt.EventQueue;

import database.Database_Manager;
import model.Reading;
import model.User;
import view.login.*;
import view.Main_Frame;

public class Launcher {
	public static void main(String[] args) {
		// Set Up Needed Database Connections
		Database_Manager database_manager = Database_Manager.getInstance();
		
		User Test_User = new User(1,"Test_User", "Test_Password", "Test_Email");
		User Ren = new User(1,"Ren", "asdasd", "Ren123@gmail.com");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User RenUser = database_manager.getUserManager().getUserById(1);
					// Create and show the main window
//					Main_Frame window = new Main_Frame(database_manager,Test_User);
					Main_Frame window = new Main_Frame(database_manager,Ren);
//					Main_Frame window = new Main_Frame(database_manager,RenUser);
//					Log_In_Window window = new Log_In_Window(database_manager);
					
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
