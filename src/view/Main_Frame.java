package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import database.Database_Manager;
import model.*;
import view.panel.*;


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
	
	private static final String HOME_PANEL = "Home";
	private static final String ELECTRICITY_PANEL = "Electricity";
	private static final String WATER_PANEL = "Water";
	private static final String GAS_PANEL = "Gas";
	
	private JButton side_home_button;
	private JButton side_electricity_button;
	private JButton side_water_button;
	private JButton side_gas_button;
	
	public Main_Frame(Database_Manager database_manager, User user) {
		this.database_manager = database_manager;
		this.current_user = user;
		
		initializeFrame();
		createPanels();
		createSidePanel();
		createCardPanel();
		
		content_pane.add(side_panel, BorderLayout.WEST);
		content_pane.add(card_panel, BorderLayout.CENTER);
		
	}
	
	private void initializeFrame() {
		setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 1200, 725);
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
        electricity_panel = new Electricity_Panel(database_manager, current_user);
        water_panel = new Water_Panel(database_manager, current_user);
        gas_panel = new Gas_Panel(database_manager, current_user);
        
        card_panel.add(home_panel, HOME_PANEL);
        card_panel.add(electricity_panel, ELECTRICITY_PANEL);
        card_panel.add(water_panel, WATER_PANEL);
        card_panel.add(gas_panel, GAS_PANEL);
        
        
	}
	
	private void createCardPanel() {
		card_panel = new JPanel();
		card_layout = new CardLayout();
		card_panel.setLayout(card_layout);
		
		Home_Panel home_panel = new Home_Panel(database_manager, current_user);
		Electricity_Panel electricity_panel = new Electricity_Panel(database_manager, current_user);
		Water_Panel water_panel = new Water_Panel(database_manager, current_user);
		Gas_Panel gas_panel = new Gas_Panel(database_manager, current_user);
		
		card_panel.add(home_panel, HOME_PANEL);
		card_panel.add(electricity_panel, ELECTRICITY_PANEL);
		card_panel.add(water_panel, WATER_PANEL);
		card_panel.add(gas_panel, GAS_PANEL);
		
	}
	
	private void createSidePanel() {
		Side_Panel = new JPanel();
		Side_Panel.setLayout(new CardLayout());
		
		Side_Panel.add(new Home_Panel(database_manager, current_user), HOME_PANEL);
		Side_Panel.add(new Electricity_Panel(database_manager, current_user), ELECTRICITY_PANEL);
		Side_Panel.add(new Water_Panel(database_manager, current_user), WATER_PANEL);
		Side_Panel.add(new Gas_Panel(database_manager, current_user), GAS_PANEL);
		
	}
}
