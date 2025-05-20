package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import database.Database_Manager;
import model.*;
import view.panel.*;
import view.login.Log_In_Window;



public class Main_Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;	
	private User current_user;
	
	private JPanel content_pane;
	private JPanel side_panel;
	private JPanel card_panel;
	private CardLayout card_layout;
	
	private Home_Panel home_panel;
	private Electricity_Panel electricity_panel;
	private Water_Panel water_panel;
	private Gas_Panel gas_panel;
	private Profile_Panel profile_panel;
	
	private static final String HOME_PANEL = "Home";
	private static final String ELECTRICITY_PANEL = "Electricity";
	private static final String WATER_PANEL = "Water";
	private static final String GAS_PANEL = "Gas";
	private static final String PROFILE_PANEL = "Profile";
	
	private JButton side_home_button;
	private JButton side_electricity_button;
	private JButton side_water_button;
	private JButton side_gas_button;
	private JButton side_profile_button;
	private JButton side_logout_button;
	
	//dev stuff
	private Development_Panel development_panel;
	private JButton side_dev_button;
	private static final String DEV_PANEL = "Development";
	
	
	public Main_Frame(Database_Manager database_manager, User user) {
		this.database_manager = database_manager;
		this.current_user = user;
		
		initializeFrame();
		createPanels();
        initializeComponents();
        addComponentsToFrame();
        setupActionListeners();
		
		content_pane.add(side_panel, BorderLayout.WEST);
		content_pane.add(card_panel, BorderLayout.CENTER);
		
	}
	
	private void initializeFrame() {
		setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 1200, 725);
        setResizable(false);
        content_pane = new JPanel();
        content_pane.setLayout(new BorderLayout(0, 0));
        setContentPane(content_pane);
    }
	
	private void createPanels() {
		side_panel = new JPanel();
		side_panel.setPreferredSize(new Dimension(200, getHeight()));
        side_panel.setLayout(new BoxLayout(side_panel, BoxLayout.Y_AXIS));
        
        card_layout = new CardLayout();
        card_panel = new JPanel();
        card_panel.setLayout(card_layout);
        
        home_panel = new Home_Panel(database_manager, current_user);
        electricity_panel = new Electricity_Panel(database_manager, current_user, home_panel);
        water_panel = new Water_Panel(database_manager, current_user, home_panel);
        gas_panel = new Gas_Panel(database_manager, current_user, home_panel);
        profile_panel = new Profile_Panel(database_manager, current_user);
        
        card_panel.add(home_panel, HOME_PANEL);
        card_panel.add(electricity_panel, ELECTRICITY_PANEL);
        card_panel.add(water_panel, WATER_PANEL);
        card_panel.add(gas_panel, GAS_PANEL);
        card_panel.add(profile_panel, PROFILE_PANEL);
        
        // dev panel
        
        development_panel = new Development_Panel(database_manager, current_user);
        card_panel.add(development_panel, DEV_PANEL);
        
	}
	
	private void initializeComponents() {
		side_home_button = new JButton("Home");
		side_electricity_button = new JButton("Electricity");
		side_water_button = new JButton("Water");
		side_gas_button = new JButton("Gas");
		side_profile_button = new JButton("Profile");
		side_logout_button = new JButton("Logout");
		
		side_home_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		side_home_button.setMaximumSize(new Dimension(180, 40));
		side_home_button.setBackground(new Color(70, 70, 70));
		side_home_button.setForeground(Color.WHITE);
		side_home_button.setFocusPainted(false);
		side_home_button.setBorderPainted(false);
		side_home_button.setFont(new Font("Arial", Font.PLAIN, 16));
		
		side_electricity_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		side_electricity_button.setMaximumSize(new Dimension(180, 40));
		side_electricity_button.setBackground(new Color(70, 70, 70));
		side_electricity_button.setForeground(Color.WHITE);
		side_electricity_button.setFocusPainted(false);
		side_electricity_button.setBorderPainted(false);
		side_electricity_button.setFont(new Font("Arial", Font.PLAIN, 16));
		
		side_water_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		side_water_button.setMaximumSize(new Dimension(180, 40));
		side_water_button.setBackground(new Color(70, 70, 70));
		side_water_button.setForeground(Color.WHITE);
		side_water_button.setFocusPainted(false);
		side_water_button.setBorderPainted(false);
		side_water_button.setFont(new Font("Arial", Font.PLAIN, 16));
		
		side_gas_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		side_gas_button.setMaximumSize(new Dimension(180, 40));
		side_gas_button.setBackground(new Color(70, 70, 70));
		side_gas_button.setForeground(Color.WHITE);
		side_gas_button.setFocusPainted(false);
		side_gas_button.setBorderPainted(false);
		side_gas_button.setFont(new Font("Arial", Font.PLAIN, 16));
		
		side_profile_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		side_profile_button.setMaximumSize(new Dimension(180, 40));
		side_profile_button.setBackground(new Color(70, 70, 70));
		side_profile_button.setForeground(Color.WHITE);
		side_profile_button.setFocusPainted(false);
		side_profile_button.setBorderPainted(false);
		side_profile_button.setFont(new Font("Arial", Font.PLAIN, 16));
		
		side_logout_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		side_logout_button.setMaximumSize(new Dimension(180, 40));
		side_logout_button.setBackground(new Color(70, 70, 70));
		side_logout_button.setForeground(Color.WHITE);
		side_logout_button.setFocusPainted(false);
		side_logout_button.setBorderPainted(false);
		side_logout_button.setFont(new Font("Arial", Font.PLAIN, 16));
		
		//development button
		side_dev_button = new JButton("Development");
		
		side_dev_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		side_dev_button.setMaximumSize(new Dimension(180, 40));
		side_dev_button.setBackground(new Color(70, 70, 70));
		side_dev_button.setForeground(Color.WHITE);
		side_dev_button.setFocusPainted(false);
		side_dev_button.setBorderPainted(false);
		side_dev_button.setFont(new Font("Arial", Font.PLAIN, 16));
		
	}
	
	private void addComponentsToFrame() {
		content_pane.add(side_panel, BorderLayout.WEST);
		content_pane.add(card_panel, BorderLayout.CENTER);
		
		side_panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Top margin
		side_panel.add(side_home_button);
		side_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		side_panel.add(side_electricity_button);
		side_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		side_panel.add(side_water_button);
		side_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		side_panel.add(side_gas_button);
		side_panel.add(Box.createVerticalGlue());// Push remaining items to bottom
		side_panel.add(Box.createRigidArea(new Dimension(0, 10))); // Development button
		side_panel.add(side_dev_button);
		side_panel.add(Box.createRigidArea(new Dimension(0, 10))); // Profile panel at bottom
		side_panel.add(side_profile_button);
		side_panel.add(Box.createRigidArea(new Dimension(0, 10))); // Logout button
		side_panel.add(side_logout_button);
		side_panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Bottom margin
		
	}
	
	private void setupActionListeners() {
        side_home_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                home_panel.home_Panel_Refresh();
            	card_layout.show(card_panel, HOME_PANEL);
            }
        });
        
        side_electricity_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(card_panel, ELECTRICITY_PANEL);
			}
		});
        
        side_water_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		card_layout.show(card_panel, WATER_PANEL);
        	}
        });
        
        side_gas_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(card_panel, GAS_PANEL);
			}
		});
        
        side_profile_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		card_layout.show(card_panel, PROFILE_PANEL);
			}
        });
        
        side_logout_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", javax.swing.JOptionPane.YES_NO_OPTION);
				if (response == javax.swing.JOptionPane.YES_OPTION) {
					dispose();
					Log_In_Window loginWindow = new Log_In_Window(database_manager);
					loginWindow.setVisible(true);
				}
			}
		});
        
        //dev button
        side_dev_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(card_panel, DEV_PANEL);
        		development_panel.updateDimensions(side_panel);
			}
		});
    }
	
}
