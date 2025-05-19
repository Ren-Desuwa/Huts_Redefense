package view.panel;

import java.awt.Dimension;

import javax.swing.JPanel;


import database.Database_Manager;
import model.Reading;
import model.User;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Electricity_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	
	private JLabel lbl_Electricity_Reading_Value;
	private User current_user;
	private JPanel panel_Electricity_Consumption_Title;
	private JPanel panel_add_reading;
	private JList<String> all_readings;
	private JScrollPane sP_Recent_Readings;
	private JLabel lbl_Title_Electricity_Consumption;
	private JLabel lbl_SubTitle_Electricity_Consmption;
	private JLabel lbl_Date;
	private JLabel lbl_Title_Current_Reading;
	private JLabel lbl_Electricity_Reading_Unit;
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
	/**
	 * Create the panel.
	 */
	public Electricity_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
		panel_Electricity_Consumption_Title = new JPanel();
		panel_Electricity_Consumption_Title.setLayout(null);
		panel_Electricity_Consumption_Title.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Electricity_Consumption_Title.setBounds(21, 11, 944, 85);
		add(panel_Electricity_Consumption_Title);
		
		lbl_Title_Electricity_Consumption = new JLabel("Electricity Consumption");
		lbl_Title_Electricity_Consumption.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Electricity_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Title_Electricity_Consumption.setBounds(10, 0, 393, 54);
		panel_Electricity_Consumption_Title.add(lbl_Title_Electricity_Consumption);
		
		lbl_Date = new JLabel("Date");
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Date.setBounds(764, 11, 170, 54);
		lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		panel_Electricity_Consumption_Title.add(lbl_Date);
		
		lbl_SubTitle_Electricity_Consmption = new JLabel("Track and manage your electricity usage ");
		lbl_SubTitle_Electricity_Consmption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_SubTitle_Electricity_Consmption.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_SubTitle_Electricity_Consmption.setBounds(20, 52, 393, 22);
		panel_Electricity_Consumption_Title.add(lbl_SubTitle_Electricity_Consmption);
		
		panel_add_reading = new JPanel();
		panel_add_reading.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_add_reading.setBounds(21, 114, 466, 377);
		add(panel_add_reading);
		panel_add_reading.setLayout(null);
		
		lbl_Title_AddReading = new JLabel("Consumption Trends");
		lbl_Title_AddReading.setBounds(0, 0, 466, 32);
		lbl_Title_AddReading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddReading.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panel_add_reading.add(lbl_Title_AddReading);
		
		all_readings = getAllReadings();
		sP_Recent_Readings = new JScrollPane();
		sP_Recent_Readings.setBounds(499, 114, 466, 377);
		sP_Recent_Readings.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(sP_Recent_Readings);
		sP_Recent_Readings.setViewportView(all_readings);
		
		Headerpanel = new JPanel();
		sP_Recent_Readings.setColumnHeaderView(Headerpanel);
		Headerpanel.setLayout(null);
		Headerpanel.setPreferredSize(new Dimension(466, 70));
		
		lbl_Title_AddReading_1 = new JLabel("Recent Readings");
		lbl_Title_AddReading_1.setBounds(0, 0, 466, 31);
		Headerpanel.add(lbl_Title_AddReading_1);
		lbl_Title_AddReading_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddReading_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		lbl_Head_Date = new JLabel("Date");
		lbl_Head_Date.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Date.setBounds(10, 39, 78, 17);
		lbl_Head_Date.setFont(new Font("Tahoma", Font.BOLD, 15));
		Headerpanel.add(lbl_Head_Date);
		
		lbl_Head_Readings = new JLabel("Readings");
		lbl_Head_Readings.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Readings.setBounds(124, 39, 78, 17);
		lbl_Head_Readings.setFont(new Font("Tahoma", Font.BOLD, 15));
		Headerpanel.add(lbl_Head_Readings);
		
		lbl_Head_Rate = new JLabel("Rate");
		lbl_Head_Rate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Rate.setBounds(245, 39, 78, 17);
		lbl_Head_Rate.setFont(new Font("Tahoma", Font.BOLD, 15));
		Headerpanel.add(lbl_Head_Rate);
		
		lbl_Head_TotalPrice = new JLabel("Total Price");
		lbl_Head_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_TotalPrice.setBounds(348, 39, 108, 17);
		lbl_Head_TotalPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		Headerpanel.add(lbl_Head_TotalPrice);
		
		Line = new JPanel();
		Line.setBorder(new LineBorder(new Color(0, 0, 0), 12));
		Line.setBounds(10, 64, 446, 3);
		Headerpanel.add(Line);
		
		panel_Current_Reading = new JPanel();
		panel_Current_Reading.setLayout(null);
		panel_Current_Reading.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Current_Reading.setBounds(21, 509, 466, 168);
		add(panel_Current_Reading);
		
		lbl_Title_Current_Reading = new JLabel("Current Reading");
		lbl_Title_Current_Reading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Current_Reading.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Current_Reading.setBounds(42, 11, 393, 32);
		panel_Current_Reading.add(lbl_Title_Current_Reading);
		
		lbl_Electricity_Reading_Value = new JLabel();
		lbl_Electricity_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Electricity_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Electricity_Reading_Value.setBounds(144, 54, 100, 32);
		panel_Current_Reading.add(lbl_Electricity_Reading_Value);
		
		lbl_Electricity_Reading_Unit = new JLabel("KwH");
		lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Electricity_Reading_Unit.setBounds(254, 55, 68, 32);
		panel_Current_Reading.add(lbl_Electricity_Reading_Unit);
		
		btn_Add_New_Reading = new JButton("Add New Reading");
		btn_Add_New_Reading.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goToAddReading();
			}
		});
		btn_Add_New_Reading.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_Add_New_Reading.setBounds(155, 125, 151, 34);
		panel_Current_Reading.add(btn_Add_New_Reading);
		
		panel_tips = new JPanel();
		panel_tips.setLayout(null);
		panel_tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_tips.setBounds(499, 509, 466, 168);
		add(panel_tips);
		
		lbl_Title_Tips = new JLabel("Energy Saving Tips");
		lbl_Title_Tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Tips.setBounds(42, 11, 393, 32);
		panel_tips.add(lbl_Title_Tips);
		
		lbl_Tips1 = new JLabel("<html><ul><li>Unplug chargers when not in use to avoid phantom energy consumption</li></ul></html>");
		lbl_Tips1.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips1.setBounds(-16, 49, 502, 51);
		panel_tips.add(lbl_Tips1);
		lbl_Tips1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lbl_Tips2 = new JLabel("<html><ul><li>Use LED bulbs it consumes 75% less energy than incandescent bulbs</li></ul></html>");
		lbl_Tips2.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips2.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Tips2.setBounds(-16, 107, 502, 51);
		panel_tips.add(lbl_Tips2);
		
		setupData();
	}
	
	
	private void goToAddReading() {

		
		// Open the Add Reading window
		Add_Reading_Panel add_reading_panel = new Add_Reading_Panel(database_manager, current_user);
		add_reading_panel.setVisible(true);
	}
	public void setupData() {
		
		try {
			Reading electricity_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "electricity");
			
			if (electricity_reading == null) {
				lbl_Electricity_Reading_Value.setText("No Data");
			} else {
				lbl_Electricity_Reading_Value.setText(String.valueOf(electricity_reading.getReading()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JList<String> getAllReadings() {
		try {
			if (!database_manager.getReadingManager().isReadingExists(current_user, "electricity")) {
				JList<String> list = new JList<>(new String[] {"No readings found.", "Please add a reading."});
				list.setFont(new Font("Tahoma", Font.PLAIN, 12));
				list.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				list.setPreferredSize(new Dimension(429, 448));
				list.setFixedCellHeight(30);
				return list;
			}
			List<Reading> all_readings = database_manager.getReadingManager().getAllReadingsByType(current_user, "electricity");
			
			String[] readings = new String[all_readings.size()];
			for (int i = 0; i < all_readings.size(); i++) {
				Reading reading = all_readings.get(i);
				readings[i] = " \t" + reading.getDate() + " \t" + reading.getReading() + "kWh \t" + reading.getRate() + "Php \t" + reading.getTotal_Price() + "Php";
			}
			JList<String> list = new JList<>(readings);
			list.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			list.setPreferredSize(new Dimension(429, 448));
			list.setFixedCellHeight(30);
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new JList<>(new String[] {"Error fetching readings."});
		}
	}
}
