package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.User;

import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JButton;

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
		setPreferredSize(new Dimension(1000, 725));
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(460, 410, 89, 23);
		add(btnNewButton);
		

	}
}
