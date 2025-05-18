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

public class Electricity_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private User current_user;
	private JPanel panel_Welcome_Title;
	private JPanel panel_add_reading;
	private JTextField tf_Date;
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
		
		panel_Welcome_Title = new JPanel();
		panel_Welcome_Title.setLayout(null);
		panel_Welcome_Title.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Welcome_Title.setBounds(21, 11, 944, 85);
		add(panel_Welcome_Title);
		
		JLabel lbl_Title_Electricity_Consumption = new JLabel("Electricity Consumption");
		lbl_Title_Electricity_Consumption.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Electricity_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Title_Electricity_Consumption.setBounds(10, 0, 393, 54);
		panel_Welcome_Title.add(lbl_Title_Electricity_Consumption);
		
		JLabel lbl_Date = new JLabel("18/05/2025");
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Date.setBounds(764, 11, 170, 54);
		panel_Welcome_Title.add(lbl_Date);
		
		JLabel lbl_SubTitle_Electricity_Consmption = new JLabel("Track and manage your electricity usage ");
		lbl_SubTitle_Electricity_Consmption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_SubTitle_Electricity_Consmption.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_SubTitle_Electricity_Consmption.setBounds(20, 52, 393, 22);
		panel_Welcome_Title.add(lbl_SubTitle_Electricity_Consmption);
		
		panel_add_reading = new JPanel();
		panel_add_reading.setLayout(null);
		panel_add_reading.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_add_reading.setBounds(21, 114, 467, 408);
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
		
		tf_Date = new JTextField();
		tf_Date.setBounds(10, 102,169, 56);
		panel_add_reading.add(tf_Date);
		tf_Date.setColumns(10);
		
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
		btn_Add_Reading.setBounds(161, 315, 120, 50);
		panel_add_reading.add(btn_Add_Reading);
		
		JList<String> all_readings = getAllReadings();
		JScrollPane scrollPane = new JScrollPane(all_readings);
		scrollPane.setBounds(518, 114, 447, 408);
		add(scrollPane);
		
		JLabel lbl_Title_AddReading_1 = new JLabel("Recent Readings");
		lbl_Title_AddReading_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddReading_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		scrollPane.setColumnHeaderView(lbl_Title_AddReading_1);
		
	}
	
	private JList<String> getAllReadings() {
		try {
			List<Reading> all_readings = database_manager.getReadingManager().getAllReadingsByType(current_user, "Electricity");
			if (all_readings != null) {
				return new JList<String>(new String[] {"No readings found.", "Please add a reading."});
			}
			
			String[] readings = new String[all_readings.size()];
			for (int i = 0; i < all_readings.size(); i++) {
				Reading reading = all_readings.get(i);
				readings[i] = "Date: " + reading.getDate() + ", Reading: " + reading.getReading() + " kWh, Rate: " + reading.getRate() + " Php, Total Price: " + reading.getTotal_Price() + " Php";
			}
			JList<String> list = new JList<>(readings);
			list.setFont(new Font("Tahoma", Font.PLAIN, 15));
			list.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			list.setPreferredSize(new Dimension(447, 408));
			list.setVisibleRowCount(10);
			list.setFixedCellHeight(40);
			list.setFixedCellWidth(447);
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
