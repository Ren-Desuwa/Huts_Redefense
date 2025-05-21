package view.panel;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JPanel;


import database.Database_Manager;
import model.Reading;
import model.User;
import view.panel.misc.Add_Reading_Panel;
import view.panel.misc.Utility_Tips_Manager;
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

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Gas_Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Database and User
	private Database_Manager database_manager;
	private Utility_Tips_Manager utility_Tips_Manager = Utility_Tips_Manager.getInstance();
	private User current_user;
	
	// Main Panels
	private JPanel panel_Title_Gas_Consumption;
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
	
	
	private JLabel lbl_Head_Date;

	// Title panel labels
	private JLabel lbl_Title_Gas_Consumption;
	private JLabel lbl_SubTitle_Gas_Consumption;
	private JLabel lbl_Time;
	private JLabel lbl_Date;
	
	// Scroll panel components
	private JLabel lbl_Title_RecentReadings;
	private JLabel lbl_Head_Readings;
	private JLabel lbl_Head_Rate;
	private JLabel lbl_Head_TotalPrice;
	private JScrollPane sP_Recent_Readings;
	
	// Current Reading components
	private JLabel lbl_Title_Current_Reading;
	private JLabel lbl_Gas_Reading_Value;
	private JLabel lbl_Gas_Reading_Unit;
	private JLabel lbl_Trend_Of_Reading_Gas;
	private JButton btn_Add_New_Reading;
	
	// Tips panel labels
	private JLabel lbl_Title_Tips;
	private JLabel lbl_Tips_1;
	
	public Gas_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		
		setBackground(new Color(213, 213, 213));
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
		initialize_UI();
		create_Actions_Listeners();
		setupData();
	}
	
	private void initialize_UI() {

	//==============================================================================================    
	// UI CREATION - HEADER SECTION
	//==============================================================================================

    panel_Title_Gas_Consumption = new Rounded_Panel();
    panel_Title_Gas_Consumption.setBackground(new Color(255, 255, 255));
    panel_Title_Gas_Consumption.setLayout(null);
    panel_Title_Gas_Consumption.setBounds(21, 11, 944, 85);
    add(panel_Title_Gas_Consumption);

    lbl_Title_Gas_Consumption = new JLabel("Gas Consumption");
    lbl_Title_Gas_Consumption.setHorizontalAlignment(SwingConstants.LEFT);
    lbl_Title_Gas_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
    lbl_Title_Gas_Consumption.setBounds(20, 0, 393, 54);
    panel_Title_Gas_Consumption.add(lbl_Title_Gas_Consumption);

    lbl_Date = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    lbl_Date.setVerticalAlignment(SwingConstants.TOP);
    lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
    lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
    lbl_Date.setBounds(764, 11, 170, 54);
    panel_Title_Gas_Consumption.add(lbl_Date);

    lbl_Time = new JLabel(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
    lbl_Time.setVerticalAlignment(SwingConstants.TOP);
    lbl_Time.setHorizontalAlignment(SwingConstants.RIGHT);
    lbl_Time.setFont(new Font("Tahoma", Font.PLAIN, 20));
    lbl_Time.setBounds(764, 39, 170, 41);
    panel_Title_Gas_Consumption.add(lbl_Time);

    lbl_SubTitle_Gas_Consumption = new JLabel("Track and manage your gas usage ");
    lbl_SubTitle_Gas_Consumption.setHorizontalAlignment(SwingConstants.LEFT);
    lbl_SubTitle_Gas_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 17));
    lbl_SubTitle_Gas_Consumption.setBounds(20, 52, 393, 22);
    panel_Title_Gas_Consumption.add(lbl_SubTitle_Gas_Consumption);

    //==============================================================================================
    // UI CREATION - MAIN SECTION - GRAPH
    //==============================================================================================
    
    panel_Graph_Container = new Rounded_Panel();
    panel_Graph_Container.setBackground(new Color(255, 255, 255));
    panel_Graph_Container.setBounds(21, 114, 466, 377);
    panel_Graph_Container.setLayout(null);
    add(panel_Graph_Container);

    lbl_Title_Graph = new JLabel("Monthly Gas Expenses");
    lbl_Title_Graph.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_Title_Graph.setFont(new Font("Tahoma", Font.PLAIN, 25));
    lbl_Title_Graph.setBounds(0, 0, 466, 32);
    panel_Graph_Container.add(lbl_Title_Graph);

    panel_Graph_View = new Rounded_Panel(25, Color.BLACK, 0);
    panel_Graph_View.setBounds(10, 30, 446, 336);
    panel_Graph_View.setBorder(new EmptyBorder(5, 5, 5, 5));
    panel_Graph_View.setBackground(Color.WHITE);
    panel_Graph_View.setLayout(new BorderLayout());
    panel_Graph_Container.add(panel_Graph_View);

    if (database_manager == null) {
        JPanel placeholder = new JPanel();
        placeholder.setBackground(Color.WHITE);
        panel_Graph_View.add(placeholder);
    } else {
        graph_Panel = new Graph_Panel(database_manager.getReadingManager(), current_user, "gas");
        graph_Panel.setBackground(Color.WHITE);
        panel_Graph_View.add(graph_Panel);
    }

    //==============================================================================================
    // UI CREATION - MAIN SECTION - RECENT READINGS
    //==============================================================================================
    
    panel_Recent_Readings_Container = new Rounded_Panel();
    panel_Recent_Readings_Container.setBackground(new Color(255, 255, 255));
    panel_Recent_Readings_Container.setBounds(497, 114, 466, 377);
    panel_Recent_Readings_Container.setLayout(null);
    add(panel_Recent_Readings_Container);

    sP_Recent_Readings = new JScrollPane();
    sP_Recent_Readings.setBounds(5, 5, 456, 366);
    sP_Recent_Readings.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    sP_Recent_Readings.setViewportView(all_readings);
    panel_Recent_Readings_Container.add(sP_Recent_Readings);

    //===============================================================================================
    // UI CREATION - MAIN SECTION - RECENT READINGS - HEADER
    //===============================================================================================
    
    Headerpanel = new JPanel();
    Headerpanel.setBackground(Color.WHITE);
    Headerpanel.setPreferredSize(new Dimension(466, 70));
    Headerpanel.setLayout(null);
    sP_Recent_Readings.setColumnHeaderView(Headerpanel);

    lbl_Title_RecentReadings = new JLabel("Recent Readings");
    lbl_Title_RecentReadings.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_Title_RecentReadings.setFont(new Font("Tahoma", Font.PLAIN, 25));
    lbl_Title_RecentReadings.setBounds(0, 0, 466, 31);
    Headerpanel.add(lbl_Title_RecentReadings);

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
    lbl_Head_Rate.setBounds(240, 39, 78, 17);
    Headerpanel.add(lbl_Head_Rate);

    lbl_Head_TotalPrice = new JLabel("Total Price");
    lbl_Head_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_Head_TotalPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
    lbl_Head_TotalPrice.setBounds(329, 39, 108, 17);
    Headerpanel.add(lbl_Head_TotalPrice);

    Line = new JPanel();
    Line.setBorder(new LineBorder(Color.BLACK, 12));
    Line.setBounds(10, 64, 446, 3);
    Headerpanel.add(Line);

    //===============================================================================================
    // UI CREATION - MAIN SECTION - CURRENT READING
    //===============================================================================================
    
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

    lbl_Gas_Reading_Value = new JLabel();
    lbl_Gas_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_Gas_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
    lbl_Gas_Reading_Value.setBounds(144, 54, 100, 32);
    panel_Current_Reading.add(lbl_Gas_Reading_Value);

    lbl_Gas_Reading_Unit = new JLabel("Qty");
    lbl_Gas_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lbl_Gas_Reading_Unit.setBounds(254, 55, 68, 32);
    panel_Current_Reading.add(lbl_Gas_Reading_Unit);

    btn_Add_New_Reading = new Rounded_Button("Add New Reading", 25);
    btn_Add_New_Reading.setBackground(Color.LIGHT_GRAY);
    btn_Add_New_Reading.setForeground(Color.BLACK);
    btn_Add_New_Reading.setFont(new Font("Tahoma", Font.BOLD, 10));
    btn_Add_New_Reading.setBounds(155, 125, 151, 34);
    panel_Current_Reading.add(btn_Add_New_Reading);

    lbl_Trend_Of_Reading_Gas = new JLabel("No available data");
    lbl_Trend_Of_Reading_Gas.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_Trend_Of_Reading_Gas.setFont(new Font("Dialog", Font.PLAIN, 15));
    lbl_Trend_Of_Reading_Gas.setBounds(97, 82, 261, 32);
    panel_Current_Reading.add(lbl_Trend_Of_Reading_Gas);

    //===============================================================================================
    // UI CREATION - MAIN SECTION - TIPS
    //===============================================================================================
    
    panel_tips = new Rounded_Panel();
    panel_tips.setBackground(new Color(255, 255, 255));
    panel_tips.setLayout(null);
    panel_tips.setBounds(499, 509, 466, 168);
    add(panel_tips);

    lbl_Title_Tips = new JLabel("Gas Saving Tips");
    lbl_Title_Tips.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
    lbl_Title_Tips.setForeground(new Color(255,77,0));
    lbl_Title_Tips.setBounds(42, 11, 393, 32);
    panel_tips.add(lbl_Title_Tips);

    lbl_Tips_1 = new JLabel("<html><ul><li>Cover pots with lids while cooking to trap heat and reduce cooking time.</li></ul></html>");
    lbl_Tips_1.setVerticalAlignment(SwingConstants.TOP);
    lbl_Tips_1.setHorizontalAlignment(SwingConstants.LEFT);
    lbl_Tips_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
    lbl_Tips_1.setBounds(-17, 38, 473, 119);
    panel_tips.add(lbl_Tips_1);

    //===============================================================================================
    // UI CREATION - TIMERS
    //===============================================================================================
    
    Timer clock_timer = new Timer(60_000, e -> {
        lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm a")));
    });
    LocalTime now = LocalTime.now();
    int initialDelay = (60 - now.getSecond()) * 1000 - now.getNano() / 1_000_000;
    clock_timer.setInitialDelay(initialDelay);
    clock_timer.start();

    Timer tips_timer = new Timer(30_000, e -> {
        lbl_Tips_1.setText("<html><ul><li>" + utility_Tips_Manager.getRandomTip("gas") + "</li><br>"
                         + "<li>" + utility_Tips_Manager.getRandomTip("gas") + "</li></ul></html>");
    });
    tips_timer.setInitialDelay(0);
    tips_timer.start();
}

	
	private void create_Actions_Listeners() {
		btn_Add_New_Reading.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openAddReading();
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
		
		lbl_Title_Tips.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_Tips_1.setText("<html><ul><li>" + utility_Tips_Manager.getRandomTip("electricity") + "</li><br>"
							   			   + "<li>" + utility_Tips_Manager.getRandomTip("electricity") + "</li></ul></html>");
			}
		});
	}
	
	public void Refresh_Graph() {
		if (graph_Panel != null) {
			graph_Panel.refreshData();
		}
	}
	
	private void openAddReading() {
		EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	Add_Reading_Panel add_reading_panel = new Add_Reading_Panel(
		            	    (JFrame) SwingUtilities.getWindowAncestor(Gas_Panel.this),
		            	    database_manager, current_user, Gas_Panel.this, "gas"
		            	);
		            	add_reading_panel.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	public void Panel_Refresh() {
	    setupData(); // Also update the current reading display
	}
	
	private void setupData() {
		all_readings = database_manager.getReadingManager().getReadingsAsJList(this, database_manager, current_user, "gas");
		sP_Recent_Readings.setViewportView(all_readings);
		
		try {
			Reading gas_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "gas");
			
			if (gas_reading == null) {
				lbl_Gas_Reading_Value.setText("No Data");
			} else {
				lbl_Gas_Reading_Value.setText(String.valueOf(gas_reading.getReading()));
				database_manager.getReadingManager().updateReadingLabel(current_user,gas_reading, lbl_Gas_Reading_Value, lbl_Trend_Of_Reading_Gas, "gas");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}