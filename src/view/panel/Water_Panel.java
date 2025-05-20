package view.panel;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JPanel;


import database.Database_Manager;
import model.Reading;
import model.User;
import view.panel.misc.Add_Reading_Panel;
import view.panel.misc.Edit_Reading_Panel;
import visuals.Graph_Panel;
import visuals.Rounded_Button;
import visuals.Rounded_Panel;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Water_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Database and user fields
	private Database_Manager database_manager;
	private User current_user;
	
	// Main panel fields
	private JPanel panel_Title_Water_Consumption;
	private JPanel panel_Current_Reading;
	private JPanel panel_Graph_Container;
	private JPanel panel_tips;
	private JPanel panel_Recent_Readings_Container;
	
	// Scroll panel fields
	private JPanel Headerpanel;
	private JPanel Line;
	private JList<String> all_readings;
	
	// Graph panel fields
	private JPanel panel_Graph_View;
	private Graph_Panel graph_Panel;
	private JLabel lbl_Title_Graph;
	
	// Home panel
	private Home_Panel homepanel;
	
	// Title panel label
	private JLabel lbl_Title_Water_Consumption;
	private JLabel lbl_SubTitle_Water_Consmption;
	private JLabel lbl_Date;
	private JLabel lbl_Time;
	
	// Scroll label components
	private JScrollPane sP_Recent_Readings;
	private JLabel lbl_Title_RecentReadings;
	private JLabel lbl_Head_Date;
	private JLabel lbl_Head_Readings;
	private JLabel lbl_Head_Rate;
	private JLabel lbl_Head_TotalPrice;
	
	// Current reading components
	private JLabel lbl_Title_Current_Reading;
	private JLabel lbl_Water_Reading_Value;
	private JLabel lbl_Water_Reading_Unit;
	private JButton btn_Add_New_Reading;
	
	// Tips panel labels
	private JLabel lbl_Title_Tips;
	private JLabel lbl_Tips1;
	private JLabel lbl_Tips2;
	


	public Water_Panel(Database_Manager database_manager, User current_user, Home_Panel homepanel) {
		this.homepanel = homepanel;
		this.database_manager = database_manager;
		this.current_user = current_user;
		
		setBackground(new Color(213, 213, 213));
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
		panel_Title_Water_Consumption = new Rounded_Panel();
		panel_Title_Water_Consumption.setBackground(new Color(255, 255, 255));
		panel_Title_Water_Consumption.setLayout(null);
		panel_Title_Water_Consumption.setBounds(21, 11, 944, 85);
		add(panel_Title_Water_Consumption);
		
		lbl_Title_Water_Consumption = new JLabel("Water Consumption");
		lbl_Title_Water_Consumption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Title_Water_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Title_Water_Consumption.setBounds(20, 0, 393, 54);
		panel_Title_Water_Consumption.add(lbl_Title_Water_Consumption);
		
		lbl_Date = new JLabel("Date");
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Date.setBounds(764, 11, 170, 54);
		lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		panel_Title_Water_Consumption.add(lbl_Date);
		
		lbl_Time = new JLabel("Time");
        lbl_Time.setVerticalAlignment(SwingConstants.TOP);
        lbl_Time.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Time.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Time.setBounds(764, 39, 170, 41);
        lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        panel_Title_Water_Consumption.add(lbl_Time);
		
		lbl_SubTitle_Water_Consmption = new JLabel("Track and manage your water usage ");
		lbl_SubTitle_Water_Consmption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_SubTitle_Water_Consmption.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_SubTitle_Water_Consmption.setBounds(20, 52, 393, 22);
		panel_Title_Water_Consumption.add(lbl_SubTitle_Water_Consmption);
		
		panel_Graph_Container = new Rounded_Panel();
		panel_Graph_Container.setBackground(new Color(255, 255, 255));
		panel_Graph_Container.setBounds(21, 114, 466, 377);
		add(panel_Graph_Container);
		panel_Graph_Container.setLayout(null);
		
		lbl_Title_Graph = new JLabel("Monthly Water Expenses");
		lbl_Title_Graph.setBounds(0, 0, 466, 32);
		lbl_Title_Graph.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Graph.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panel_Graph_Container.add(lbl_Title_Graph);
		
		panel_Graph_View = new Rounded_Panel(25, Color.BLACK, 0);
		panel_Graph_View.setBounds(10, 30, 446, 336);
		panel_Graph_Container.add(panel_Graph_View);
        panel_Graph_View.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_Graph_View.setBackground(new Color(255, 255, 255));
        panel_Graph_View.setLayout(new BorderLayout());
		
        if (database_manager == null) {
            JPanel placeholder = new JPanel();
            placeholder.setBackground(Color.WHITE);
            panel_Graph_View.add(placeholder);
        } else {
            // Create and add actual graph panel at runtime
            graph_Panel = new Graph_Panel(database_manager.getReadingManager(), current_user, "water");
            graph_Panel.setBackground(new Color(255, 255, 255));
            panel_Graph_View.add(graph_Panel);
        }
        
        all_readings = getAllReadings();
        panel_Recent_Readings_Container = new Rounded_Panel();
		panel_Recent_Readings_Container.setBackground(new Color(255, 255, 255));
		panel_Recent_Readings_Container.setBounds(497, 114, 466, 377);
		add(panel_Recent_Readings_Container);
		panel_Recent_Readings_Container.setLayout(null);
		
		sP_Recent_Readings = new JScrollPane();
		sP_Recent_Readings.setBounds(5, 5, 456, 366);
		sP_Recent_Readings.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel_Recent_Readings_Container.add(sP_Recent_Readings);
		sP_Recent_Readings.setViewportView(all_readings);
		
		Headerpanel = new JPanel();
		Headerpanel.setBackground(new Color(255, 255, 255));
		sP_Recent_Readings.setColumnHeaderView(Headerpanel);
		Headerpanel.setLayout(null);
		Headerpanel.setPreferredSize(new Dimension(466, 70));
		
		lbl_Title_RecentReadings = new JLabel("Recent Readings");
		lbl_Title_RecentReadings.setBounds(0, 0, 466, 31);
		Headerpanel.add(lbl_Title_RecentReadings);
		lbl_Title_RecentReadings.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_RecentReadings.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
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
		lbl_Head_Rate.setBounds(240, 39, 78, 17);
		lbl_Head_Rate.setFont(new Font("Tahoma", Font.BOLD, 15));
		Headerpanel.add(lbl_Head_Rate);
		
		lbl_Head_TotalPrice = new JLabel("Total Price");
		lbl_Head_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_TotalPrice.setBounds(329, 39, 108, 17);
		lbl_Head_TotalPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		Headerpanel.add(lbl_Head_TotalPrice);
		
		Line = new JPanel();
		Line.setBorder(new LineBorder(new Color(0, 0, 0), 12));
		Line.setBounds(10, 64, 446, 3);
		Headerpanel.add(Line);
		
		panel_Current_Reading = new Rounded_Panel();
		panel_Current_Reading.setBackground(new Color(255, 255, 255));
		panel_Current_Reading.setLayout(null);
		panel_Current_Reading.setBounds(21, 509, 466, 168);
		add(panel_Current_Reading);
		
		lbl_Title_Current_Reading = new JLabel("Current Reading");
		lbl_Title_Current_Reading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Current_Reading.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Current_Reading.setBounds(42, 11, 393, 32);
		panel_Current_Reading.add(lbl_Title_Current_Reading);
		
		lbl_Water_Reading_Value = new JLabel();
		lbl_Water_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Water_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Water_Reading_Value.setBounds(144, 54, 100, 32);
		panel_Current_Reading.add(lbl_Water_Reading_Value);
		
		lbl_Water_Reading_Unit = new JLabel("m³");
		lbl_Water_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Water_Reading_Unit.setBounds(254, 55, 68, 32);
		panel_Current_Reading.add(lbl_Water_Reading_Unit);
		
		btn_Add_New_Reading = new Rounded_Button("Add New Reading", 25);
		btn_Add_New_Reading.setBackground(new Color(192, 192, 192));
		btn_Add_New_Reading.setForeground(Color.BLACK);
		btn_Add_New_Reading.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goToAddReading();
			}
			@Override
            public void mouseEntered(MouseEvent e) {
				btn_Add_New_Reading.setBackground(new Color(150, 150, 150));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            	btn_Add_New_Reading.setBackground(new Color(192, 192, 192));
            }
		});
		btn_Add_New_Reading.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_Add_New_Reading.setBounds(155, 125, 151, 34);
		panel_Current_Reading.add(btn_Add_New_Reading);
		
		panel_tips = new Rounded_Panel();
		panel_tips.setBackground(new Color(255, 255, 255));
		panel_tips.setLayout(null);
		panel_tips.setBounds(499, 509, 466, 168);
		add(panel_tips);
		
		lbl_Title_Tips = new JLabel("Water Saving Tips");
		lbl_Title_Tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Tips.setBounds(42, 11, 393, 32);
		panel_tips.add(lbl_Title_Tips);
		
		lbl_Tips1 = new JLabel("<html><ul><li>Turn off the tap while brushing your teeth to save gallons of water daily.</li></ul></html>");
		lbl_Tips1.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips1.setBounds(-25, 49, 502, 51);
		panel_tips.add(lbl_Tips1);
		lbl_Tips1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lbl_Tips2 = new JLabel("<html><ul><li>Fix leaky faucets and pipes promptly to prevent unnecessary water waste.</li></ul></html>");
		lbl_Tips2.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips2.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Tips2.setBounds(-25, 107, 502, 51);
		panel_tips.add(lbl_Tips2);
		
		setupData();
	}
	
	public void Refresh_Graph() {
		if (graph_Panel != null) {
			graph_Panel.refreshData();
		}
	}
	
	private void goToAddReading() {

		EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	Add_Reading_Panel add_reading_panel = new Add_Reading_Panel(
		            	    (JFrame) SwingUtilities.getWindowAncestor(Water_Panel.this),
		            	    database_manager, current_user, Water_Panel.this, "water"
		            	);
		            	add_reading_panel.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	public void Panel_Refresh() {
		all_readings = getAllReadings();
	    sP_Recent_Readings.setViewportView(all_readings);
	    getAllReadings();
	    setupData(); // Also update the current reading display
	    homepanel.home_Panel_Refresh();
	}
	
	public void setupData() {
		
		try {
			Reading water_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "water");
			
			if (water_reading == null) {
				lbl_Water_Reading_Value.setText("No Data");
			} else {
				lbl_Water_Reading_Value.setText(String.valueOf(water_reading.getReading()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JList<String> getAllReadings() {
		try {
			if (!database_manager.getReadingManager().isReadingExists(current_user, "water")) {
				JList<String> list = new JList<>(new String[] {"No readings found.", "Please add a reading."});
				list.setFont(new Font("monoFont", Font.PLAIN, 15));
				list.setPreferredSize(new Dimension(429, 448));
				list.setFixedCellHeight(30);
				return list;
			}
			List<Reading> all_readings = database_manager.getReadingManager().getAllReadingsByType(current_user, "water");
			
			String[] readings = new String[all_readings.size()];
			for (int i = 0; i < all_readings.size(); i++) {
				Reading reading = all_readings.get(i);
				readings[i] = String.format("    %-23s %-21s %-19s %-10s", reading.getDate(), reading.getReading() + "m³", reading.getRate() + "Php", reading.getTotal_Price() + "Php");
			}
			JList<String> list = new JList<>(readings);
			list.setFont(new Font("monoFont", Font.PLAIN, 13));
			list.setPreferredSize(new Dimension(429, 448));
			list.setFixedCellHeight(30);
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int response = javax.swing.JOptionPane.showConfirmDialog(null, "Do you want to eddit this reading?", "Edit Reading", javax.swing.JOptionPane.YES_NO_OPTION);
					if (response == javax.swing.JOptionPane.YES_OPTION) {
						EventQueue.invokeLater(new Runnable() {
					        public void run() {
					            try {
					            	Edit_Reading_Panel edit_reading_panel = new Edit_Reading_Panel(
				            			(JFrame) SwingUtilities.getWindowAncestor(Water_Panel.this),
					            	    database_manager, current_user, Water_Panel.this, "water"
					            	);
					            	edit_reading_panel.setVisible(true);

					            } catch (Exception e) {
					                e.printStackTrace();
					            }
					        }
					    });
					}
				}
			});
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new JList<>(new String[] {"Error fetching readings."});
		}
	}
}