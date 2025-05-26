package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import database.Utility_Tips_Manager;
import model.Reading;
import model.User;
import visuals.Graph_Panel;
import visuals.Rounded_Panel;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;

public class Home_Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    //==============================================================================================
    // FIELDS
    //==============================================================================================
    
    /** Database and user fields */
    private Database_Manager database_Manager;
    private Utility_Tips_Manager utility_Tips_Manager = Utility_Tips_Manager.getInstance();
    private User current_User;
    private String field = "reading";
    
    /** Main panel containers */
    private JPanel panel_Welcome_Title;
    private JPanel panel_Information;
    private JPanel panel_Graph_Container;
    private JPanel panel_Tips;
    private JPanel panel_View_Buttons;
    
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
    private JLabel lbl_SubTitle_Welcome;
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
    private JLabel lbl_Trend_Of_Reading_Electricity;
    private JLabel lbl_Trend_Of_Reading_Water;
    private JLabel lbl_Trend_Of_Reading_Gas;
    private JLabel lbl_Trend_Of_Reading_Overall;
    
    /** Tips panel components */
    private Rounded_Panel panel_Tip_1;
    private Rounded_Panel panel_Tip_2;
    
    /** Tip labels */
    private JLabel lbl_Tip_1;
    private JLabel lbl_Tip_2;
    private JLabel lbl_Tip_Type_1;
    private JLabel lbl_Tip_Type_2;
    
    /** View data section components */
    private JLabel lbl_View_Data;
    private JLabel lbl_Reading;
    private JLabel lbl_Rate;
    private JLabel lbl_Price;
    
    /** Radio buttons for data type selection */
    private ButtonGroup rdgroup_Data_Type;
    private JRadioButton rdbtn_Reading;
    private JRadioButton rdbtn_Rate;
    private JRadioButton rdbtn_Price;
    private JPanel panel_Reading_Button;
    private JPanel panel_Rate_Button;
    private JPanel panel_Price_Button;

    public Home_Panel(Database_Manager database_Manager, User current_User) {
        this.database_Manager = database_Manager;
        this.current_User = current_User;
        
        // Set panel properties
        setBackground(new Color(213, 213, 213));
        setPreferredSize(new Dimension(986, 688));
        setLayout(null);

        // Initialize all UI components
        initialize_UI();
		create_Action_Listeners();
        
        // Setup initial data
        setup_Data();
    }
    
    private void initialize_UI() {
    	
    	//==============================================================================================
        // UI CREATION - HEADER SECTION
        //==============================================================================================
    	
    	// Welcome panel
		setLayout(null);
    	panel_Welcome_Title = new Rounded_Panel();
        panel_Welcome_Title.setBackground(new Color(255, 255, 255));
        panel_Welcome_Title.setBounds(21, 11, 944, 85);
        panel_Welcome_Title.setLayout(null);
        add(panel_Welcome_Title);
        
        // Title and date/time labels
        lbl_Title_Welcome = new JLabel("Welcome,");
        lbl_Title_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_Welcome.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lbl_Title_Welcome.setBounds(10, 0, 182, 60);
        panel_Welcome_Title.add(lbl_Title_Welcome);
        
        // Username label
        lbl_Username = new JLabel("User");
        lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lbl_Username.setBounds(184, 0, 609, 60);
        lbl_Username.setText(current_User.getUsername());
        panel_Welcome_Title.add(lbl_Username);
        
        // Date and time labels
        lbl_Date = new JLabel("Date");
        lbl_Date.setVerticalAlignment(SwingConstants.TOP);
        lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Date.setBounds(764, 11, 170, 54);
        lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panel_Welcome_Title.add(lbl_Date);
        
        // Time label
        lbl_Time = new JLabel("Time");
        lbl_Time.setVerticalAlignment(SwingConstants.TOP);
        lbl_Time.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Time.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Time.setBounds(764, 39, 170, 41);
        lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        panel_Welcome_Title.add(lbl_Time);
        
        // Subtitle label
        lbl_SubTitle_Welcome = new JLabel("Here is Your Summary of Expenses\r\n\r\n");
        lbl_SubTitle_Welcome.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_SubTitle_Welcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbl_SubTitle_Welcome.setBounds(20, 58, 393, 22);
        panel_Welcome_Title.add(lbl_SubTitle_Welcome);
        
        //==============================================================================================
        // UI CREATION - CONTENT PANELS
        //==============================================================================================
        
        // Main information panel
        panel_Information = new Rounded_Panel();
        panel_Information.setBackground(new Color(255, 255, 255));
        panel_Information.setBounds(21, 114, 467, 408);
        panel_Information.setLayout(null);
        add(panel_Information);
        
        //==============================================================================================
        // UI CREATION - UTILITY INFO PANELS - ELECTRICITY INFO PANEL
        //==============================================================================================
        
        // Electricity info panel
        panel_Electricity_Info = new Rounded_Panel();
        panel_Electricity_Info.setBackground(new Color(220, 220, 220));
        panel_Electricity_Info.setBounds(10, 11, 447, 87);
        panel_Electricity_Info.setLayout(null);
        panel_Information.add(panel_Electricity_Info);
        
        // Title label for electricity info panel
        lbl_Title_Electricity_Info = new JLabel("Electricity");
        lbl_Title_Electricity_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_Electricity_Info.setBounds(10, 15, 156, 32);
        panel_Electricity_Info.add(lbl_Title_Electricity_Info);
        
        // Reading value label for electricity
        lbl_Electricity_Reading_Value = new JLabel();
        lbl_Electricity_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Electricity_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Electricity_Reading_Value.setBounds(226, 15, 100, 32);
        panel_Electricity_Info.add(lbl_Electricity_Reading_Value);
        
        // Unit label for electricity reading
        lbl_Electricity_Reading_Unit = new JLabel("KwH");
        lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Electricity_Reading_Unit.setBounds(336, 15, 101, 32);
        panel_Electricity_Info.add(lbl_Electricity_Reading_Unit);
        
        // Trend label for electricity reading
        lbl_Trend_Of_Reading_Electricity = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Electricity.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Electricity.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Electricity.setBounds(113, 44, 324, 32);
        panel_Electricity_Info.add(lbl_Trend_Of_Reading_Electricity);

        //==============================================================================================
        // UI CREATION - UTILITY INFO PANELS - WATER INFO PANEL
        //==============================================================================================
        
        // Water info panel
        panel_Water_Info = new Rounded_Panel();
        panel_Water_Info.setBackground(new Color(220, 220, 220));
        panel_Water_Info.setBounds(10, 109, 447, 87);
        panel_Water_Info.setLayout(null);
        panel_Information.add(panel_Water_Info);
        
        // Title label for water info panel
        lbl_Title_Water_Info = new JLabel("Water");
        lbl_Title_Water_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_Water_Info.setBounds(10, 15, 156, 32);
        panel_Water_Info.add(lbl_Title_Water_Info);
        
        // Reading value label for water
        lbl_Water_Reading_Value = new JLabel();
        lbl_Water_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Water_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Water_Reading_Value.setBounds(226, 15, 100, 32);
        panel_Water_Info.add(lbl_Water_Reading_Value);
        
        // Unit label for water reading
        lbl_Water_Reading_Unit = new JLabel("m³");
        lbl_Water_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Water_Reading_Unit.setBounds(336, 15, 101, 32);
        panel_Water_Info.add(lbl_Water_Reading_Unit);
        
        // Trend label for water reading
        lbl_Trend_Of_Reading_Water = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Water.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Water.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Water.setBounds(113, 44, 324, 32);
        panel_Water_Info.add(lbl_Trend_Of_Reading_Water);

        //==============================================================================================
        // UI CREATION - UTILITY INFO PANELS - GAS INFO PANEL
        //==============================================================================================
        
        // Gas info panel
        panel_Gas_Info = new Rounded_Panel();
        panel_Gas_Info.setBackground(new Color(220, 220, 220));
        panel_Gas_Info.setBounds(10, 207, 447, 87);
        panel_Gas_Info.setLayout(null);
        panel_Information.add(panel_Gas_Info);
        
        // Title label for gas info panel
        lbl_Title_Gas_Info = new JLabel("Gas");
        lbl_Title_Gas_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_Gas_Info.setBounds(10, 15, 156, 32);
        panel_Gas_Info.add(lbl_Title_Gas_Info);
        
        // Reading value label for gas
        lbl_Gas_Reading_Value = new JLabel();
        lbl_Gas_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Gas_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Gas_Reading_Value.setBounds(226, 15, 100, 32);
        panel_Gas_Info.add(lbl_Gas_Reading_Value);
        
        // Unit label for gas reading
        lbl_Gas_Reading_Unit = new JLabel("Qty");
        lbl_Gas_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Gas_Reading_Unit.setBounds(337, 15, 100, 32);
        panel_Gas_Info.add(lbl_Gas_Reading_Unit);
        
        // Trend label for gas reading
        lbl_Trend_Of_Reading_Gas = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Gas.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Gas.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Gas.setBounds(113, 44, 324, 32);
        panel_Gas_Info.add(lbl_Trend_Of_Reading_Gas);
        
        //==============================================================================================
        // UI CREATION - UTILITY INFO PANELS - OVERALL INFO PANEL
        //==============================================================================================
        
        // Overall info panel
        panel_Overall_Info = new Rounded_Panel();
        panel_Overall_Info.setBackground(new Color(220, 220, 220));
        panel_Overall_Info.setBounds(10, 305, 447, 87);
        panel_Overall_Info.setLayout(null);
        panel_Information.add(panel_Overall_Info);
        
        // Title label for overall info panel
        lbl_Title_OverAll_Info = new JLabel("Overall Expenses");
        lbl_Title_OverAll_Info.setFont(new Font("Dialog", Font.PLAIN, 20));
        lbl_Title_OverAll_Info.setBounds(10, 15, 181, 32);
        panel_Overall_Info.add(lbl_Title_OverAll_Info);
        
        // Reading value label for overall expenses
        lbl_OverAll_Reading_Value = new JLabel();
        lbl_OverAll_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_OverAll_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_OverAll_Reading_Value.setBounds(226, 15, 101, 32);
        panel_Overall_Info.add(lbl_OverAll_Reading_Value);
        
        // Unit label for overall expenses reading
        lbl_OverAll_Reading_Unit = new JLabel("Php");
        lbl_OverAll_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_OverAll_Reading_Unit.setBounds(337, 15, 100, 32);
        panel_Overall_Info.add(lbl_OverAll_Reading_Unit);
        
        // Trend label for overall expenses reading
        lbl_Trend_Of_Reading_Overall = new JLabel("No avilable data");
        lbl_Trend_Of_Reading_Overall.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Trend_Of_Reading_Overall.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Trend_Of_Reading_Overall.setBounds(113, 44, 324, 32);
        panel_Overall_Info.add(lbl_Trend_Of_Reading_Overall);
        
        //==============================================================================================
        // UI CREATION - GRAPH SECTION
        //==============================================================================================
        
        // Main graph container panel
        panel_Graph_Container = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Graph_Container.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_Graph_Container.setBounds(504, 157, 413, 365);
        panel_Graph_Container.setBackground(new Color(255, 255, 255));
        panel_Graph_Container.setLayout(new BorderLayout());
        add(panel_Graph_Container);

        graph_Panel = new Graph_Panel(database_Manager.getReadingManager(), current_User, "reading");
        graph_Panel.setBackground(new Color(255, 255, 255));
        panel_Graph_Container.add(graph_Panel);
        
        // Behind panels for design time
        panel_Behind1 = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Behind1.setBackground(new Color(220, 220, 220));
        panel_Behind1.setBounds(520, 142, 413, 365);
        add(panel_Behind1);
        
        // Additional behind panels for design time
        panel_Behind2 = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Behind2.setBackground(new Color(200, 200, 200));
        panel_Behind2.setBounds(536, 129, 413, 356);
        add(panel_Behind2);
        
        // Final behind panel for design time
        panel_Behind3 = new Rounded_Panel(25, Color.BLACK, 0);
        panel_Behind3.setBackground(new Color(180, 180, 180));
        panel_Behind3.setBounds(552, 114, 413, 347);
        add(panel_Behind3);
        
        //==============================================================================================
        // UI CREATION - TIPS SECTION
        //==============================================================================================
        
        // Main tips panel
        panel_Tips = new Rounded_Panel();
        panel_Tips.setBackground(new Color(255, 255, 255));
        panel_Tips.setBounds(21, 533, 629, 144);
        panel_Tips.setLayout(null);
        add(panel_Tips);

        //===============================================================================================
        // UI CREATION - TIPS PANELS - TIP 1
        //===============================================================================================
        
        // Tips panel title
        panel_Tip_1 = new Rounded_Panel();
        panel_Tip_1.setLayout(null);
        panel_Tip_1.setBounds(10, 11, 306, 122);
        panel_Tip_1.setBackground(new Color(235, 235, 235));
        panel_Tips.add(panel_Tip_1);
        
        // Tip 1 label
        lbl_Tip_Type_1 = new JLabel("Tip 1");
        lbl_Tip_Type_1.setForeground(new Color(0, 128, 255));
        lbl_Tip_Type_1.setVerticalAlignment(SwingConstants.TOP);
        lbl_Tip_Type_1.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Tip_Type_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Tip_Type_1.setBounds(10, 11, 243, 36);
        panel_Tip_1.add(lbl_Tip_Type_1);
        
        // Tip 1 content label
        lbl_Tip_1 = new JLabel("<html>Info</html>");
        lbl_Tip_1.setBounds(20, 44, 273, 67);
        lbl_Tip_1.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Tip_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        panel_Tip_1.add(lbl_Tip_1);

        //===============================================================================================
        // UI CREATION - TIPS PANELS - TIP 2
        //===============================================================================================
        
        // Second tips panel
        panel_Tip_2 = new Rounded_Panel();
        panel_Tip_2.setLayout(null);
        panel_Tip_2.setBounds(326, 11, 291, 122);
        panel_Tip_2.setBackground(new Color(235, 235, 235));
        panel_Tips.add(panel_Tip_2);
        
        // Tip 2 label
        lbl_Tip_Type_2 = new JLabel("Tip 2");
        lbl_Tip_Type_2.setVerticalAlignment(SwingConstants.TOP);
        lbl_Tip_Type_2.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Tip_Type_2.setForeground(new Color(235, 235, 0));
        lbl_Tip_Type_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Tip_Type_2.setBounds(10, 11, 243, 36);
        panel_Tip_2.add(lbl_Tip_Type_2);
        
        // Tip 2 content label
        lbl_Tip_2 = new JLabel("<html>Info</html>");
        lbl_Tip_2.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Tip_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbl_Tip_2.setBounds(20, 44, 268, 67);
        panel_Tip_2.add(lbl_Tip_2);
        
        //==============================================================================================
        // UI CREATION - TIPS SECTION - TIMER
        //==============================================================================================
        
        // Timer for updating time label 1 minute interval
        Timer clock_timer = new Timer(60_000, e -> {
            lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        });
        LocalTime now = LocalTime.now();
        int initialDelay = (60 - now.getSecond()) * 1000 - now.getNano() / 1_000_000;
        clock_timer.setInitialDelay(initialDelay);
        clock_timer.start();
        
        // Timer for updating date label daily 30 seconds interval
        Timer tips_timer = new Timer(30_000, e -> {
        	lbl_Tip_1.setText("<html>" + utility_Tips_Manager.getRandomTip() + "</html>");
        	lbl_Tip_Type_1.setText(utility_Tips_Manager.getType());
        	lbl_Tip_Type_1.setForeground(utility_Tips_Manager.setcolor());
        	lbl_Tip_2.setText("<html>" + utility_Tips_Manager.getRandomTip() + "</html>");
        	lbl_Tip_Type_2.setText(utility_Tips_Manager.getType());
        	lbl_Tip_Type_2.setForeground(utility_Tips_Manager.setcolor());
        });
        tips_timer.setInitialDelay(0); // Start immediately
        tips_timer.start();
        
        //==============================================================================================
        // UI CREATION - VIEW DATA SECTION
        //==============================================================================================
        
        // Main container panel
        panel_View_Buttons = new Rounded_Panel();
        panel_View_Buttons.setBackground(Color.WHITE);
        panel_View_Buttons.setBounds(660, 533, 316, 144);
        panel_View_Buttons.setLayout(null);
        add(panel_View_Buttons);

        // Title label
        lbl_View_Data = new JLabel("View Data");
        lbl_View_Data.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_View_Data.setForeground(Color.BLACK);
        lbl_View_Data.setBounds(10, 11, 296, 25);
        panel_View_Buttons.add(lbl_View_Data);

        // Button group
        rdgroup_Data_Type = new ButtonGroup();

        // ---------- First Option ----------
        panel_Reading_Button = new Rounded_Panel(15, Color.BLACK, 0);
        panel_Reading_Button.setLayout(null);
        panel_Reading_Button.setBackground(new Color(220, 220, 220));
        panel_Reading_Button.setBounds(10, 45, 296, 25); // Moved up slightly
        panel_View_Buttons.add(panel_Reading_Button);

        rdbtn_Reading = new JRadioButton();
        rdbtn_Reading.setBounds(5, 0, 21, 23);
        rdbtn_Reading.setOpaque(false);
        rdbtn_Reading.setSelected(true);
        panel_Reading_Button.add(rdbtn_Reading);
        rdgroup_Data_Type.add(rdbtn_Reading);

        lbl_Reading = new JLabel("Readings");
        lbl_Reading.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Reading.setBounds(30, 0, 260, 23);
        lbl_Reading.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_Reading_Button.add(lbl_Reading);

        // ---------- Second Option ----------
        panel_Rate_Button = new Rounded_Panel(15, Color.BLACK, 0);
        panel_Rate_Button.setLayout(null);
        panel_Rate_Button.setBackground(new Color(220, 220, 220));
        panel_Rate_Button.setBounds(10, 75, 296, 25); // Even spacing
        panel_View_Buttons.add(panel_Rate_Button);

        rdbtn_Rate = new JRadioButton();
        rdbtn_Rate.setBounds(5, 0, 21, 23);
        rdbtn_Rate.setOpaque(false);
        panel_Rate_Button.add(rdbtn_Rate);
        rdgroup_Data_Type.add(rdbtn_Rate);

        lbl_Rate = new JLabel("Rate");
        lbl_Rate.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Rate.setBounds(30, 0, 260, 23);
        lbl_Rate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_Rate_Button.add(lbl_Rate);

        // ---------- Third Option ----------
        panel_Price_Button = new Rounded_Panel(15, Color.BLACK, 0);
        panel_Price_Button.setLayout(null);
        panel_Price_Button.setBackground(new Color(220, 220, 220));
        panel_Price_Button.setBounds(10, 105, 296, 25); // Bottom-aligned nicely
        panel_View_Buttons.add(panel_Price_Button);

        rdbtn_Price = new JRadioButton();
        rdbtn_Price.setBounds(5, 0, 21, 23);
        rdbtn_Price.setOpaque(false);
        panel_Price_Button.add(rdbtn_Price);
        rdgroup_Data_Type.add(rdbtn_Price);

        lbl_Price = new JLabel("Price");
        lbl_Price.setFont(new Font("Dialog", Font.PLAIN, 15));
        lbl_Price.setBounds(30, 0, 260, 23);
        lbl_Price.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_Price_Button.add(lbl_Price);

    }
    
    //==============================================================================================
    // DATA HANDLING & EVENT LISTENERS 
    //==============================================================================================
    
    // Refresh method to update the panel data
    public void home_Panel_Refresh() {setup_Data();}
    
    // Create action listeners for all interactive components
    private void create_Action_Listeners() {

    // Electricity panel
    panel_Electricity_Info.addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) { graph_Panel.showElectricityGraph(); }
        @Override public void mouseEntered(MouseEvent e) { panel_Electricity_Info.setBackground(new Color(200, 200, 200)); }
        @Override public void mouseExited(MouseEvent e) { panel_Electricity_Info.setBackground(new Color(220, 220, 220)); }
    });

    // Water panel
    panel_Water_Info.addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) { graph_Panel.showWaterGraph(); }
        @Override public void mouseEntered(MouseEvent e) { panel_Water_Info.setBackground(new Color(200, 200, 200)); }
        @Override public void mouseExited(MouseEvent e) { panel_Water_Info.setBackground(new Color(220, 220, 220)); }
    });

    // Gas panel
    panel_Gas_Info.addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) { graph_Panel.showGasGraph(); }
        @Override public void mouseEntered(MouseEvent e) { panel_Gas_Info.setBackground(new Color(200, 200, 200)); }
        @Override public void mouseExited(MouseEvent e) { panel_Gas_Info.setBackground(new Color(220, 220, 220)); }
    });

    // Overall panel
    panel_Overall_Info.addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) { graph_Panel.showOverallGraph(); }
        @Override public void mouseEntered(MouseEvent e) { panel_Overall_Info.setBackground(new Color(200, 200, 200)); }
        @Override public void mouseExited(MouseEvent e) { panel_Overall_Info.setBackground(new Color(220, 220, 220)); }
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
                graph_Panel.setField(fieldType);
                field = fieldType;
                radioButton.setSelected(true);
                setup_Data();
            }
            @Override public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(200, 200, 200));
            }
            @Override public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(220, 220, 220));
            }
        };
    }

    // Setup data for the home panel
    private void setup_Data() {
        try {
            // Initialize graph panel if needed
            if (graph_Panel == null && database_Manager != null) {
                graph_Panel = new Graph_Panel(database_Manager.getReadingManager(), current_User, field);
                graph_Panel.setBackground(Color.WHITE);
                panel_Graph_Container.removeAll();
                panel_Graph_Container.add(graph_Panel);
                panel_Graph_Container.revalidate();
                panel_Graph_Container.repaint();
            }

            // Get latest readings
            Reading electricity = database_Manager.getReadingManager().getLatest_Reading_By_Type(current_User, "electricity");
            Reading water = database_Manager.getReadingManager().getLatest_Reading_By_Type(current_User, "water");
            Reading gas = database_Manager.getReadingManager().getLatest_Reading_By_Type(current_User, "gas");

            // Update each reading's label
            database_Manager.getReadingManager().updateReading_Label(current_User,electricity, lbl_Electricity_Reading_Value, lbl_Trend_Of_Reading_Electricity,lbl_Electricity_Reading_Unit, "electricity", field);
            database_Manager.getReadingManager().updateReading_Label(current_User,water, lbl_Water_Reading_Value, lbl_Trend_Of_Reading_Water, lbl_Water_Reading_Unit, "water", field);
            database_Manager.getReadingManager().updateReading_Label(current_User,gas, lbl_Gas_Reading_Value, lbl_Trend_Of_Reading_Gas, lbl_Gas_Reading_Unit, "gas", field);

            double total = 0.0;
            try {
                // Calculate total expenses by summing latest month's totals for each utility
                total = database_Manager.getReadingManager().getLatestMonthReadingSum(current_User, "gas", "total")
                     + database_Manager.getReadingManager().getLatestMonthReadingSum(current_User, "water", "total")
                     + database_Manager.getReadingManager().getLatestMonthReadingSum(current_User, "electricity", "total");

                if (total == 0) {
                    lbl_OverAll_Reading_Value.setText("No Data");
                    lbl_Trend_Of_Reading_Overall.setText("No Data");
                } else {
                    lbl_OverAll_Reading_Value.setText(String.format("%.2f", total));
                    String trend = database_Manager.getReadingManager().getTrend(current_User, null, "total");
                    lbl_Trend_Of_Reading_Overall.setText(trend);
                    lbl_Trend_Of_Reading_Overall.setForeground(
                        database_Manager.getReadingManager().getTrend_Color(current_User, null)
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
                lbl_OverAll_Reading_Value.setText("Error");
                lbl_Trend_Of_Reading_Overall.setText("Error calculating trend");
            }


            // Refresh graph content
            graph_Panel.refreshData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
 * File: Home_Panel.java
 * 
 * Description:
 * This file defines the `Home_Panel` class, which serves as the main dashboard panel for the application. 
 * It provides a summary of the user's utility expenses, including electricity, water, and gas readings, 
 * as well as overall expenses. The panel also includes interactive graphs, utility tips, and options 
 * to view data by reading, rate, or price. The class interacts with the `Database_Manager` to fetch 
 * and display the latest utility data and trends.
 * 
 * Key Features:
 * - Displays a welcome message with the user's name, current date, and time.
 * - Shows the latest readings for electricity, water, gas, and overall expenses.
 * - Provides trends for each utility type and overall expenses.
 * - Includes a graph panel to visualize data trends.
 * - Displays utility tips that update periodically.
 * - Allows users to switch between viewing data by reading, rate, or price.
 * - Provides interactive panels for each utility type, which update the graph when clicked.
 * 
 * Variables:
 * 
 * - **Database and User Management**:
 *   - `database_Manager` (Database_Manager): Manages database operations, including reading-related actions.
 *   - `utility_Tips_Manager` (Utility_Tips_Manager): Provides random utility tips for display.
 *   - `current_User` (User): Represents the currently logged-in user.
 *   - `field` (String): Specifies the type of data to display in the graph (e.g., "reading", "rate", or "total").
 * 
 * - **Main Panel Containers**:
 *   - `panel_Welcome_Title` (JPanel): Displays the welcome message, username, date, and time.
 *   - `panel_Information` (JPanel): Contains the utility info panels for electricity, water, gas, and overall expenses.
 *   - `panel_Graph_Container` (JPanel): Contains the graph panel for visualizing data trends.
 *   - `panel_Tips` (JPanel): Displays utility tips.
 *   - `panel_View_Buttons` (JPanel): Contains buttons for switching between data types (reading, rate, price).
 * 
 * - **Utility Info Panels**:
 *   - `panel_Electricity_Info` (JPanel): Displays electricity reading, unit, and trend.
 *   - `panel_Water_Info` (JPanel): Displays water reading, unit, and trend.
 *   - `panel_Gas_Info` (JPanel): Displays gas reading, unit, and trend.
 *   - `panel_Overall_Info` (JPanel): Displays overall expenses and trend.
 * 
 * - **Reading Value Labels**:
 *   - `lbl_Electricity_Reading_Value` (JLabel): Displays the electricity reading value.
 *   - `lbl_Water_Reading_Value` (JLabel): Displays the water reading value.
 *   - `lbl_Gas_Reading_Value` (JLabel): Displays the gas reading value.
 *   - `lbl_OverAll_Reading_Value` (JLabel): Displays the overall expenses value.
 * 
 * - **Panel Title Labels**:
 *   - `lbl_Title_Welcome` (JLabel): Displays the "Welcome" title.
 *   - `lbl_Username` (JLabel): Displays the username of the logged-in user.
 *   - `lbl_Date` (JLabel): Displays the current date.
 *   - `lbl_Time` (JLabel): Displays the current time.
 *   - `lbl_Title_Electricity_Info` (JLabel): Displays the title for the electricity info panel.
 *   - `lbl_Title_Water_Info` (JLabel): Displays the title for the water info panel.
 *   - `lbl_Title_Gas_Info` (JLabel): Displays the title for the gas info panel.
 *   - `lbl_Title_OverAll_Info` (JLabel): Displays the title for the overall expenses panel.
 * 
 * - **Unit Labels**:
 *   - `lbl_Electricity_Reading_Unit` (JLabel): Displays the unit for electricity readings (e.g., "kWh").
 *   - `lbl_Water_Reading_Unit` (JLabel): Displays the unit for water readings (e.g., "m³").
 *   - `lbl_Gas_Reading_Unit` (JLabel): Displays the unit for gas readings (e.g., "Qty").
 *   - `lbl_OverAll_Reading_Unit` (JLabel): Displays the unit for overall expenses (e.g., "Php").
 * 
 * - **Graph Components**:
 *   - `graph_Panel` (Graph_Panel): Displays a graph of the selected data type (reading, rate, or price).
 *   - `panel_Behind1`, `panel_Behind2`, `panel_Behind3` (JPanel): Decorative panels behind the graph for design purposes.
 *   - `lbl_Trend_Of_Reading_Electricity` (JLabel): Displays the trend for electricity readings.
 *   - `lbl_Trend_Of_Reading_Water` (JLabel): Displays the trend for water readings.
 *   - `lbl_Trend_Of_Reading_Gas` (JLabel): Displays the trend for gas readings.
 *   - `lbl_Trend_Of_Reading_Overall` (JLabel): Displays the trend for overall expenses.
 * 
 * - **Tips Panel Components**:
 *   - `panel_Tip_1` (Rounded_Panel): Displays the first utility tip.
 *   - `panel_Tip_2` (Rounded_Panel): Displays the second utility tip.
 *   - `lbl_Tip_1` (JLabel): Displays the content of the first utility tip.
 *   - `lbl_Tip_2` (JLabel): Displays the content of the second utility tip.
 *   - `lbl_Tip_Type_1` (JLabel): Displays the type of the first utility tip.
 *   - `lbl_Tip_Type_2` (JLabel): Displays the type of the second utility tip.
 * 
 * - **View Data Section Components**:
 *   - `lbl_View_Data` (JLabel): Displays the title for the view data section.
 *   - `lbl_Reading` (JLabel): Displays the "Readings" option.
 *   - `lbl_Rate` (JLabel): Displays the "Rate" option.
 *   - `lbl_Price` (JLabel): Displays the "Price" option.
 *   - `rdgroup_Data_Type` (ButtonGroup): Groups the radio buttons for selecting the data type.
 *   - `rdbtn_Reading` (JRadioButton): Radio button for selecting "Readings".
 *   - `rdbtn_Rate` (JRadioButton): Radio button for selecting "Rate".
 *   - `rdbtn_Price` (JRadioButton): Radio button for selecting "Price".
 *   - `panel_Reading_Button` (JPanel): Panel for the "Readings" option.
 *   - `panel_Rate_Button` (JPanel): Panel for the "Rate" option.
 *   - `panel_Price_Button` (JPanel): Panel for the "Price" option.
 * 
 * Functions:
 * 
 * 1. `Home_Panel(Database_Manager, User)`:
 *    - Constructor that initializes the panel with the provided database manager and current user.
 *    - Calls `initialize_UI()` to set up the UI, `create_Action_Listeners()` to add event listeners, and `setup_Data()` to load initial data.
 * 
 * 2. `initialize_UI()`:
 *    - Sets up the panel's properties (e.g., size, layout, background color).
 *    - Creates and positions all UI components, including labels, panels, and buttons.
 * 
 * 3. `create_Action_Listeners()`:
 *    - Adds mouse listeners to the utility info panels and view data buttons to handle user interactions.
 *    - Updates the graph and data display based on user actions.
 * 
 * 4. `setup_Data()`:
 *    - Fetches the latest readings for electricity, water, gas, and overall expenses from the database.
 *    - Updates the labels and trends for each utility type and overall expenses.
 *    - Refreshes the graph panel with the selected data type.
 * 
 * 5. `home_Panel_Refresh()`:
 *    - Refreshes the panel data by calling `setup_Data()`.
 * 
 * Usage:
 * This class is used as the main dashboard panel for the application. 
 * It provides a comprehensive summary of the user's utility expenses and trends, along with interactive features for data visualization and tips.
 */
