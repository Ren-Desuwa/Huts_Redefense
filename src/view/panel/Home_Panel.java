package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import database.Reading_Manager;
import model.Reading;
import model.User;
import view.panel.misc.Utility_Tips_Manager;
import visuals.Graph_Panel;
import visuals.Rounded_Panel;

import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;


import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.BorderLayout;


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
        // Clock update timer
        Timer timer = new Timer(60_000, e -> update_Clock());
        timer.setInitialDelay(calculate_Initial_Delay());
        timer.start();
        
        // Panel click events
        add_Panel_Listeners(panel_Electricity_Info, () -> graph_Panel.showElectricityGraph());
        add_Panel_Listeners(panel_Water_Info, () -> graph_Panel.showWaterGraph());
        add_Panel_Listeners(panel_Gas_Info, () -> graph_Panel.showGasGraph());
        add_Panel_Listeners(panel_Overall_Info, () -> graph_Panel.showOverallGraph());
    }

    private void update_Clock() {
        lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private int calculate_Initial_Delay() {
        LocalTime now = LocalTime.now();
        return (60 - now.getSecond()) * 1000 - now.getNano() / 1_000_000;
    }

    private void add_Panel_Listeners(JPanel panel, Runnable onClick) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(200, 200, 200));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(220, 220, 220));
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
    /**
     * Updates reading displays for a specific utility type
     */
    private void update_Utility_Reading(Reading reading, JLabel valueLabel, JLabel trendLabel, String utilityType) {
        if (reading == null) {
            valueLabel.setText("No Data");
            trendLabel.setText("No Data");
        } else {
            valueLabel.setText(String.valueOf(reading.getReading()));
            try {
                String trend = database_Manager.getReadingManager().getTrend(current_User, utilityType);
                trendLabel.setText(trend);
                trendLabel.setForeground(database_Manager.getReadingManager().getTrendColor(current_User, utilityType));
            } catch (SQLException e) {
                e.printStackTrace();
                trendLabel.setText("Error calculating trend");
            }
        }
    }

    /**
     * Updates the reading value labels based on latest readings
     */
    private void update_Reading_Labels(Reading electricity_Reading, Reading water_Reading, Reading gas_Reading) {
        // Update individual utility readings
        update_Utility_Reading(electricity_Reading, lbl_Electricity_Reading_Value, lbl_Trend_Of_Reading_Electricity, "electricity");
        update_Utility_Reading(water_Reading, lbl_Water_Reading_Value, lbl_Trend_Of_Reading_Water, "water");
        update_Utility_Reading(gas_Reading, lbl_Gas_Reading_Value, lbl_Trend_Of_Reading_Gas, "gas");
        
        // Update overall expenses
        update_Overall_Expenses(electricity_Reading, water_Reading, gas_Reading);
    }

    /**
     * Updates all bar graphs with latest data
     */
    private void update_Bar_Graphs() {
        try {
            Reading_Manager rm = database_Manager.getReadingManager();
            
            // Get data directly from Reading_Manager
            Map<Month, Double> electricity_Data = rm.getMonthlyUtilityData(current_User, "electricity", 6, false);
            Map<Month, Double> water_Data = rm.getMonthlyUtilityData(current_User, "water", 6, false);
            Map<Month, Double> gas_Data = rm.getMonthlyUtilityData(current_User, "gas", 6, false);
            Map<Month, Double> overall_Data = rm.getMonthlyTotalExpenses(current_User, 6);
            
            // Update graphs with collected data
            graph_Panel.updateGraphs(electricity_Data, water_Data, gas_Data, overall_Data, 5);
        } catch (SQLException e) {
            e.printStackTrace();
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
            lbl_Trend_Of_Reading_Overall.setText("No data");
        } else {
            lbl_OverAll_Reading_Value.setText(String.valueOf(total_Price));
            try {
                String trend = database_Manager.getReadingManager().getTrendOverall(current_User);
                lbl_Trend_Of_Reading_Overall.setText(trend);
                lbl_Trend_Of_Reading_Overall.setForeground(database_Manager.getReadingManager().getTrendColor(current_User, null));
            } catch (SQLException e) {
                e.printStackTrace();
                lbl_Trend_Of_Reading_Overall.setText("Error calculating trend");
            }
        }
    }

    private double calculate_Total_Price(Reading electricity_Reading, Reading water_Reading, Reading gas_Reading) {
        double total_Price = 0.0;
        if (electricity_Reading != null) total_Price += electricity_Reading.getTotal_Price();
        if (water_Reading != null) total_Price += water_Reading.getTotal_Price();
        if (gas_Reading != null) total_Price += gas_Reading.getTotal_Price();
        return total_Price;
    }
}