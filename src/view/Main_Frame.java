package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import database.Database_Manager;
import model.*;
import view.panel.*;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Home_Panel home_panel;
	private Database_Manager database_manager;
	private JPanel content_pane;
	private User current_user;
	private JLabel lblNewLabel;
	
	public Main_Frame(Database_Manager database_manager, User user) {
		this.database_manager = database_manager;
		this.current_user = user;
		this.home_panel = new Home_Panel();
		
		content_pane = new JPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1200, 725);

		setContentPane(content_pane);
		content_pane.setLayout(null);
		
		lblNewLabel = new JLabel();
		// Size label at the bottom
        updateSizeLabel(); // Set initial size
        // Listen for window resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateSizeLabel();
            }
        });
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(23, 24, 403, 199);
		content_pane.add(lblNewLabel);
	}
	
	private void updateSizeLabel() {
        Dimension size = getSize();
        lblNewLabel.setText("Width: " + size.width + "  Height: " + size.height);
    }
}
