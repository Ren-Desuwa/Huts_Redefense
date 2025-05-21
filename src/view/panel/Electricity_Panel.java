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
import visuals.Rounded_Panel;
import visuals.Rounded_Button;

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

public class Electricity_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Database and user fields
	private Database_Manager database_manager;
	private User current_user;
	
	// Main panel fields
	private JPanel panel_Title_Electricity_Consumption;
	private JPanel panel_Current_Reading;
	private JPanel panel_Graph_Container;
	private JPanel panel_tips;
	private JPanel panel_Recent_Readings_Container;
	
	// Scroll panel fields
	private JPanel panel_Header;
	private JPanel panel_Line;
	private JList<String> all_readings;
	
	//Graph panel field
	private JPanel panel_Graph_View;
	private Graph_Panel graph_Panel;
	private JLabel lbl_Title_Gaph;
	
	// Title Panel labels
	private JLabel lbl_Title_Electricity_Consumption;
	private JLabel lbl_SubTitle_Electricity_Consumption;
	private JLabel lbl_Date;
	private JLabel lbl_Time;
	
	// Scroll panel components
	private JScrollPane scrollpane_Recent_Readings;
	private JLabel lbl_Title_RecentReadings;
	private JLabel lbl_Head_Date;
	private JLabel lbl_Head_Readings;
	private JLabel lbl_Head_Rate;
	private JLabel lbl_Head_TotalPrice;
	
	// Current Reading panel components
	private JLabel lbl_Electricity_Reading_Value;
	private JLabel lbl_Title_Current_Reading;
	private JLabel lbl_Electricity_Reading_Unit;
	private JButton btn_Add_New_Reading;
	
	// Tips panel labels
	private JLabel lbl_Title_Tips;
	private JLabel lbl_Tips1;
	private JLabel lbl_Tips2;
	private JLabel lbl_Trend_Of_Reading_Electricity;
	
	
	/**
	 * Create the panel.
	 */
	public Electricity_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		
		setBackground(new Color(213, 213, 213));
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
		initialize_UI();
		setupData();
	}
	
	private void initialize_UI() {
		panel_Title_Electricity_Consumption = new Rounded_Panel();
		panel_Title_Electricity_Consumption.setBackground(new Color(255, 255, 255));
		panel_Title_Electricity_Consumption.setLayout(null);
		panel_Title_Electricity_Consumption.setBounds(21, 11, 944, 85);
		add(panel_Title_Electricity_Consumption);
		
		lbl_Title_Electricity_Consumption = new JLabel("Electricity Consumption");
		lbl_Title_Electricity_Consumption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Title_Electricity_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Title_Electricity_Consumption.setBounds(20, 0, 393, 54);
		panel_Title_Electricity_Consumption.add(lbl_Title_Electricity_Consumption);
		
		lbl_Date = new JLabel("Date");
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Date.setBounds(764, 11, 170, 54);
		lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		panel_Title_Electricity_Consumption.add(lbl_Date);
		
		lbl_Time = new JLabel("Time");
        lbl_Time.setVerticalAlignment(SwingConstants.TOP);
        lbl_Time.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Time.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Time.setBounds(764, 39, 170, 41);
        lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        panel_Title_Electricity_Consumption.add(lbl_Time);
		
		lbl_SubTitle_Electricity_Consumption = new JLabel("Track and manage your electricity usage ");
		lbl_SubTitle_Electricity_Consumption.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_SubTitle_Electricity_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_SubTitle_Electricity_Consumption.setBounds(20, 52, 393, 22);
		panel_Title_Electricity_Consumption.add(lbl_SubTitle_Electricity_Consumption);
		
		panel_Graph_Container = new Rounded_Panel();
		panel_Graph_Container.setBackground(new Color(255, 255, 255));
		panel_Graph_Container.setBounds(21, 114, 466, 377);
		add(panel_Graph_Container);
		panel_Graph_Container.setLayout(null);
		
		lbl_Title_Gaph = new JLabel("Monthly Electricity Expenses");
		lbl_Title_Gaph.setBounds(0, 0, 466, 32);
		lbl_Title_Gaph.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Gaph.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panel_Graph_Container.add(lbl_Title_Gaph);
		
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
            graph_Panel = new Graph_Panel(database_manager.getReadingManager(), current_user, "electricity");
            graph_Panel.setBackground(new Color(255, 255, 255));
            panel_Graph_View.add(graph_Panel);
        }
		
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
		
		lbl_Electricity_Reading_Value = new JLabel();
		lbl_Electricity_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Electricity_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Electricity_Reading_Value.setBounds(144, 54, 100, 32);
		panel_Current_Reading.add(lbl_Electricity_Reading_Value);
		
		lbl_Electricity_Reading_Unit = new JLabel("KwH");
		lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Electricity_Reading_Unit.setBounds(254, 55, 68, 32);
		panel_Current_Reading.add(lbl_Electricity_Reading_Unit);
		
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
		
		lbl_Trend_Of_Reading_Electricity = new JLabel("No avilable data");
		lbl_Trend_Of_Reading_Electricity.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Trend_Of_Reading_Electricity.setFont(new Font("Dialog", Font.PLAIN, 15));
		lbl_Trend_Of_Reading_Electricity.setBounds(97, 82, 261, 32);
		panel_Current_Reading.add(lbl_Trend_Of_Reading_Electricity);
		
		panel_tips = new Rounded_Panel();
		panel_tips.setBackground(new Color(255, 255, 255));
		panel_tips.setLayout(null);
		panel_tips.setBounds(499, 509, 466, 168);
		add(panel_tips);
		
		lbl_Title_Tips = new JLabel("Energy Saving Tips");
		lbl_Title_Tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Title_Tips.setBounds(42, 11, 393, 32);
		panel_tips.add(lbl_Title_Tips);
		
		lbl_Tips1 = new JLabel("<html><ul><li>Unplug chargers when not in use to avoid phantom energy consumption.</li></ul></html>");
		lbl_Tips1.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips1.setBounds(-34, 49, 502, 51);
		panel_tips.add(lbl_Tips1);
		lbl_Tips1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lbl_Tips2 = new JLabel("<html><ul><li>Use LED bulbs it consumes 75% less energy than incandescent bulbs.</li></ul></html>");
		lbl_Tips2.setVerticalAlignment(SwingConstants.TOP);
		lbl_Tips2.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Tips2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Tips2.setBounds(-34, 107, 502, 51);
		panel_tips.add(lbl_Tips2);
		
		all_readings = getAllReadings();
		panel_Recent_Readings_Container = new Rounded_Panel();
		panel_Recent_Readings_Container.setBackground(new Color(255, 255, 255));
		panel_Recent_Readings_Container.setBounds(497, 114, 466, 377);
		add(panel_Recent_Readings_Container);
		panel_Recent_Readings_Container.setLayout(null);
		
		scrollpane_Recent_Readings = new JScrollPane();
		scrollpane_Recent_Readings.setBounds(5, 5, 456, 366);
		scrollpane_Recent_Readings.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel_Recent_Readings_Container.add(scrollpane_Recent_Readings);
		scrollpane_Recent_Readings.setViewportView(all_readings);
		
		panel_Header = new JPanel();
		panel_Header.setBackground(new Color(255, 255, 255));
		scrollpane_Recent_Readings.setColumnHeaderView(panel_Header);
		panel_Header.setLayout(null);
		panel_Header.setPreferredSize(new Dimension(466, 70));
		
		lbl_Title_RecentReadings = new JLabel("Recent Readings");
		lbl_Title_RecentReadings.setBounds(0, 0, 466, 31);
		panel_Header.add(lbl_Title_RecentReadings);
		lbl_Title_RecentReadings.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_RecentReadings.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		lbl_Head_Date = new JLabel("Date");
		lbl_Head_Date.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Date.setBounds(10, 39, 78, 17);
		lbl_Head_Date.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_Header.add(lbl_Head_Date);
		
		lbl_Head_Readings = new JLabel("Readings");
		lbl_Head_Readings.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Readings.setBounds(124, 39, 78, 17);
		lbl_Head_Readings.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_Header.add(lbl_Head_Readings);
		
		lbl_Head_Rate = new JLabel("Rate");
		lbl_Head_Rate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_Rate.setBounds(240, 39, 78, 17);
		lbl_Head_Rate.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_Header.add(lbl_Head_Rate);
		
		lbl_Head_TotalPrice = new JLabel("Total Price");
		lbl_Head_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Head_TotalPrice.setBounds(329, 39, 108, 17);
		lbl_Head_TotalPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_Header.add(lbl_Head_TotalPrice);
		
		panel_Line = new JPanel();
		panel_Line.setBorder(new LineBorder(new Color(0, 0, 0), 12));
		panel_Line.setBounds(10, 64, 446, 3);
		panel_Header.add(panel_Line);
	}
	
	public void Refresh_Graph() {
		if (graph_Panel != null) {
			graph_Panel.refreshData();
		}
	}
	
	
	
	public void Panel_Refresh() {
		all_readings = getAllReadings();
	    scrollpane_Recent_Readings.setViewportView(all_readings);
	    getAllReadings();
	    setupData(); // Also update the current reading display
	}
	
	public void setupData() {
		
		try {
			Reading electricity_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "electricity");
			
			if (electricity_reading == null) {
				lbl_Electricity_Reading_Value.setText("No Data");
			} else {
				lbl_Electricity_Reading_Value.setText(String.valueOf(electricity_reading.getReading()));
				database_manager.getReadingManager().updateReadingLabel(current_user,electricity_reading, lbl_Electricity_Reading_Value, lbl_Trend_Of_Reading_Electricity, "electricity");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JList<String> getAllReadings() {
	    MouseAdapter listClickListener = new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            JList<?> list = (JList<?>) e.getSource();
	            int selectedIndex = list.getSelectedIndex();
	            
	            if (selectedIndex >= 0) {
	                int response = javax.swing.JOptionPane.showConfirmDialog(null, 
	                    "Do you want to edit this reading?", "Edit Reading", 
	                    javax.swing.JOptionPane.YES_NO_OPTION);
	                    
	                if (response == javax.swing.JOptionPane.YES_OPTION) {
	                    EventQueue.invokeLater(new Runnable() {
	                        public void run() {
	                            try {
	                                List<Reading> allReadings = database_manager.getReadingManager().getAllReadingsByType(current_user, "electricity");
	                                Reading selectedReading = null;
	                                
	                                if (selectedIndex < allReadings.size()) {
	                                    selectedReading = allReadings.get(selectedIndex);
	                                }
	                                
	                                Edit_Reading_Panel edit_reading_panel = new Edit_Reading_Panel(
	                                    (JFrame) SwingUtilities.getWindowAncestor(Electricity_Panel.this),
	                                    database_manager, current_user, Electricity_Panel.this, "electricity", selectedReading
	                                );
	                                edit_reading_panel.setVisible(true);
	                            } catch (Exception ex) {
	                                ex.printStackTrace();
	                            }
	                        }
	                    });
	                }
	            }
	        }
	    };
	    
	    return database_manager.getReadingManager().getReadingsAsJList(current_user, "electricity", listClickListener);
	}
	
	private void goToAddReading() {

		EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	Add_Reading_Panel add_reading_panel = new Add_Reading_Panel(
		            	    (JFrame) SwingUtilities.getWindowAncestor(Electricity_Panel.this),
		            	    database_manager, current_user, Electricity_Panel.this, "electricity"
		            	);
		            	add_reading_panel.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
}
