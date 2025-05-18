package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.User;

import javax.swing.JLabel;

public class Home_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private User current_user;

	/**
	 * Create the panel.
	 */
	public Home_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		setLayout(null);
		

	}
}
