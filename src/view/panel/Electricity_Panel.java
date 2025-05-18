package view.panel;

import java.awt.Dimension;

import javax.swing.JPanel;

import database.Database_Manager;
import model.User;

public class Electricity_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private User current_user;

	/**
	 * Create the panel.
	 */
	public Electricity_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
	}

}
