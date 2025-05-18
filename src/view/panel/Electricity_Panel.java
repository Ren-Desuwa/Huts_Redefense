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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Electricity_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	
	private JLabel lbl_Electricity_Reading_Value;
	private User current_user;
	private JPanel panel_Electricity_Consumption_Title;
	private JPanel panel_add_reading;
	private JTextField tf_Reading;
	private JTextField tf_Rate;
	private JTextField tf_TotalPrice;

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
		
		JLabel lbl_Title_Electricity_Consumption = new JLabel("Electricity Consumption");
		lbl_Title_Electricity_Consumption.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Electricity_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Title_Electricity_Consumption.setBounds(10, 0, 393, 54);
		panel_Electricity_Consumption_Title.add(lbl_Title_Electricity_Consumption);
		
		JLabel lbl_Date = new JLabel("18/05/2025");
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Date.setBounds(764, 11, 170, 54);
		panel_Electricity_Consumption_Title.add(lbl_Date);
		
		JLabel lbl_SubTitle_Electricity_Consmption = new JLabel("Track and manage your electricity usage ");
		lbl_SubTitle_Electricity_Consmption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_SubTitle_Electricity_Consmption.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_SubTitle_Electricity_Consmption.setBounds(20, 52, 393, 22);
		panel_Electricity_Consumption_Title.add(lbl_SubTitle_Electricity_Consmption);
		
		panel_add_reading = new JPanel();
		panel_add_reading.setLayout(null);
		panel_add_reading.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_add_reading.setBounds(21, 114, 466, 377);
		add(panel_add_reading);
		
		JLabel lbl_Title_AddReading = new JLabel("Add New Reading");
		lbl_Title_AddReading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddReading.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_AddReading.setBounds(0, 0, 393, 32);
		panel_add_reading.add(lbl_Title_AddReading);
		
		JLabel lbl_Date_1 = new JLabel("Date");
		lbl_Date_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Date_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Date_1.setBounds(10, 75, 114, 22);
		panel_add_reading.add(lbl_Date_1);
		
		JLabel lbl_Reading = new JLabel("Reading (kWh)");
		lbl_Reading.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Reading.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Reading.setBounds(10, 188, 141, 22);
		panel_add_reading.add(lbl_Reading);
		
		tf_Reading = new JTextField();
		tf_Reading.setColumns(10);
		tf_Reading.setBounds(10, 212,169, 56);
		panel_add_reading.add(tf_Reading);
		
		JLabel lbl_Rate = new JLabel("Rate (Php)");
		lbl_Rate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Rate.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Rate.setBounds(262, 75, 141, 22);
		panel_add_reading.add(lbl_Rate);
		
		tf_Rate = new JTextField();
		tf_Rate.setColumns(10);
		tf_Rate.setBounds(262, 99, 169, 59);
		panel_add_reading.add(tf_Rate);
		
		JLabel lbl_TotalPrice = new JLabel("Total Price (Php)");
		lbl_TotalPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_TotalPrice.setBounds(262, 188, 141, 22);
		panel_add_reading.add(lbl_TotalPrice);
		
		tf_TotalPrice = new JTextField();
		tf_TotalPrice.setColumns(10);
		tf_TotalPrice.setBounds(262, 212, 169, 56);
		panel_add_reading.add(tf_TotalPrice);
		
		JButton btn_Add_Reading = new JButton("Add Reading");
		btn_Add_Reading.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addReading();
			}
		});
		btn_Add_Reading.setBounds(161, 315, 120, 50);
		panel_add_reading.add(btn_Add_Reading);
		
		JComboBox CB_Day = new JComboBox();
		CB_Day.setFont(new Font("Tahoma", Font.PLAIN, 17));
		for (int i = 1; i <= 31; i++) {
			CB_Day.addItem(i);
			}
		CB_Day.setBounds(10, 99, 63, 59);
		panel_add_reading.add(CB_Day);
		
		JComboBox CB_Month = new JComboBox();
		CB_Month.setFont(new Font("Tahoma", Font.PLAIN, 17));
		for (int i = 1; i <= 12; i++) {
			CB_Month.addItem(i);
			}
		CB_Month.setBounds(83, 99, 63, 59);
		panel_add_reading.add(CB_Month);
		
		JComboBox CB_Year = new JComboBox();
		for (int i = 1975; i <= 2025; i++) {
			CB_Year.addItem(i);
			}
		CB_Year.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CB_Year.setBounds(157, 99, 63, 59);
		panel_add_reading.add(CB_Year);
		
		JList<String> all_readings = getAllReadings();
		JScrollPane SP_Recent_Readings = new JScrollPane(all_readings);
		SP_Recent_Readings.setBounds(499, 114, 466, 377);
		add(SP_Recent_Readings);
		
		JLabel lbl_Title_AddReading_1 = new JLabel("Recent Readings");
		lbl_Title_AddReading_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddReading_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		SP_Recent_Readings.setColumnHeaderView(lbl_Title_AddReading_1);
		
		JPanel panel_Current_Reading = new JPanel();
		panel_Current_Reading.setLayout(null);
		panel_Current_Reading.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Current_Reading.setBounds(21, 509, 466, 168);
		add(panel_Current_Reading);
		
		JLabel lbl_Title_Current_Reading = new JLabel("Current Reading");
		lbl_Title_Current_Reading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Current_Reading.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Current_Reading.setBounds(42, 11, 393, 32);
		panel_Current_Reading.add(lbl_Title_Current_Reading);
		
		lbl_Electricity_Reading_Value = new JLabel();
		lbl_Electricity_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Electricity_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Electricity_Reading_Value.setBounds(144, 54, 100, 32);
		panel_Current_Reading.add(lbl_Electricity_Reading_Value);
		
		JLabel lbl_Electricity_Reading_Unit = new JLabel("KwH");
		lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Electricity_Reading_Unit.setBounds(254, 55, 68, 32);
		panel_Current_Reading.add(lbl_Electricity_Reading_Unit);
		
		JButton btn_Add_New_Reading = new JButton("Add New Reading");
		btn_Add_New_Reading.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		btn_Add_New_Reading.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_Add_New_Reading.setBounds(155, 125, 151, 34);
		panel_Current_Reading.add(btn_Add_New_Reading);
		
		JPanel panel_tips = new JPanel();
		panel_tips.setLayout(null);
		panel_tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_tips.setBounds(499, 509, 466, 168);
		add(panel_tips);
		
		JLabel lbl_Title_Tips = new JLabel("Energy Saving Tips");
		lbl_Title_Tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Tips.setBounds(42, 11, 393, 32);
		panel_tips.add(lbl_Title_Tips);
		
		JLabel lbl_Tips1 = new JLabel("<html><ul><li>Unplug chargers when not in use to avoid phantom energy consumption</li></ul></html>");
		lbl_Tips1.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips1.setBounds(-16, 49, 502, 51);
		panel_tips.add(lbl_Tips1);
		lbl_Tips1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lbl_Tips2 = new JLabel("<html><ul><li>Use LED bulbs it consumes 75% less energy than incandescent bulbs</li></ul></html>");
		lbl_Tips2.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips2.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Tips2.setBounds(-16, 107, 502, 51);
		panel_tips.add(lbl_Tips2);
		
		setupData();
	}
	
