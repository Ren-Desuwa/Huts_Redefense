package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.User;

import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;

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
		
		JPanel panel_Electricity_Graph = new JPanel();
		panel_Electricity_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Electricity_Graph.setBounds(512, 190, 413, 365);
		add(panel_Electricity_Graph);
		
		JPanel panel_Water_Graph = new JPanel();
		panel_Water_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Water_Graph.setBounds(512, 190, 413, 313);
		add(panel_Water_Graph);
		
		JPanel panel_Gas_Graph = new JPanel();
		panel_Gas_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Gas_Graph.setBounds(512, 190, 413, 313);
		add(panel_Gas_Graph);
		
		JPanel panel_behind1 = new JPanel();
		panel_behind1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind1.setBounds(528, 175, 413, 365);
		add(panel_behind1);
		
		JPanel panel_behind2 = new JPanel();
		panel_behind2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind2.setBounds(544, 162, 413, 356);
		add(panel_behind2);
		
		JPanel panel_behind3 = new JPanel();
		panel_behind3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind3.setBounds(560, 147, 413, 347);
		add(panel_behind3);
		
		JPanel panel_Welcome_Title = new JPanel();
		panel_Welcome_Title.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Welcome_Title.setBounds(29, 22, 944, 98);
		add(panel_Welcome_Title);
		
		JPanel panel_Electricity_Graph_1 = new JPanel();
		panel_Electricity_Graph_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Electricity_Graph_1.setBounds(29, 147, 467, 408);
		add(panel_Electricity_Graph_1);
		
		JPanel panel_tips = new JPanel();
		panel_tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_tips.setBounds(29, 587, 944, 116);
		add(panel_tips);
		

	}
}
