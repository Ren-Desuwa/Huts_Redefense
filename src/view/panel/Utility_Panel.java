package view.panel;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JPanel;

import database.Database_Manager;
import database.Utility_Tips_Manager;
import model.Reading;
import model.User;
import view.panel.misc.Add_Reading_Window;
import view.panel.misc.Graph_Panel;
import visuals.RoundedPanel;
import visuals.RoundedButton;

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
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 * A modular panel for displaying utility consumption data (electricity, gas, water, etc.)
 * This panel handles displaying current readings, historical data in a graph, and utility tips.
 */
public class Utility_Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Database and user fields
    private Database_Manager database_manager;
    private Utility_Tips_Manager utility_Tips_Manager = Utility_Tips_Manager.getInstance();
    private User current_user;
    private String utility_type; // "electricity", "gas", "water".
    private String field = "reading"; // Default field to display in graph (can be "reading", "rate", or "total")
    private Map<Month, Double> data; // Monthly data for the graph
    
    
    // Panel configuration
    private String panel_title;
    private String panel_subtitle;
    private Color tips_title_color;
    
    // Year navigation fields
    private int current_graph_year;
    private int[] years;
    boolean hasNextYear = false;
    boolean hasPreviousYear = false;
    
    // Main panel fields
    private JPanel panel_Title;
    private JPanel panel_Current_Reading;
    private JPanel panel_Graph_Container;
    private JPanel panel_tips;
    private JPanel panel_Recent_Readings_Container;
    
    // Scroll panel fields
    private JPanel panel_Header;
    private JPanel panel_Line;
    private JList<String> all_readings;
    
    // Graph panel field
    private JPanel panel_Graph_View;
    private JLabel lbl_Prev_Button;
    private JLabel lbl_CurrentYear;
	private JLabel lbl_Next_Button;
	private JPanel panel_Graph_Button_Container;
    private Graph_Panel graph_Panel;
    private JLabel lbl_Title_Graph;
    
    // Title Panel labels
    private JLabel lbl_Title;
    private JLabel lbl_SubTitle;
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
    private JLabel lbl_Reading_Value;
    private JLabel lbl_Title_Current_Reading;
    private JLabel lbl_Reading_Unit;
    private JButton btn_Add_New_Reading;
    private JLabel lbl_Trend_Of_Reading;
    
    // Tips panel labels
    private JLabel lbl_Title_Tips;
    private JLabel lbl_Tips_1;
    private RoundedPanel panel_View_Buttons;
    private JLabel lbl_View_Data;
    private RoundedPanel panel_Reading_Button;
    private JRadioButton rdbtn_Reading;
    private JLabel lbl_Reading;
    private RoundedPanel panel_Rate_Button;
    private JRadioButton rdbtn_Rate;
    private JLabel lbl_Rate;
    private JRadioButton rdbtn_Price;
    private JLabel lbl_Price;
    private ButtonGroup rdgroup_View_Buttons;
    private RoundedPanel panel_Price_Button;
    
    public Utility_Panel(Database_Manager database_manager, User current_user, 
            String utility_type, String panel_title, String panel_subtitle, 
            String tips_title, Color tips_title_color) {
        
        this.database_manager = database_manager;
        this.current_user = current_user;
        this.utility_type = utility_type;
        this.panel_title = panel_title;
        this.panel_subtitle = panel_subtitle;
        this.tips_title_color = tips_title_color;
        this.current_graph_year = LocalDate.now().getYear();
        
        
        setBackground(new Color(213, 213, 213));
        setPreferredSize(new Dimension(986, 688));
        setLayout(null);
        
        initialize_UI(tips_title);
        create_Actions_Listeners();
        
        setupData();
    }
    
    private void initialize_UI(String tips_title) {
        //==============================================================================================
        // UI CREATION - HEADER SECTION
        //==============================================================================================

    	// Create the main title panel
        panel_Title = new RoundedPanel();
        panel_Title.setBackground(new Color(255, 255, 255));
        panel_Title.setBounds(21, 11, 944, 85);
        panel_Title.setLayout(null);
        add(panel_Title);

        // Create the title label and date/time labels
        lbl_Title = new JLabel(panel_title);
        lbl_Title.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Title.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lbl_Title.setBounds(20, 0, 393, 54);
        panel_Title.add(lbl_Title);

        // Create the date and time labels
        lbl_Date = new JLabel();
        lbl_Date.setVerticalAlignment(SwingConstants.TOP);
        lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Date.setBounds(764, 11, 170, 54);
        lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panel_Title.add(lbl_Date);

        // Create the time label
        lbl_Time = new JLabel();
        lbl_Time.setVerticalAlignment(SwingConstants.TOP);
        lbl_Time.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Time.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Time.setBounds(764, 39, 170, 41);
        lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        panel_Title.add(lbl_Time);

        // Create the subtitle label
        lbl_SubTitle = new JLabel(panel_subtitle);
        lbl_SubTitle.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_SubTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbl_SubTitle.setBounds(20, 52, 393, 22);
        panel_Title.add(lbl_SubTitle);

        //==============================================================================================
        // UI CREATION - CONTENT PANELS - GRAPH
        //==============================================================================================
        
        // Create the graph container panel
        panel_Graph_Container = new RoundedPanel();
        panel_Graph_Container.setBackground(new Color(255, 255, 255));
        panel_Graph_Container.setBounds(21, 114, 466, 377);
        panel_Graph_Container.setLayout(null);
        add(panel_Graph_Container);

        // Create the graph title label and graph view panel
        lbl_Title_Graph = new JLabel("Monthly " + panel_title.replace("Consumption", "Expenses"));
	    lbl_Title_Graph.setHorizontalAlignment(SwingConstants.CENTER);
	    lbl_Title_Graph.setFont(new Font("Tahoma", Font.PLAIN, 25));
	    lbl_Title_Graph.setBounds(0, 0, 466, 32);
	    panel_Graph_Container.add(lbl_Title_Graph);

	    // Create the graph view panel
	    panel_Graph_View = new JPanel();
	    panel_Graph_View.setBackground(Color.WHITE);
	    panel_Graph_View.setBorder(new EmptyBorder(5, 5, 5, 5));
	    panel_Graph_View.setLayout(new BorderLayout());
	    panel_Graph_View.setBounds(10, 74, 446, 292);
	    panel_Graph_Container.add(panel_Graph_View);

	    // Create the graph button container panel and buttons
	    panel_Graph_Button_Container = new JPanel();
	    panel_Graph_Button_Container.setBounds(0, 37, 466, 46);
	    panel_Graph_Container.add(panel_Graph_Button_Container);
	    panel_Graph_Button_Container.setOpaque(false);
	    panel_Graph_Button_Container.setPreferredSize(new Dimension(446, 70));
	    panel_Graph_Button_Container.setLayout(null);

	    // Create the previous and next buttons, and current year label
	    lbl_Prev_Button = new JLabel("<");
	    lbl_Prev_Button.setVisible(false);
	    lbl_Prev_Button.setVerticalAlignment(SwingConstants.TOP);
	    lbl_Prev_Button.setHorizontalAlignment(SwingConstants.CENTER);
	    lbl_Prev_Button.setFont(new Font("Tahoma", Font.PLAIN, 35));
	    lbl_Prev_Button.setBounds(59, -8, 80, 48);
	    lbl_Prev_Button.setForeground(new Color(220, 220, 220));
	    panel_Graph_Button_Container.add(lbl_Prev_Button);

	    // Create the next button
	    lbl_Next_Button = new JLabel(">");
	    lbl_Next_Button.setVisible(false);
	    lbl_Next_Button.setVerticalAlignment(SwingConstants.TOP);
	    lbl_Next_Button.setHorizontalAlignment(SwingConstants.CENTER);
	    lbl_Next_Button.setFont(new Font("Tahoma", Font.PLAIN, 35));
	    lbl_Next_Button.setBounds(315, -8, 80, 48);
	    lbl_Next_Button.setForeground(new Color(220, 220, 220));
	    panel_Graph_Button_Container.add(lbl_Next_Button);

	    // Create the current year label
	    lbl_CurrentYear = new JLabel(String.valueOf(current_graph_year));
	    lbl_CurrentYear.setHorizontalAlignment(SwingConstants.CENTER);
	    lbl_CurrentYear.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lbl_CurrentYear.setBounds(170, 0, 111, 32);
	    panel_Graph_Button_Container.add(lbl_CurrentYear);

	    // Add graph panel or placeholder based on database manager availability
	    graph_Panel = new Graph_Panel("reading", utility_type);
	    graph_Panel.setBackground(new Color(255, 255, 255));
	    panel_Graph_View.add(graph_Panel);


        //==============================================================================================
        // UI CREATION - CONTENT PANELS - CURRENT READING 
        //==============================================================================================
        
	    // Create the current reading panel
        panel_Current_Reading = new RoundedPanel();
        panel_Current_Reading.setBackground(new Color(255, 255, 255));
        panel_Current_Reading.setLayout(null);
        panel_Current_Reading.setBounds(610, 509, 355, 168);
        add(panel_Current_Reading);

        // Create the title label and reading value labels
        lbl_Title_Current_Reading = new JLabel("Current Expenses");
        lbl_Title_Current_Reading.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_Current_Reading.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lbl_Title_Current_Reading.setBounds(10, 11, 335, 32);
        panel_Current_Reading.add(lbl_Title_Current_Reading);

        // Create the reading value label and unit label
        lbl_Reading_Value = new JLabel();
        lbl_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Reading_Value.setBounds(88, 54, 118, 32);
        panel_Current_Reading.add(lbl_Reading_Value);

        // Create the reading unit label
        lbl_Reading_Unit = new JLabel("₱");
        lbl_Reading_Unit.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Reading_Unit.setBounds(216, 54, 129, 32);
        panel_Current_Reading.add(lbl_Reading_Unit);

        // Create the trend of reading label and add new reading button
        lbl_Trend_Of_Reading = new JLabel("No available data");
        lbl_Trend_Of_Reading.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading.setBounds(10, 82, 335, 32);
        panel_Current_Reading.add(lbl_Trend_Of_Reading);

        // Create the add new reading button
        btn_Add_New_Reading = new RoundedButton("Add Reading", 25);
        btn_Add_New_Reading.setBackground(new Color(192, 192, 192));
        btn_Add_New_Reading.setForeground(Color.BLACK);
        btn_Add_New_Reading.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_Add_New_Reading.setBounds(109, 117, 137, 40);
        panel_Current_Reading.add(btn_Add_New_Reading);

        //==============================================================================================
        // UI CREATION - CONTENT PANELS - TIPS
        //==============================================================================================
        
        // Create the tips panel
        panel_tips = new RoundedPanel();
        panel_tips.setBackground(new Color(255, 255, 255));
        panel_tips.setLayout(null);
        panel_tips.setBounds(224, 509, 376, 168);
        add(panel_tips);

        // Create the title label and tips label
        lbl_Title_Tips = new JLabel(tips_title);
        lbl_Title_Tips.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lbl_Title_Tips.setForeground(tips_title_color);
        lbl_Title_Tips.setBounds(10, 11, 356, 32);
        panel_tips.add(lbl_Title_Tips);

        // Create the tips label with a random tip
        lbl_Tips_1 = new JLabel("<html><ul><li>" + utility_Tips_Manager.getRandomTip(utility_type) + "</li></ul></html>");
        lbl_Tips_1.setVerticalAlignment(SwingConstants.TOP);
        lbl_Tips_1.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Tips_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbl_Tips_1.setBounds(-17, 38, 383, 119);
        panel_tips.add(lbl_Tips_1);

        //==============================================================================================
        // UI CREATION - CONTENT PANELS - RECENT READINGS
        //==============================================================================================
        
        // Create the recent readings container panel
        panel_Recent_Readings_Container = new RoundedPanel();
        panel_Recent_Readings_Container.setBackground(new Color(255, 255, 255));
        panel_Recent_Readings_Container.setBounds(497, 114, 466, 377);
        panel_Recent_Readings_Container.setLayout(null);
        add(panel_Recent_Readings_Container);

        // Create the scroll pane for recent readings
        scrollpane_Recent_Readings = new JScrollPane();
        scrollpane_Recent_Readings.setBounds(5, 5, 456, 366);
        scrollpane_Recent_Readings.setBorder(BorderFactory.createEmptyBorder());
        panel_Recent_Readings_Container.add(scrollpane_Recent_Readings);
        
        //===============================================================================================
        // UI CREATION - SCROLLPANE HEADER
        //===============================================================================================

        // Create the header panel for the scroll pane
        panel_Header = new JPanel();
        panel_Header.setBackground(Color.WHITE);
        panel_Header.setPreferredSize(new Dimension(466, 70));
        panel_Header.setLayout(null);
        scrollpane_Recent_Readings.setColumnHeaderView(panel_Header);

        // Create the header labels for recent readings
        lbl_Title_RecentReadings = new JLabel("Recent Readings");
        lbl_Title_RecentReadings.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_RecentReadings.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lbl_Title_RecentReadings.setBounds(0, 0, 466, 31);
        panel_Header.add(lbl_Title_RecentReadings);

        // Create the header labels for date, readings, rate, and total price
        lbl_Head_Date = new JLabel("Date");
        lbl_Head_Date.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Head_Date.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbl_Head_Date.setBounds(10, 39, 78, 17);
        panel_Header.add(lbl_Head_Date);

        // Create the header labels for readings, rate, and total price
        lbl_Head_Readings = new JLabel("Readings");
        lbl_Head_Readings.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Head_Readings.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbl_Head_Readings.setBounds(124, 39, 78, 17);
        panel_Header.add(lbl_Head_Readings);

        // Create the header labels for rate and total price
        lbl_Head_Rate = new JLabel("Rate");
        lbl_Head_Rate.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Head_Rate.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbl_Head_Rate.setBounds(240, 39, 78, 17);
        panel_Header.add(lbl_Head_Rate);

        // Create the header labels for total price
        lbl_Head_TotalPrice = new JLabel("Total Price");
        lbl_Head_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Head_TotalPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbl_Head_TotalPrice.setBounds(329, 39, 108, 17);
        panel_Header.add(lbl_Head_TotalPrice);

        // Create a line panel to separate the header from the content
        panel_Line = new JPanel();
        panel_Line.setBorder(new LineBorder(Color.BLACK, 12));
        panel_Line.setBounds(10, 64, 446, 3);
        panel_Header.add(panel_Line);

        //==============================================================================================
        // UI CREATION - TIMERS
        //==============================================================================================

        // Timer to update the date and time labels every minute
        Timer clock_timer = new Timer(60_000, e -> {
            lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        });
        LocalTime now = LocalTime.now();
        // Calculate the initial delay to start the timer at the next minute
        int initialDelay = (60 - now.getSecond()) * 1000 - now.getNano() / 1_000_000;
        clock_timer.setInitialDelay(initialDelay);
        clock_timer.start();

        // Timer to update the date label every day at midnight
        Timer tips_timer = new Timer(30_000, e -> {
            lbl_Tips_1.setText("<html><ul><li>" + utility_Tips_Manager.getRandomTip(utility_type) + "</li><br>"
                             + "<li>" + utility_Tips_Manager.getRandomTip(utility_type) + "</li></ul></html>");
        });
        tips_timer.setInitialDelay(0);
        tips_timer.start();
        
        //==============================================================================================
        // UI Creation - VIEW BUTTONS
        //==============================================================================================
        
        // Create the view buttons panel
        panel_View_Buttons = new RoundedPanel();
        panel_View_Buttons.setLayout(null);
        panel_View_Buttons.setBackground(Color.WHITE);
        panel_View_Buttons.setBounds(21, 509, 193, 168); // Keep as-is
        add(panel_View_Buttons);

        // Title label
        lbl_View_Data = new JLabel("View Data");
        lbl_View_Data.setForeground(Color.BLACK);
        lbl_View_Data.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_View_Data.setBounds(10, 11, 173, 25);
        panel_View_Buttons.add(lbl_View_Data);

        // Reading Panel
        panel_Reading_Button = new RoundedPanel(15, Color.BLACK, 0);
        panel_Reading_Button.setLayout(null);
        panel_Reading_Button.setBackground(new Color(220, 220, 220));
        panel_Reading_Button.setBounds(10, 46, 173, 32); // taller height
        panel_View_Buttons.add(panel_Reading_Button);

        // Create the radio button group for view buttons
        rdgroup_View_Buttons = new ButtonGroup();
        
        // Reading Button
        rdbtn_Reading = new JRadioButton();
        rdbtn_Reading.setSelected(true);
        rdbtn_Reading.setOpaque(false);
        rdbtn_Reading.setBounds(5, 5, 21, 23);
        panel_Reading_Button.add(rdbtn_Reading);

        // Reading Label
        lbl_Reading = new JLabel("Readings");
        lbl_Reading.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Reading.setBounds(30, 5, 133, 23);
        panel_Reading_Button.add(lbl_Reading);

        // Rate Panel
        panel_Rate_Button = new RoundedPanel(15, Color.BLACK, 0);
        panel_Rate_Button.setLayout(null);
        panel_Rate_Button.setBackground(new Color(220, 220, 220));
        panel_Rate_Button.setBounds(10, 83, 173, 32);
        panel_View_Buttons.add(panel_Rate_Button);

        // Rate Button
        rdbtn_Rate = new JRadioButton();
        rdbtn_Rate.setOpaque(false);
        rdbtn_Rate.setBounds(5, 5, 21, 23);
        panel_Rate_Button.add(rdbtn_Rate);

        // Rate Label
        lbl_Rate = new JLabel("Rate");
        lbl_Rate.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Rate.setBounds(30, 5, 133, 23);
        panel_Rate_Button.add(lbl_Rate);

        // Price Panel
        panel_Price_Button = new RoundedPanel(15, Color.BLACK, 0);
        panel_Price_Button.setLayout(null);
        panel_Price_Button.setBackground(new Color(220, 220, 220));
        panel_Price_Button.setBounds(10, 120, 173, 32);
        panel_View_Buttons.add(panel_Price_Button);

        // Price Button
        rdbtn_Price = new JRadioButton();
        rdbtn_Price.setOpaque(false);
        rdbtn_Price.setBounds(5, 5, 21, 23);
        panel_Price_Button.add(rdbtn_Price);

        // Price Label
        lbl_Price = new JLabel("Price");
        lbl_Price.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Price.setBounds(30, 5, 133, 23);
        panel_Price_Button.add(lbl_Price);
        
        // Add radio buttons to the button group
        rdgroup_View_Buttons.add(rdbtn_Reading);
        rdgroup_View_Buttons.add(rdbtn_Rate);
        rdgroup_View_Buttons.add(rdbtn_Price);

    }
    
    private void create_Actions_Listeners() {
    	lbl_Prev_Button.addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent e) {
    	        if (hasPreviousYear) {
    	            current_graph_year--;
    	            lbl_CurrentYear.setText(String.valueOf(current_graph_year));
    	            setupData();
    	        }
    	    }

    	    @Override
    	    public void mouseEntered(MouseEvent e) { if (hasPreviousYear) { lbl_Prev_Button.setForeground(new Color(90, 90, 90)); } }
    	    @Override
    	    public void mouseExited(MouseEvent e) { if (hasPreviousYear) { lbl_Prev_Button.setForeground(new Color(170, 170, 170)); } }
    	});
		
    	lbl_Next_Button.addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent e) {
    	        if (hasNextYear) {
    	            current_graph_year++;
    	            lbl_CurrentYear.setText(String.valueOf(current_graph_year));
    	            setupData();
    	        }
    	    }
    	    @Override
    	    public void mouseEntered(MouseEvent e) {
    	        if (hasNextYear) {
    	            lbl_Next_Button.setForeground(new Color(90, 90, 90));
    	        }
    	    }
    	    @Override
    	    public void mouseExited(MouseEvent e) {
    	        if (hasNextYear) {
    	            lbl_Next_Button.setForeground(new Color(170, 170, 170));
    	        }
    	    }
    	});

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
        
        lbl_Title_Tips.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lbl_Tips_1.setText("<html><ul><li>" + utility_Tips_Manager.getRandomTip(utility_type) + "</li><br>"
                                       + "<li>" + utility_Tips_Manager.getRandomTip(utility_type) + "</li></ul></html>");
            }
        });
        
        // Assign panel listeners
        panel_Reading_Button.addMouseListener(mouseListener(panel_Reading_Button, "reading", rdbtn_Reading));
        panel_Rate_Button.addMouseListener(mouseListener(panel_Rate_Button, "rate", rdbtn_Rate));
        panel_Price_Button.addMouseListener(mouseListener(panel_Price_Button, "total", rdbtn_Price));
        
        // Assign radio button listeners
        rdbtn_Reading.addMouseListener(mouseListener(panel_Reading_Button, "reading", rdbtn_Reading));
        rdbtn_Rate.addMouseListener(mouseListener(panel_Rate_Button, "rate", rdbtn_Rate));
        rdbtn_Price.addMouseListener(mouseListener(panel_Price_Button, "total", rdbtn_Price));

        // Assign to labels
        lbl_Reading.addMouseListener(mouseListener(panel_Reading_Button, "reading", rdbtn_Reading));
        lbl_Rate.addMouseListener(mouseListener(panel_Rate_Button, "rate", rdbtn_Rate));
        lbl_Price.addMouseListener(mouseListener(panel_Price_Button, "total", rdbtn_Price));
    }
        
        // Returns a MouseAdapter for handling clicks on the specified panel and field type
        MouseAdapter mouseListener(JPanel panel, String fieldType, JRadioButton radioButton) {
            return new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    field = fieldType;
                    radioButton.setSelected(true);
                    setupData();
                }
                @Override public void mouseEntered(MouseEvent e) {
                    panel.setBackground(new Color(200, 200, 200));
                }
                @Override public void mouseExited(MouseEvent e) {
                    panel.setBackground(new Color(220, 220, 220));
                }
            };
        }

    public void Refresh_Graph() {
        setupData(); // update the graph data
    }

    /**
     * Refreshes the panel data
     */
    public void Panel_Refresh() {
        setupData(); // update the current reading display
    }

    private void setupData() {
        all_readings = database_manager.getReadingManager().getReadings_As_JList(this, database_manager, current_user, utility_type);
        scrollpane_Recent_Readings.setViewportView(all_readings);
        
        try {
            Reading latest_reading = database_manager.getReadingManager().getLatest_Reading_By_Type(current_user, utility_type);
            
            if (latest_reading == null) {
                lbl_Reading_Value.setText("No Data");
            } else {
                
                database_manager.getReadingManager().updateReading_Label(current_user, latest_reading, lbl_Reading_Value, lbl_Trend_Of_Reading, lbl_Reading_Unit, utility_type, field);
            }
            
            this.years = database_manager.getReadingManager().getReading_Years(current_user, utility_type);

            // Reset navigation flags
            hasNextYear = false;
            hasPreviousYear = false;

            if (years != null && years.length > 0) {
                // Find min and max years
                int minYear = Integer.MAX_VALUE;
                int maxYear = Integer.MIN_VALUE;
                
                for (int year : years) {
                    minYear = Math.min(minYear, year);
                    maxYear = Math.max(maxYear, year);
                }

                // Only allow navigation within the bounds of actual data
                hasNextYear = current_graph_year < maxYear;
                hasPreviousYear = current_graph_year > minYear;
            }
            
            lbl_Next_Button.setForeground(new Color(170, 170, 170));
            lbl_Next_Button.setVisible(hasNextYear);
            lbl_Next_Button.setEnabled(hasNextYear);
            lbl_Prev_Button.setForeground(new Color(170, 170, 170));
            lbl_Prev_Button.setVisible(hasPreviousYear);
            lbl_Prev_Button.setEnabled(hasPreviousYear);
            
            data = database_manager.getReadingManager().getMonthly_Utility_Data(current_user, utility_type, current_graph_year , field);
            graph_Panel.setField(field, data);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void goToAddReading() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Add_Reading_Window add_reading_panel = new Add_Reading_Window(
                        (JFrame) SwingUtilities.getWindowAncestor(Utility_Panel.this),
                        database_manager, current_user, Utility_Panel.this, utility_type
                    );
                    add_reading_panel.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
/*
 * File: Utility_Panel.java
 *
 * Description:
 * This file defines the `Utility_Panel` class, which is a modular panel for displaying utility consumption data 
 * (electricity, gas, water, etc.). It provides a graphical interface for viewing current readings, historical data 
 * in a graph, and utility tips. The panel is designed to be reusable for different utility types by passing 
 * configuration parameters during initialization.
 *
 * Variables:
 *
 * - **Database and User Fields**:
 *   - `database_manager` (Database_Manager): Manages database operations, including reading-related actions.
 *   - `utility_Tips_Manager` (Utility_Tips_Manager): Provides utility tips for the user.
 *   - `current_user` (User): Represents the currently logged-in user.
 *   - `utility_type` (String): Specifies the type of utility (electricity, gas, or water).
 *   - `field` (String): Specifies the field to display in the graph (e.g., "reading", "rate", or "total").
 *
 * - **Panel Configuration**:
 *   - `panel_title` (String): The title of the panel.
 *   - `panel_subtitle` (String): The subtitle of the panel.
 *   - `tips_title_color` (Color): The color of the tips title.
 *
 * - **Year Navigation Fields**:
 *   - `current_graph_year` (int): The currently displayed year in the graph.
 *   - `years` (int[]): An array of years for which data is available.
 *   - `hasNextYear` (boolean): Indicates if there is a next year available for navigation.
 *   - `hasPreviousYear` (boolean): Indicates if there is a previous year available for navigation.
 *
 * - **Main Panel Fields**:
 *   - `panel_Title` (JPanel): The panel containing the title and date/time labels.
 *   - `panel_Current_Reading` (JPanel): The panel displaying the current reading and trend.
 *   - `panel_Graph_Container` (JPanel): The panel containing the graph and navigation buttons.
 *   - `panel_tips` (JPanel): The panel displaying utility tips.
 *   - `panel_Recent_Readings_Container` (JPanel): The panel displaying recent readings in a scrollable list.
 *
 * - **Graph Panel Fields**:
 *   - `graph_Panel` (Graph_Panel): The panel displaying the graph.
 *   - `lbl_Prev_Button` (JLabel): The button for navigating to the previous year in the graph.
 *   - `lbl_CurrentYear` (JLabel): The label displaying the currently selected year.
 *   - `lbl_Next_Button` (JLabel): The button for navigating to the next year in the graph.
 *
 * - **Title Panel Labels**:
 *   - `lbl_Title` (JLabel): The main title of the panel.
 *   - `lbl_SubTitle` (JLabel): The subtitle of the panel.
 *   - `lbl_Date` (JLabel): The label displaying the current date.
 *   - `lbl_Time` (JLabel): The label displaying the current time.
 *
 * - **Scroll Panel Components**:
 *   - `scrollpane_Recent_Readings` (JScrollPane): The scrollable container for recent readings.
 *   - `all_readings` (JList<String>): The list of recent readings.
 *   - `lbl_Title_RecentReadings` (JLabel): The title of the recent readings section.
 *   - `lbl_Head_Date`, `lbl_Head_Readings`, `lbl_Head_Rate`, `lbl_Head_TotalPrice` (JLabel): Column headers for the recent readings table.
 *
 * - **Current Reading Panel Components**:
 *   - `lbl_Reading_Value` (JLabel): The label displaying the current reading value.
 *   - `lbl_Title_Current_Reading` (JLabel): The title of the current reading section.
 *   - `lbl_Reading_Unit` (JLabel): The label displaying the unit of the current reading.
 *   - `btn_Add_New_Reading` (JButton): The button for adding a new reading.
 *   - `lbl_Trend_Of_Reading` (JLabel): The label displaying the trend of the current reading.
 *
 * - **Tips Panel Labels**:
 *   - `lbl_Title_Tips` (JLabel): The title of the tips section.
 *   - `lbl_Tips_1` (JLabel): The label displaying a random utility tip.
 *
 * - **View Buttons**:
 *   - `panel_View_Buttons` (Rounded_Panel): The panel containing the view buttons.
 *   - `rdbtn_Reading`, `rdbtn_Rate`, `rdbtn_Price` (JRadioButton): Radio buttons for selecting the field to display in the graph.
 *   - `rdgroup_View_Buttons` (ButtonGroup): The group containing the radio buttons.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Utility_Panel(Database_Manager, User, String, String, String, String, Color)`:
 *      - Initializes the panel with the provided database manager, user, utility type, title, subtitle, tips title, and tips title color.
 *      - Calls `initialize_UI()` to set up the UI, `create_Actions_Listeners()` to add event listeners, and `setupData()` to load data.
 *
 * 2. **initialize_UI(String)**:
 *    - Sets up the panel's properties (e.g., size, layout, background color).
 *    - Creates and positions all UI components, including labels, panels, buttons, and the graph.
 *
 * 3. **create_Actions_Listeners()**:
 *    - Adds mouse listeners to the graph navigation buttons, add reading button, and view buttons to handle user interactions.
 *    - Updates the graph and data display based on user actions.
 *
 * 4. **setupData()**:
 *    - Fetches the latest readings for the specified utility type from the database.
 *    - Updates the labels and trends for the current reading.
 *    - Refreshes the graph panel with the selected field and year.
 *    - Configures the year navigation buttons based on available data.
 *
 * 5. **Refresh_Graph()**:
 *    - Refreshes the graph data for the currently selected year and field.
 *
 * 6. **Panel_Refresh()**:
 *    - Refreshes the panel data by calling `setupData()`.
 *
 * 7. **goToAddReading()**:
 *    - Opens the `Add_Reading_Window` dialog for adding a new reading.
 *
 * Usage:
 * This class is used as a modular panel for displaying utility consumption data. It provides a comprehensive summary 
 * of the user's utility expenses and trends, along with interactive features for data visualization and tips.
 */