public void setupData() {
		
		try {
			Reading electricity_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "Electricity");
			
			if (electricity_reading == null) {
				lbl_Electricity_Reading_Value.setText("No Data");
			} else {
				lbl_Electricity_Reading_Value.setText(String.valueOf(electricity_reading.getReading()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addReading() {
		double reading = Double.parseDouble(tf_Reading.getText());
		double rate = Double.parseDouble(tf_Rate.getText());
		double total_price = Double.parseDouble(tf_TotalPrice.getText());
		
//		try {
//			database_manager.getReadingManager().addReading(current_user, );
//			System.out.println("Reading added successfully.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	private JList<String> getAllReadings() {
		try {
			if (!database_manager.getReadingManager().isReadingExists(current_user, TOOL_TIP_TEXT_KEY)) {
				JList<String> list = new JList<>(new String[] {"No readings found.", "Please add a reading."});
				list.setFont(new Font("Tahoma", Font.PLAIN, 15));
				list.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				list.setPreferredSize(new Dimension(429, 448));
				list.setFixedCellHeight(30);
				return list;
			}
			List<Reading> all_readings = database_manager.getReadingManager().getAllReadingsByType(current_user, "Electricity");
			
			String[] readings = new String[all_readings.size()];
			for (int i = 0; i < all_readings.size(); i++) {
				Reading reading = all_readings.get(i);
				readings[i] = "Date: " + reading.getDate() + ", Reading: " + reading.getReading() + " kWh, Rate: " + reading.getRate() + " Php, Total Price: " + reading.getTotal_Price() + " Php";
			}
			JList<String> list = new JList<>(readings);
			list.setFont(new Font("Tahoma", Font.PLAIN, 15));
			list.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			list.setPreferredSize(new Dimension(429, 448));
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
