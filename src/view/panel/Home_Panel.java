package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.Reading;
import model.User;
import view.panel.misc.Utility_Tips_Manager;
import visuals.Graph_Panel;
import visuals.Rounded_Panel;

import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.CardLayout;

/**
 * Home Panel for displaying user utility data and graphs
 */
public class Home_Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    //==============================================================================================
    // FIELDS
    //==============================================================================================
    
    /** Database and user fields */
    private Database_Manager database_Manager;
    private User current_User;
    
    /** Main panel containers */
    private JPanel panel_Welcome_Title;
    private JPanel panel_Information;
    private JPanel panel_Tips;
    private JPanel panel_Graph_Container;
    
    /** Utility info panels */
    private JPanel panel_Electricity_Info;
    private JPanel panel_Water_Info;
    private JPanel panel_Gas_Info;
    private JPanel panel_Overall_Info;
    
    /** Reading value labels */
    private JLabel lbl_Electricity_Reading_Value;
    private JLabel lbl_Water_Reading_Value;
    private JLabel lbl_Gas_Reading_Value;
    private JLabel lbl_OverAll_Reading_Value;
    
    /** Panel title labels */
    private JLabel lbl_Title_Welcome;
    private JLabel lbl_Username;
    private JLabel lbl_Date;
    private JLabel lbl_Time;
    private JLabel lbl_Title_Electricity_Info;
    private JLabel lbl_Title_Water_Info;
    private JLabel lbl_Title_Gas_Info;
    private JLabel lbl_Title_OverAll_Info;
    
    /** Unit labels */
    private JLabel lbl_Electricity_Reading_Unit;
    private JLabel lbl_Water_Reading_Unit;
    private JLabel lbl_Gas_Reading_Unit;
    private JLabel lbl_OverAll_Reading_Unit;
    
    /** Graph components */
    private Graph_Panel graph_Panel;
    private JPanel panel_Behind1;
    private JPanel panel_Behind2;
    private JPanel panel_Behind3;
    private JLabel lbl_Trend_Of_Reading_Overall;
    private JLabel lbl_Trend_Of_Reading_Gas;
    private JLabel lbl_Trend_Of_Reading_Water;
    private JLabel lbl_Trend_Of_Reading_Electricity;
    
    /** Tips panel components */
    private Utility_Tips_Manager utility_Tips_Manager;
    private JLabel lbl_Title_Tips;
    private JPanel panel_Current_Tip;
    private JLabel lbl_Current_Tip;
    private JLabel lbl_Current_Tip_Type;
    
    //==============================================================================================
    // CONSTRUCTOR
    //==============================================================================================
    
    /**
     * Constructor for the Home Panel
     * 
     * @param database_Manager Database manager for accessing data
     * @param current_User The current logged-in user
     */
    public Home_Panel(Database_Manager database_Manager, User current_User) {
        this.database_Manager = database_Manager;
        this.current_User = current_User;
        
        initialize_Panel_Properties();
        initialize_UI();
        setup_Data();
    }
    
    //==============================================================================================
    // INITIALIZATION METHODS
    //==============================================================================================
    
    /**
     * Initialize the panel's base properties
     */
    private void initialize_Panel_Properties() {
        setPreferredSize(new Dimension(986, 688));
        setLayout(null);
    }
    
    /**
     * Initialize all UI components
     */
    private void initialize_UI() {
        create_Header_Panel();
        create_Content_Panel();
        create_Graph_Panel();
        create_Tips_Panel();
        create_Action_Listeners();
    }
    
    //==============================================================================================
    // UI CREATION - HEADER SECTION
    //==============================================================================================
    
    /**
     * Create the header panel with welcome title
     */
    private void create_Header_Panel() {
        panel_Welcome_Title = new Rounded_Panel();
        panel_Welcome_Title.setBackground(new Color(255, 255, 255));
        panel_Welcome_Title.setBounds(21, 11, 944, 85);
        panel_Welcome_Title.setLayout(null);
        add(panel_Welcome_Title);
        
        lbl_Title_Welcome = new JLabel("Welcome,");
        lbl_Title_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_Welcome.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lbl_Title_Welcome.setBounds(10, 0, 182, 60);
        panel_Welcome_Title.add(lbl_Title_Welcome);
        
        lbl_Username = new JLabel("User");
        lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lbl_Username.setBounds(181, 0, 206, 60);
        lbl_Username.setText(current_User.getUsername());
        panel_Welcome_Title.add(lbl_Username);
        
        lbl_Date = new JLabel("Date");
        lbl_Date.setVerticalAlignment(SwingConstants.TOP);
        lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Date.setBounds(764, 11, 170, 54);
        lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  ")));
        panel_Welcome_Title.add(lbl_Date);
        
        lbl_Time = new JLabel("Time");
        lbl_Time.setVerticalAlignment(SwingConstants.TOP);
        lbl_Time.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Time.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Time.setBounds(764, 39, 170, 41);
        lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm  ")));
        panel_Welcome_Title.add(lbl_Time);
        
        JLabel lbl_SubTitle_Welcome = new JLabel("Here is Your Summary of Expenses\r\n\r\n");
        lbl_SubTitle_Welcome.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_SubTitle_Welcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbl_SubTitle_Welcome.setBounds(20, 58, 393, 22);
        panel_Welcome_Title.add(lbl_SubTitle_Welcome);
    }
    
    //==============================================================================================
    // UI CREATION - CONTENT PANELS
    //==============================================================================================
    
    /**
     * Create the content panel with utility information
     */
    private void create_Content_Panel() {
        panel_Information = new Rounded_Panel();
        panel_Information.setBackground(new Color(255, 255, 255));
//        panel_Information.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Information.setBounds(21, 114, 467, 408);
        panel_Information.setLayout(null);
        add(panel_Information);
        
        create_Electricity_Info_Panel();
        create_Water_Info_Panel();
        create_Gas_Info_Panel();
        create_Overall_Info_Panel();
    }
    
    /**
     * Create the electricity information panel
     */
    private void create_Electricity_Info_Panel() {
        panel_Electricity_Info = new Rounded_Panel();
        panel_Electricity_Info.setBackground(new Color(220, 220, 220));
        panel_Electricity_Info.setBounds(10, 11, 447, 87);
        panel_Electricity_Info.setLayout(null);
        panel_Information.add(panel_Electricity_Info);
        
        lbl_Title_Electricity_Info = new JLabel("Electricity");
        lbl_Title_Electricity_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_Electricity_Info.setBounds(10, 15, 156, 32);
        panel_Electricity_Info.add(lbl_Title_Electricity_Info);
        
        lbl_Electricity_Reading_Value = new JLabel();
        lbl_Electricity_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Electricity_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Electricity_Reading_Value.setBounds(259, 15, 100, 32);
        panel_Electricity_Info.add(lbl_Electricity_Reading_Value);
        
        lbl_Electricity_Reading_Unit = new JLabel("KwH");
        lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Electricity_Reading_Unit.setBounds(369, 15, 68, 32);
        panel_Electricity_Info.add(lbl_Electricity_Reading_Unit);
        
        lbl_Trend_Of_Reading_Electricity = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Electricity.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Electricity.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Electricity.setBounds(176, 44, 261, 32);
        panel_Electricity_Info.add(lbl_Trend_Of_Reading_Electricity);
    }
    
    /**
     * Create the water information panel
     */
    private void create_Water_Info_Panel() {
        panel_Water_Info = new Rounded_Panel();
        panel_Water_Info.setBackground(new Color(220, 220, 220));
        panel_Water_Info.setBounds(10, 109, 447, 87);
        panel_Water_Info.setLayout(null);
        panel_Information.add(panel_Water_Info);
        
        lbl_Title_Water_Info = new JLabel("Water");
        lbl_Title_Water_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_Water_Info.setBounds(10, 15, 156, 32);
        panel_Water_Info.add(lbl_Title_Water_Info);
        
        lbl_Water_Reading_Value = new JLabel();
        lbl_Water_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Water_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Water_Reading_Value.setBounds(261, 15, 100, 32);
        panel_Water_Info.add(lbl_Water_Reading_Value);
        
        lbl_Water_Reading_Unit = new JLabel("m³");
        lbl_Water_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Water_Reading_Unit.setBounds(369, 15, 68, 32);
        panel_Water_Info.add(lbl_Water_Reading_Unit);
        
        lbl_Trend_Of_Reading_Water = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Water.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Water.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Water.setBounds(176, 44, 261, 32);
        panel_Water_Info.add(lbl_Trend_Of_Reading_Water);
    }
    
    /**
     * Create the gas information panel
     */
    private void create_Gas_Info_Panel() {
        panel_Gas_Info = new Rounded_Panel();
        panel_Gas_Info.setBackground(new Color(220, 220, 220));
        panel_Gas_Info.setBounds(10, 207, 447, 87);
        panel_Gas_Info.setLayout(null);
        panel_Information.add(panel_Gas_Info);
        
        lbl_Title_Gas_Info = new JLabel("Gas");
        lbl_Title_Gas_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_Gas_Info.setBounds(10, 15, 156, 32);
        panel_Gas_Info.add(lbl_Title_Gas_Info);
        
        lbl_Gas_Reading_Value = new JLabel();
        lbl_Gas_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Gas_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Gas_Reading_Value.setBounds(259, 15, 100, 32);
        panel_Gas_Info.add(lbl_Gas_Reading_Value);
        
        lbl_Gas_Reading_Unit = new JLabel("Qty");
        lbl_Gas_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Gas_Reading_Unit.setBounds(369, 15, 68, 32);
        panel_Gas_Info.add(lbl_Gas_Reading_Unit);
        
        lbl_Trend_Of_Reading_Gas = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Gas.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Gas.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Gas.setBounds(176, 44, 261, 32);
        panel_Gas_Info.add(lbl_Trend_Of_Reading_Gas);
    }
    
    /**
     * Create the overall information panel
     */
    private void create_Overall_Info_Panel() {
        panel_Overall_Info = new Rounded_Panel();
        panel_Overall_Info.setBackground(new Color(220, 220, 220));
        panel_Overall_Info.setBounds(10, 305, 447, 87);
        panel_Overall_Info.setLayout(null);
        panel_Information.add(panel_Overall_Info);
        
        lbl_Title_OverAll_Info = new JLabel("Overall Expenses");
        lbl_Title_OverAll_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_OverAll_Info.setBounds(10, 15, 260, 32);
        panel_Overall_Info.add(lbl_Title_OverAll_Info);
        
        lbl_OverAll_Reading_Value = new JLabel();
        lbl_OverAll_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_OverAll_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_OverAll_Reading_Value.setBounds(258, 15, 101, 32);
        panel_Overall_Info.add(lbl_OverAll_Reading_Value);
        
        lbl_OverAll_Reading_Unit = new JLabel("Php");
        lbl_OverAll_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_OverAll_Reading_Unit.setBounds(369, 15, 68, 32);
        panel_Overall_Info.add(lbl_OverAll_Reading_Unit);
        
        lbl_Trend_Of_Reading_Overall = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Overall.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Overall.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Overall.setBounds(177, 44, 260, 32);
        panel_Overall_Info.add(lbl_Trend_Of_Reading_Overall);
    }
    
    //==============================================================================================
    // UI CREATION - GRAPH SECTION
    //==============================================================================================
    
    /**
     * Create the graph panel with CardLayout for different utilities
     */
    private void create_Graph_Panel() {
        // Create the main graph container (visible to WindowBuilder)
        panel_Graph_Container = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Graph_Container.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_Graph_Container.setBounds(504, 157, 413, 365);
        panel_Graph_Container.setBackground(new Color(255, 255, 255));
        panel_Graph_Container.setLayout(new BorderLayout()); // Use BorderLayout for easy replacement
        add(panel_Graph_Container);
        
        // Create Graph_Panel instance
        Rounded_Panel rounded_Panel = new Rounded_Panel(25, Color.BLACK, 0);
        rounded_Panel.setBackground(new Color(255, 255, 255));
        graph_Panel = new Graph_Panel(rounded_Panel);
        graph_Panel.setBackground(new Color(255, 255, 255));
        
        // Add Graph_Panel to the container
        panel_Graph_Container.add(graph_Panel, BorderLayout.CENTER);
        
        create_Graph_Shadow_Panels();
    }
    
    /**
     * Creates shadow panels for visual design effect behind the graph
     */
    private void create_Graph_Shadow_Panels() {
        panel_Behind1 = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Behind1.setBackground(new Color(220, 220, 220));
//        panel_Behind1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Behind1.setBounds(520, 142, 413, 365);
        add(panel_Behind1);
        
        panel_Behind2 = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Behind2.setBackground(new Color(200, 200, 200));
//        panel_Behind2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Behind2.setBounds(536, 129, 413, 356);
        add(panel_Behind2);
        
        panel_Behind3 = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Behind3.setBackground(new Color(180, 180, 180));
//        panel_Behind3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Behind3.setBounds(552, 114, 413, 347);
        add(panel_Behind3);
    }
    
    //==============================================================================================
    // UI CREATION - TIPS SECTION
    //==============================================================================================
    
    /**
     * Create the tips panel with money-saving advice
     */
    private void create_Tips_Panel() {
    	utility_Tips_Manager = new Utility_Tips_Manager();
    	
        panel_Tips = new Rounded_Panel();
        panel_Tips.setBackground(new Color(255, 255, 255));
//        panel_Tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Tips.setBounds(21, 533, 944, 144);
        panel_Tips.setLayout(null);
        add(panel_Tips);
        
        lbl_Title_Tips = new JLabel("Money Saving Tips");
        lbl_Title_Tips.setBounds(10, 11, 243, 36);
        lbl_Title_Tips.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
        panel_Tips.add(lbl_Title_Tips);
        
        panel_Current_Tip = new Rounded_Panel();
        panel_Current_Tip.setBounds(31, 55, 903, 78);
        panel_Tips.add(panel_Current_Tip);
        panel_Current_Tip.setLayout(null);
        
        lbl_Current_Tip = new JLabel("Electricity Tip - Replace traditional light bulbs with LED bulbs. They use up to 75% less energy and last much longer.");
        lbl_Current_Tip.setBounds(40, 44, 843, 21);
        lbl_Current_Tip.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Current_Tip.setFont(new Font("Tahoma", Font.PLAIN, 17));
        panel_Current_Tip.add(lbl_Current_Tip);
        
        lbl_Current_Tip_Type = new JLabel("Money Saving Tips");
        lbl_Current_Tip_Type.setForeground(new Color(0, 128, 255));
        lbl_Current_Tip_Type.setVerticalAlignment(SwingConstants.TOP);
        lbl_Current_Tip_Type.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Current_Tip_Type.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Current_Tip_Type.setBounds(10, 11, 243, 36);
        panel_Current_Tip.add(lbl_Current_Tip_Type);
        
        Timer timer = new Timer(60_000, e -> {
			lbl_Current_Tip.setText(utility_Tips_Manager.getRandomTip());
			lbl_Current_Tip_Type.setText(utility_Tips_Manager.getType());
		});
        timer.setInitialDelay(0); // Start immediately
        timer.start();
    }
    
    //==============================================================================================
    // INTERACTION HANDLING
    //==============================================================================================
    
    /**
     * Setup event listeners for interactive elements
     */
    private void create_Action_Listeners() {
        Timer timer = new Timer(60_000, e -> {
            lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        });
        LocalTime now = LocalTime.now();
        int millisUntilNextMinute = (60 - now.getSecond()) * 1000 - now.getNano() / 1_000_000;
        timer.setInitialDelay(millisUntilNextMinute); // optional: sync with real time
        timer.start();
        
        // Electricity panel click event
        panel_Electricity_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graph_Panel.showElectricityGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                panel_Electricity_Info.setBackground(new Color(200, 200, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel_Electricity_Info.setBackground(new Color(220, 220, 220));
            }
        });
        
        // Water panel click event
        panel_Water_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graph_Panel.showWaterGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                panel_Water_Info.setBackground(new Color(200, 200, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel_Water_Info.setBackground(new Color(220, 220, 220));
            }
        });
        
        // Gas panel click event
        panel_Gas_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graph_Panel.showGasGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                panel_Gas_Info.setBackground(new Color(200, 200, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel_Gas_Info.setBackground(new Color(220, 220, 220));
            }
        });
        
        // Overall panel click event
        panel_Overall_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graph_Panel.showOverallGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                panel_Overall_Info.setBackground(new Color(200, 200, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel_Overall_Info.setBackground(new Color(220, 220, 220));
            }
        });
    }
    
    //==============================================================================================
    // DATA HANDLING
    //==============================================================================================
    
    /**
     * Public method to refresh the panel data
     */
    public void home_Panel_Refresh() {
        setup_Data();
    }
    
    /**
     * Loads and displays data from the database
     */
    private void setup_Data() {
        try {
            // Get latest readings for each utility type
            Reading electricity_Reading = database_Manager.getReadingManager().getLatestReadingByType(current_User, "electricity");
            Reading water_Reading = database_Manager.getReadingManager().getLatestReadingByType(current_User, "water");
            Reading gas_Reading = database_Manager.getReadingManager().getLatestReadingByType(current_User, "gas");
            
            // Update labels with latest readings
            update_Reading_Labels(electricity_Reading, water_Reading, gas_Reading);
            
            // Initialize graph panels (needed because we're using a delayed initialization approach)
            graph_Panel.initialize();
            
            // Setup the bar graphs with data from past 6 months
            update_Bar_Graphs();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the reading value labels based on latest readings
     */
    private void update_Reading_Labels(Reading electricityReading, Reading waterReading, Reading gasReading) {
        // Update individual utility readings
        update_Electricity_Reading(electricityReading);
        update_Water_Reading(waterReading);
        update_Gas_Reading(gasReading);
        
        // Calculate and update overall expenses
        update_Overall_Expenses(electricityReading, waterReading, gasReading);
    }

    /**
     * Updates the electricity reading display with the latest data
     * 
     * @param electricityReading The latest electricity reading
     */
    private void update_Electricity_Reading(Reading electricity_Reading) {
        if (electricity_Reading == null) {
            lbl_Electricity_Reading_Value.setText("No Data");
        } else {
        	lbl_Electricity_Reading_Value.setText(String.valueOf(electricity_Reading.getReading()));
            
            try {
            	lbl_Trend_Of_Reading_Electricity.setText(database_Manager.getReadingManager().getTrend(current_User, "electricity"));
            	lbl_Trend_Of_Reading_Electricity.setForeground(database_Manager.getReadingManager().getTrendColor(current_User, "electricity"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the water reading display with the latest data
     * 
     * @param waterReading The latest water reading
     */
    private void update_Water_Reading(Reading water_Reading) {
        if (water_Reading == null) {
            lbl_Water_Reading_Value.setText("No Data");
        } else {
            lbl_Water_Reading_Value.setText(String.valueOf(water_Reading.getReading()));
            
            try {
                lbl_Trend_Of_Reading_Water.setText(database_Manager.getReadingManager().getTrend(current_User, "water"));
                lbl_Trend_Of_Reading_Water.setForeground(database_Manager.getReadingManager().getTrendColor(current_User, "water"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the gas reading display with the latest data
     * 
     * @param gasReading The latest gas reading
     */
    private void update_Gas_Reading(Reading gas_Reading) {
        if (gas_Reading == null) {
            lbl_Gas_Reading_Value.setText("No Data");
        } else {
            lbl_Gas_Reading_Value.setText(String.valueOf(gas_Reading.getReading()));
            
            try {
                lbl_Trend_Of_Reading_Gas.setText(database_Manager.getReadingManager().getTrend(current_User, "gas"));
                lbl_Trend_Of_Reading_Gas.setForeground(database_Manager.getReadingManager().getTrendColor(current_User, "gas"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the overall expenses summary based on all utility readings
     * 
     * @param electricityReading The latest electricity reading
     * @param waterReading The latest water reading
     * @param gasReading The latest gas reading
     */
    private void update_Overall_Expenses(Reading electricity_Reading, Reading water_Reading, Reading gas_Reading) {
        double total_Price = calculate_Total_Price(electricity_Reading, water_Reading, gas_Reading);
        boolean has_Any_Reading = (electricity_Reading != null || water_Reading != null || gas_Reading != null);

        if (!has_Any_Reading) {
            lbl_OverAll_Reading_Value.setText("No Data");
        } else {
            lbl_OverAll_Reading_Value.setText(String.valueOf(total_Price));
            update_Overall_Trend(total_Price, electricity_Reading, water_Reading, gas_Reading);
        }
    }

    /**
     * Calculates the total price from all utility readings
     * 
     * @param electricityReading The latest electricity reading
     * @param waterReading The latest water reading
     * @param gasReading The latest gas reading
     * @return The combined total price from all readings
     */
    private double calculate_Total_Price(Reading electricity_Reading, Reading water_Reading, Reading gas_Reading) {
        double total_Price = 0.0;
        
        // Add electricity price if available
        if (electricity_Reading != null) {
            total_Price += electricity_Reading.getTotal_Price();
        }

        // Add water price if available
        if (water_Reading != null) {
            total_Price += water_Reading.getTotal_Price();
        }

        // Add gas price if available
        if (gas_Reading != null) {
            total_Price += gas_Reading.getTotal_Price();
        }
        
        return total_Price;
    }

    /**
     * Updates the overall trend label comparing current month to previous month
     * 
     * @param currentTotalPrice The current month's total price
     * @param electricityReading The latest electricity reading
     * @param waterReading The latest water reading
     * @param gasReading The latest gas reading
     */
    private void update_Overall_Trend(double current_Total_Price, Reading electricity_Reading, 
                                   Reading water_Reading, Reading gas_Reading) {
        // Get previous month's data
        LocalDate current_Date = LocalDate.now();
        LocalDate previous_Month = current_Date.minusMonths(1);
        
        double previous_Month_Total = get_Previous_Month_Total(previous_Month, electricity_Reading, water_Reading, gas_Reading);
        
        if (previous_Month_Total > 0) {
            display_Trend_Percentage(current_Total_Price, previous_Month_Total);
        } else {
        	lbl_Trend_Of_Reading_Overall.setText("No previous data");
        }
    }

    /**
     * Retrieves the total price for the previous month
     * 
     * @param previousMonth The previous month date
     * @param electricityReading Current electricity reading (for validation)
     * @param waterReading Current water reading (for validation)
     * @param gasReading Current gas reading (for validation)
     * @return The total price for the previous month
     */
    private double get_Previous_Month_Total(LocalDate previous_Month, Reading electricity_Reading, 
                                       Reading water_Reading, Reading gas_Reading) {
        double previous_Month_Total = 0.0;
        
        Reading previous_Electricity = database_Manager.getReadingManager()
                .getReadingByMonth(current_User, "electricity", previous_Month);
        Reading previous_Water = database_Manager.getReadingManager()
                .getReadingByMonth(current_User, "water", previous_Month);
        Reading previous_Gas = database_Manager.getReadingManager()
                .getReadingByMonth(current_User, "gas", previous_Month);
        
        // Add previous electricity price if available
        if (previous_Electricity != null && electricity_Reading != null) {
            previous_Month_Total += previous_Electricity.getTotal_Price();
        }
        
        // Add previous water price if available
        if (previous_Water != null && water_Reading != null) {
            previous_Month_Total += previous_Water.getTotal_Price();
        }
        
        // Add previous gas price if available
        if (previous_Gas != null && gas_Reading != null) {
            previous_Month_Total += previous_Gas.getTotal_Price();
        }
        
        return previous_Month_Total;
    }

    /**
     * Displays the percentage change between current and previous month
     * 
     * @param currentTotal Current month's total price
     * @param previousTotal Previous month's total price
     */
    private void display_Trend_Percentage(double current_Total, double previous_Total) {
        double percentage_Change = ((current_Total - previous_Total) / previous_Total) * 100;
        String trend_Text = String.format("%.1f%% from last month", percentage_Change);
        lbl_Trend_Of_Reading_Overall.setText(trend_Text);
        
        // Set color based on trend (red for increase, green for decrease)
        Color trend_Color = percentage_Change > 0 ? new Color(255, 0, 0) : new Color(0, 150, 0);
        lbl_Trend_Of_Reading_Overall.setForeground(trend_Color);
    }

    /**
     * Updates all bar graphs with latest data
     */
    private void update_Bar_Graphs() {
        try {
            // Get data for each utility type
            Map<Month, Double> electricity_Data = get_Monthly_Utility_Data("electricity", false);
            Map<Month, Double> water_Data = get_Monthly_Utility_Data("water", false);
            Map<Month, Double> gas_Data = get_Monthly_Utility_Data("gas", false);
            
            // Get price data for overall expenses
            Map<Month, Double> overall_Data = calculate_Overall_Expenses_Data();
            
            // Update graphs with collected data
            graph_Panel.updateGraphs(electricity_Data, water_Data, gas_Data, overall_Data, 5);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets monthly utility data for a specific utility type
     * 
     * @param utilityType The type of utility ("electricity", "water", or "gas")
     * @param usePrice If true, uses total_price field; if false, uses reading field
     * @return Map with Month as key and summed value as value
     */
    private Map<Month, Double> get_Monthly_Utility_Data(String utility_Type, boolean use_Price) throws SQLException {
        // Determine date range (last 6 months)
        LocalDate end_Date = LocalDate.now();
        LocalDate start_Date = end_Date.minusMonths(6);
        
        // Fetch readings from database
        List<Reading> readings = database_Manager.getReadingManager()
            .getReadingsByDateAndType(current_User, start_Date, end_Date, utility_Type);
        
        // Group readings by month
        return group_Readings_By_Month(readings, use_Price);
    }

    /**
     * Calculates overall expenses data by combining price data from all utilities
     * 
     * @return Map with Month as key and total expense as value
     */
    private Map<Month, Double> calculate_Overall_Expenses_Data() throws SQLException {
        Map<Month, Double> overall_Data = new HashMap<>();
        
        // Get price data for each utility type
        Map<Month, Double> electricity_Prices = get_Monthly_Utility_Data("electricity", true);
        Map<Month, Double> water_Prices = get_Monthly_Utility_Data("water", true);
        Map<Month, Double> gas_Prices = get_Monthly_Utility_Data("gas", true);
        
        // Combine all price data for total expenses
        for (Month month : electricity_Prices.keySet()) {
            double total_Price = electricity_Prices.getOrDefault(month, 0.0) +
                                water_Prices.getOrDefault(month, 0.0) +
                                gas_Prices.getOrDefault(month, 0.0);
            overall_Data.put(month, total_Price);
        }
        
        // Also include months that may only exist in water or gas data
        add_Missing_Months(overall_Data, water_Prices);
        add_Missing_Months(overall_Data, gas_Prices);
        
        return overall_Data;
    }

    /**
     * Adds any months from the source map that are missing in the target map
     * 
     * @param targetMap The map to add missing months to
     * @param sourceMap The map to check for missing months
     */
    private void add_Missing_Months(Map<Month, Double> target_Map, Map<Month, Double> source_Map) {
        for (Month month : source_Map.keySet()) {
            if (!target_Map.containsKey(month)) {
                target_Map.put(month, source_Map.get(month));
            } else {
                target_Map.put(month, target_Map.get(month) + source_Map.get(month));
            }
        }
    }

    /**
     * Groups readings by month and calculates either sum of readings or sum of total price
     * 
     * @param readings List of readings to group
     * @param usePrice If true, uses total_price field; if false, uses reading field
     * @return Map with Month as key and summed value as value
     */
    private Map<Month, Double> group_Readings_By_Month(List<Reading> readings, boolean use_Price) {
        Map<Month, Double> monthly_Data = new HashMap<>();
        
        if (readings != null) {
            for (Reading reading : readings) {
                LocalDate reading_Date = reading.getDate();
                Month month = reading_Date.getMonth();
                
                double value = use_Price ? reading.getTotal_Price() : reading.getReading();
                
                // Add value to existing month or create new entry
                monthly_Data.put(month, monthly_Data.getOrDefault(month, 0.0) + value);
            }
        }
        
        return monthly_Data;
    }
}