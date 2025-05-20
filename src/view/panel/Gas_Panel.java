package view.panel;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JPanel;


import database.Database_Manager;
import model.Reading;
import model.User;
import view.login.Sign_Up_Window;
import view.panel.misc.Add_Reading_Panel;
import view.panel.misc.Edit_Reading_Panel;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;

public class Gas_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private User current_user;
	
	private JLabel lbl_Gas_Reading_Value;
	private JPanel panel_Gas_Consumption_Title;
	private JPanel panel_add_reading;
	private JList<String> all_readings;
	private JScrollPane sP_Recent_Readings;
	private JLabel lbl_Title_Gas_Consumption;
	private JLabel lbl_SubTitle_Gas_Consmption;
	private JLabel lbl_Date;
	private JLabel lbl_Title_Current_Reading;
	private JLabel lbl_Gas_Reading_Unit;
	private JButton btn_Add_New_Reading;
	private JPanel panel_Current_Reading;
	private JPanel panel_tips;
	private JLabel lbl_Title_Tips;
	private JLabel lbl_Tips1;
	private JLabel lbl_Tips2;
	private JLabel lbl_Title_AddReading_1;
	private JLabel lbl_Title_AddReading;
	private JPanel Headerpanel;
	private JLabel lbl_Head_Date;
	private JLabel lbl_Head_Readings;
	private JLabel lbl_Head_Rate;
	private JLabel lbl_Head_TotalPrice;
	private JPanel Line;
	private Home_Panel homepanel;
	private JLabel lblTime;
	/**
	 * Create the panel.
	 */
	public Gas_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
	    panel_Gas_Consumption_Title = new JPanel();
		panel_Gas_Consumption_Title.setLayout(null);
		panel_Gas_Consumption_Title.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Gas_Consumption_Title.setBackground(Color.WHITE);
		panel_Gas_Consumption_Title.setBounds(21, 11, 944, 85);
		add(panel_Gas_Consumption_Title);
		
        lbl_Title_Gas_Consumption = new JLabel("Gas Consumption");
		lbl_Title_Gas_Consumption.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Gas_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Title_Gas_Consumption.setBounds(-46, 0, 393, 54);
		panel_Gas_Consumption_Title.add(lbl_Title_Gas_Consumption);
		
	    lbl_Date = new JLabel("20/05/2025");
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Date.setBounds(764, 11, 170, 54);
		panel_Gas_Consumption_Title.add(lbl_Date);
		
		lblTime = new JLabel("11:23 AM");
		lblTime.setVerticalAlignment(SwingConstants.TOP);
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTime.setBounds(764, 46, 170, 41);
		panel_Gas_Consumption_Title.add(lblTime);
		
		lbl_SubTitle_Gas_Consmption = new JLabel("Track and manage your gas usage ");
		lbl_SubTitle_Gas_Consmption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_SubTitle_Gas_Consmption.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_SubTitle_Gas_Consmption.setBounds(20, 52, 393, 22);
		panel_Gas_Consumption_Title.add(lbl_SubTitle_Gas_Consmption);
		
		panel_add_reading = new JPanel();
		panel_add_reading.setLayout(null);
		panel_add_reading.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_add_reading.setBackground(Color.WHITE);
		panel_add_reading.setBounds(21, 114, 466, 377);
		add(panel_add_reading);
		
		lbl_Title_AddReading = new JLabel("Consumption Trends");
		lbl_Title_AddReading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddReading.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_AddReading.setBounds(0, 0, 466, 32);
		panel_add_reading.add(lbl_Title_AddReading);
		
		all_readings = getAllReadings();
		sP_Recent_Readings = new JScrollPane();
		sP_Recent_Readings.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		sP_Recent_Readings.setBounds(499, 114, 466, 377);
		add(sP_Recent_Readings);
		sP_Recent_Readings.setViewportView(all_readings);
		
		Headerpanel = new JPanel();
		Headerpanel.setLayout(null);
		Headerpanel.setPreferredSize(new Dimension(466, 70));
		Headerpanel.setBackground(Color.WHITE);
		sP_Recent_Readings.setColumnHeaderView(Headerpanel);
		
		lbl_Title_AddReading_1 = new JLabel("Recent Readings");
		lbl_Title_AddReading_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddReading_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_AddReading_1.setBounds(0, 0, 466, 31);
		Headerpanel.add(lbl_Title_AddReading_1);
		
		lbl_Head_Date = new JLabel("Date");
		lbl_Head_Date.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Date.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_Head_Date.setBounds(10, 39, 78, 17);
		Headerpanel.add(lbl_Head_Date);
		
		lbl_Head_Readings = new JLabel("Readings");
		lbl_Head_Readings.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Readings.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_Head_Readings.setBounds(124, 39, 78, 17);
		Headerpanel.add(lbl_Head_Readings);
		
		lbl_Head_Rate = new JLabel("Rate");
		lbl_Head_Rate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Rate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_Head_Rate.setBounds(245, 39, 78, 17);
		Headerpanel.add(lbl_Head_Rate);
		
		lbl_Head_TotalPrice = new JLabel("Total Price");
		lbl_Head_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_TotalPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_Head_TotalPrice.setBounds(342, 39, 108, 17);
		Headerpanel.add(lbl_Head_TotalPrice);
		
		Line = new JPanel();
		Line.setBorder(new LineBorder(new Color(0, 0, 0), 12));
		Line.setBounds(10, 64, 446, 3);
		Headerpanel.add(Line);
		
		panel_Current_Reading = new JPanel();
		panel_Current_Reading.setLayout(null);
		panel_Current_Reading.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Current_Reading.setBackground(Color.WHITE);
		panel_Current_Reading.setBounds(21, 509, 466, 168);
		add(panel_Current_Reading);
		
		lbl_Title_Current_Reading = new JLabel("Current Reading");
		lbl_Title_Current_Reading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Current_Reading.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Current_Reading.setBounds(42, 11, 393, 32);
		panel_Current_Reading.add(lbl_Title_Current_Reading);
		
		lbl_Gas_Reading_Value = new JLabel();
		lbl_Gas_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Gas_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Gas_Reading_Value.setBounds(144, 54, 100, 32);
		panel_Current_Reading.add(lbl_Gas_Reading_Value);
		
		lbl_Gas_Reading_Unit = new JLabel("Qty");
		lbl_Gas_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Gas_Reading_Unit.setBounds(254, 55, 68, 32);
		panel_Current_Reading.add(lbl_Gas_Reading_Unit);
		
		btn_Add_New_Reading = new JButton("Add New Reading");
		btn_Add_New_Reading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_Add_New_Reading.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_Add_New_Reading.setBackground(Color.LIGHT_GRAY);
		btn_Add_New_Reading.setBounds(155, 125, 151, 34);
		panel_Current_Reading.add(btn_Add_New_Reading);
		
		panel_tips = new JPanel();
		panel_tips.setLayout(null);
		panel_tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_tips.setBackground(Color.WHITE);
		panel_tips.setBounds(499, 509, 466, 168);
		add(panel_tips);
		
		lbl_Title_Tips = new JLabel("Energy Saving Tips");
		lbl_Title_Tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Tips.setBounds(42, 11, 393, 32);
		panel_tips.add(lbl_Title_Tips);
		
		lbl_Tips1 = new JLabel("<html><ul><li>Unplug chargers when not in use to avoid phantom energy consumption</li></ul></html>");
		lbl_Tips1.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Tips1.setBounds(-16, 49, 502, 51);
		panel_tips.add(lbl_Tips1);
		
		lbl_Tips2 = new JLabel("<html><ul><li>Use LED bulbs it consumes 75% less energy than incandescent bulbs</li></ul></html>");
		lbl_Tips2.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips2.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Tips2.setBounds(-16, 107, 502, 51);
		panel_tips.add(lbl_Tips2);
		
	}
}
