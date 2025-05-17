package main;

import java.awt.EventQueue;
import view.login.Login_Window;

public class Launcher {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Launch the application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Create and show the main window
					Login_Window window = new Login_Window();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
