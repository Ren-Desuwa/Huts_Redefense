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
