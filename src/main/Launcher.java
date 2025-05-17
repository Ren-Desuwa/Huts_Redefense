package main;

import java.awt.EventQueue;
import view.login.Sign_In_Window;

public class Launcher {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Launch the application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Create and show the main window
					Sign_In_Window window = new Sign_In_Window();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
