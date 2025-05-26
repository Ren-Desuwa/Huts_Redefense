package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import database.Database_Manager;
import model.User;
import view.Main_Frame;
import view.panel.misc.Change_Password_Window;
import view.panel.misc.Edit_Profile_Window;
import visuals.Circle_Panel;
import visuals.Following_Tool_Tip;
import visuals.Rounded_Button;
import visuals.Rounded_Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class Profile_Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    //==============================================================================
    // FIELDS
    //==============================================================================
    
    // Data fields
    private Database_Manager database_manager;
    private User current_user;
    private Main_Frame main_frame;
    private Profile_Panel profile_Panel;
    
    // UI Components
    private JPanel panel_Main;
    private JPanel panel_Header;
    private JPanel panel_Content;
    private JPanel panel_Profile_Image;
    private JPanel panel_Statistics;
    private JPanel panel_Electricity_Stats;
    private JPanel panel_Water_Stats;
    private JPanel panel_Gas_Stats;
    
    // Labels
    private JLabel lbl_Profile_Initials;
    private JLabel lbl_Edit_Profile;
    private JLabel lbl_Email;
    private JLabel lbl_Username;
    private JLabel lbl_Account_Information;
    private JLabel lbl_Username_Prop;
    private JLabel lbl_Username_Value;
    private JLabel lbl_Email_Prop;
    private JLabel lbl_Email_Value;
    private JLabel lbl_Total_Submissions_Prop;
    private JLabel lbl_Total_Submissions_Value;
    private JLabel lbl_Usage_Statistics;
    
    // Statistics Labels
    private JLabel lbl_Electricity_Count;
    private JLabel lbl_Electricity_Label;
    private JLabel lbl_Electricity_Readings;
    private JLabel lbl_Water_Count;
    private JLabel lbl_Water_Label;
    private JLabel lbl_Water_Readings;
    private JLabel lbl_Gas_Count;
    private JLabel lbl_Gas_Label;
    private JLabel lbl_Gas_Readings;
    
    // Buttons
    private JButton btn_Change_Password;
    
    // Separators
    private JSeparator separator_Account_Info;
    private JSeparator separator_Statistics;
    private JSeparator separator_Actions;
    
    
    @SuppressWarnings("unused")
	private Following_Tool_Tip tooltip_Electricity;
    @SuppressWarnings("unused")
	private Following_Tool_Tip tooltip_Water;
    @SuppressWarnings("unused")
	private Following_Tool_Tip tooltip_Gas;
    
    //==============================================================================
    // CONSTRUCTOR
    //==============================================================================
    
    public Profile_Panel(Main_Frame main_frame,Database_Manager database_manager, User current_user) {
        this.database_manager = database_manager;
        this.current_user = current_user;
        this.main_frame = main_frame;
        this.profile_Panel = this; 
        
        setPreferredSize(new Dimension(986, 688));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        initialize_UI();
        createActionListeners();
        updateUserInfo(current_user);
    }
    
    private void initialize_UI() {

    	//=============================================================================
    	// UI COMPONENTS - MAIN PANEL
    	//=============================================================================
    	
    	// Create the main panel with rounded corners and a light background
        panel_Main = new Rounded_Panel(100, Color.BLACK, 0);
        panel_Main.setBackground(new Color(250, 250, 250));
        panel_Main.setLayout(new BorderLayout());
        add(panel_Main, BorderLayout.CENTER);
        
        //=============================================================================
        // UI COMPONENTS - HEADER
        //=============================================================================
       
        // Create the header panel with a specific background color and preferred size
        panel_Header = new JPanel();
        panel_Header.setBackground(new Color(68, 162, 255));
        panel_Header.setPreferredSize(new Dimension(0, 300));
        panel_Header.setLayout(null);
        panel_Main.add(panel_Header, BorderLayout.NORTH);
        
        // circle panel for profile image
        panel_Profile_Image = new Circle_Panel(245);
        panel_Profile_Image.setBackground(new Color(68, 162, 255));
        panel_Profile_Image.setBounds(10, 23, 250, 250);
        panel_Profile_Image.setLayout(null);
        panel_Header.add(panel_Profile_Image);
        
        // label for profile initials
        lbl_Profile_Initials = new JLabel("UN");
        lbl_Profile_Initials.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Profile_Initials.setBounds(10, 11, 230, 239);
        lbl_Profile_Initials.setForeground(Color.WHITE);
        lbl_Profile_Initials.setFont(new Font("Tahoma", Font.PLAIN, 99));
        panel_Profile_Image.add(lbl_Profile_Initials);
    
        //=============================================================================
        // UI COMPONENTS - HEADER USER INFO
        //=============================================================================
        
        // Labels for user information
        lbl_Email = new JLabel("Email@gmail.com");
        lbl_Email.setForeground(Color.WHITE);
        lbl_Email.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Email.setBounds(270, 177, 263, 55);
        panel_Header.add(lbl_Email);
        
        // Label for username
        lbl_Username = new JLabel("User Name");
        lbl_Username.setForeground(Color.WHITE);
        lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 44));
        lbl_Username.setBounds(270, 107, 293, 82);
        panel_Header.add(lbl_Username);
        
        // Label for edit profile link
        lbl_Edit_Profile = new JLabel("Edit Profile");
        lbl_Edit_Profile.setForeground(Color.WHITE);
        lbl_Edit_Profile.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Edit_Profile.setBounds(826, 23, 110, 25);
        panel_Header.add(lbl_Edit_Profile);
    
        //=============================================================================
        // UI COMPONENTS - CONTENT
        //=============================================================================
        
        // Create the content panel with a specific background color and layout
        panel_Content = new JPanel();
        panel_Content.setBackground(new Color(250, 250, 250));
        panel_Content.setLayout(null);
        panel_Main.add(panel_Content, BorderLayout.CENTER);
        
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO
        //=============================================================================
        
        // Label for account information section
        lbl_Account_Information = new JLabel("Account Inforamtion");
        lbl_Account_Information.setForeground(Color.BLACK);
        lbl_Account_Information.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Account_Information.setBounds(10, 11, 263, 25);
        panel_Content.add(lbl_Account_Information);
        
        // Separator for account information section
        separator_Account_Info = new JSeparator(SwingConstants.HORIZONTAL);
        separator_Account_Info.setForeground(new Color(64, 64, 64));
        separator_Account_Info.setBounds(10, 42, 455, 25);
        panel_Content.add(separator_Account_Info);
        
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - USERNAME
        //=============================================================================
        
        // Label for username property
        lbl_Username_Prop = new JLabel("Username");
        lbl_Username_Prop.setForeground(Color.BLACK);
        lbl_Username_Prop.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Username_Prop.setBounds(20, 58, 134, 25);
        panel_Content.add(lbl_Username_Prop);
        
        // Label for username value
        lbl_Username_Value = new JLabel("Username");
        lbl_Username_Value.setForeground(Color.BLACK);
        lbl_Username_Value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Username_Value.setBounds(202, 58, 263, 25);
        panel_Content.add(lbl_Username_Value);
        
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - EMAIL
        //=============================================================================
        
        // Label for email property
        lbl_Email_Prop = new JLabel("Email");
        lbl_Email_Prop.setForeground(Color.BLACK);
        lbl_Email_Prop.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Email_Prop.setBounds(20, 94, 134, 25);
        panel_Content.add(lbl_Email_Prop);
        
        // Label for email value
        lbl_Email_Value = new JLabel("Email@gmail.com");
        lbl_Email_Value.setForeground(Color.BLACK);
        lbl_Email_Value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Email_Value.setBounds(202, 94, 263, 25);
        panel_Content.add(lbl_Email_Value);
        
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - TOTAL SUBMISSIONS
        //=============================================================================
        
        // Label for total submissions property
        lbl_Total_Submissions_Prop = new JLabel("Total Submissions");
        lbl_Total_Submissions_Prop.setForeground(Color.BLACK);
        lbl_Total_Submissions_Prop.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Total_Submissions_Prop.setBounds(20, 130, 165, 25);
        panel_Content.add(lbl_Total_Submissions_Prop);
        
        // Label for total submissions value
        lbl_Total_Submissions_Value = new JLabel();
        lbl_Total_Submissions_Value.setForeground(Color.BLACK);
        lbl_Total_Submissions_Value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Total_Submissions_Value.setBounds(202, 130, 263, 25);
        panel_Content.add(lbl_Total_Submissions_Value);
   
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - STATISTICS
        //=============================================================================
        
        // Create the statistics panel with a specific background color and layout
        panel_Statistics = new JPanel();
        panel_Statistics.setBackground(new Color(250, 250, 250));
        panel_Statistics.setBounds(475, 11, 471, 220);
        panel_Statistics.setLayout(null);
        panel_Content.add(panel_Statistics);
        
        // Label for usage statistics section
        lbl_Usage_Statistics = new JLabel("Utility Submissions");
        lbl_Usage_Statistics.setBackground(new Color(250, 250, 250));
        lbl_Usage_Statistics.setForeground(Color.BLACK);
        lbl_Usage_Statistics.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Usage_Statistics.setBounds(10, 0, 263, 25);
        panel_Statistics.add(lbl_Usage_Statistics);
        
        // Separator for usage statistics section
        separator_Statistics = new JSeparator(SwingConstants.HORIZONTAL);
        separator_Statistics.setForeground(Color.DARK_GRAY);
        separator_Statistics.setBounds(10, 29, 451, 25);
        panel_Statistics.add(separator_Statistics);
        
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - STATISTICS - ELECTRICITY
        //=============================================================================
        
        // Create the electricity statistics panel with a specific background color and layout
        panel_Electricity_Stats = new Rounded_Panel(15);
        panel_Electricity_Stats.setBackground(new Color(218, 218, 218));
        panel_Electricity_Stats.setBounds(10, 36, 140, 174);
        panel_Electricity_Stats.setLayout(null);
        panel_Statistics.add(panel_Electricity_Stats);
        
        // Labels for electricity statistics
        lbl_Electricity_Count = new JLabel("12");
        lbl_Electricity_Count.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Electricity_Count.setForeground(Color.BLACK);
        lbl_Electricity_Count.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lbl_Electricity_Count.setBounds(0, 11, 140, 72);
        panel_Electricity_Stats.add(lbl_Electricity_Count);
        
        // Label for electricity label
        lbl_Electricity_Label = new JLabel("Electricity");
        lbl_Electricity_Label.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Electricity_Label.setForeground(Color.BLACK);
        lbl_Electricity_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Electricity_Label.setBounds(0, 90, 140, 25);
        panel_Electricity_Stats.add(lbl_Electricity_Label);
        
        // Label for electricity readings
        lbl_Electricity_Readings = new JLabel("Readings");
        lbl_Electricity_Readings.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Electricity_Readings.setForeground(Color.BLACK);
        lbl_Electricity_Readings.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Electricity_Readings.setBounds(0, 115, 140, 25);
        panel_Electricity_Stats.add(lbl_Electricity_Readings);
        
        // Tooltip for electricity statistics panel
        tooltip_Electricity = new Following_Tool_Tip(panel_Electricity_Stats, "Open Electricity Reading?", 500);
    
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - STATISTICS - WATER
        //=============================================================================
        
        // Create the water statistics panel with a specific background color and layout
        panel_Water_Stats = new Rounded_Panel(15);
        panel_Water_Stats.setBackground(new Color(218, 218, 218));
        panel_Water_Stats.setBounds(166, 36, 140, 174);
        panel_Water_Stats.setLayout(null);
        panel_Statistics.add(panel_Water_Stats);
        
        // Labels for water statistics
        lbl_Water_Readings = new JLabel("Readings");
        lbl_Water_Readings.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Water_Readings.setForeground(Color.BLACK);
        lbl_Water_Readings.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Water_Readings.setBounds(0, 115, 140, 25);
        panel_Water_Stats.add(lbl_Water_Readings);
        
        // Label for water label
        lbl_Water_Label = new JLabel("Water");
        lbl_Water_Label.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Water_Label.setForeground(Color.BLACK);
        lbl_Water_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Water_Label.setBounds(0, 90, 140, 25);
        panel_Water_Stats.add(lbl_Water_Label);
        
        // Label for water count
        lbl_Water_Count = new JLabel("6");
        lbl_Water_Count.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Water_Count.setForeground(Color.BLACK);
        lbl_Water_Count.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lbl_Water_Count.setBounds(0, 11, 140, 72);
        panel_Water_Stats.add(lbl_Water_Count);
        
        // Tooltip for water statistics panel
        tooltip_Water = new Following_Tool_Tip(panel_Water_Stats, "Open Water Reading?", 500);
    
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - STATISTICS - GAS
        //=============================================================================
        
        // Create the gas statistics panel with a specific background color and layout
        panel_Gas_Stats = new Rounded_Panel(15);
        panel_Gas_Stats.setBackground(new Color(218, 218, 218));
        panel_Gas_Stats.setBounds(321, 36, 140, 174);
        panel_Gas_Stats.setLayout(null);
        panel_Statistics.add(panel_Gas_Stats);
        
        // Labels for gas statistics
        lbl_Gas_Readings = new JLabel("Readings");
        lbl_Gas_Readings.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Gas_Readings.setForeground(Color.BLACK);
        lbl_Gas_Readings.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Gas_Readings.setBounds(0, 115, 140, 25);
        panel_Gas_Stats.add(lbl_Gas_Readings);
        
        // Label for gas label
        lbl_Gas_Label = new JLabel("Gas");
        lbl_Gas_Label.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Gas_Label.setForeground(Color.BLACK);
        lbl_Gas_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Gas_Label.setBounds(0, 90, 140, 25);
        panel_Gas_Stats.add(lbl_Gas_Label);
        
        // Label for gas count
        lbl_Gas_Count = new JLabel("4");
        lbl_Gas_Count.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Gas_Count.setForeground(Color.BLACK);
        lbl_Gas_Count.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lbl_Gas_Count.setBounds(0, 11, 140, 72);
        panel_Gas_Stats.add(lbl_Gas_Count);
        
        // Tooltip for gas statistics panel
        tooltip_Gas = new Following_Tool_Tip(panel_Gas_Stats, "Open Gas Reading?", 500);
    
        //=============================================================================
        // UI COMPONENTS - CONTENT - ACCOUNT INFO - STATISTICS - BUTTONS
        //=============================================================================

        // Label for actions section
        separator_Actions = new JSeparator(SwingConstants.HORIZONTAL);
        separator_Actions.setForeground(Color.DARK_GRAY);
        separator_Actions.setBounds(10, 250, 926, 25);
        panel_Content.add(separator_Actions);
        
        // Button to change password
        btn_Change_Password = new Rounded_Button("Change Password", 25);
        btn_Change_Password.setBounds(760, 286, 165, 40);
        btn_Change_Password.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_Change_Password.setMaximumSize(new Dimension(180, 40));
        btn_Change_Password.setBackground(new Color(192, 192, 192));
        btn_Change_Password.setForeground(Color.BLACK);
        btn_Change_Password.setFocusPainted(false);
        btn_Change_Password.setBorderPainted(false);
        btn_Change_Password.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_Content.add(btn_Change_Password);
        
    }
    
    private void createActionListeners() {
        btn_Change_Password.addActionListener(e -> createChangePasswordWindow());
        btn_Change_Password.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {btn_Change_Password.setBackground(new Color(128, 128, 128));}
			public void mouseExited(MouseEvent e) {btn_Change_Password.setBackground(new Color(192, 192, 192));}
		});

        lbl_Edit_Profile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {openEditProfileWindow();}
            public void mouseEntered(MouseEvent e) {lbl_Edit_Profile.setForeground(Color.BLUE);}
            public void mouseExited(MouseEvent e) {lbl_Edit_Profile.setForeground(Color.WHITE);}
        });

        addPanelClickListener(panel_Electricity_Stats, main_frame::showElectricityPanel);
        addPanelClickListener(panel_Water_Stats, main_frame::showWaterPanel);
        addPanelClickListener(panel_Gas_Stats, main_frame::showGasPanel);
    }

    private void addPanelClickListener(JPanel panel, Runnable action) {
        panel.addMouseListener(new MouseAdapter() {public void mouseClicked(MouseEvent e) {action.run();}});
    }
    
    public void updateUserInfo(User user) {
    	this.current_user = user;
        if (current_user == null) {
            return;
        }
        
        // Update header information
        String username = current_user.getUsername(); // Assuming getter exists
        String email = current_user.getEmail(); // Assuming getter exists
        int totalSubmissions; // Assuming getter exists
        int electricityCount; // Assuming getter exists
        int waterCount; // Assuming getter existsq
        int gasCount; // Assuming getter exists
        
        try {
			totalSubmissions = database_manager.getReadingManager().getTotal_Readings(current_user);
			electricityCount = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, "electricity").size();
			waterCount = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, "water").size();
			gasCount = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, "gas").size();
			
		} catch (SQLException e) {
			totalSubmissions = 0; // Default to 0 if there's an error
			electricityCount = 0; // Default to 0 if there's an error
			waterCount = 0; // Default to 0 if there's an error
			gasCount = 0; // Default to 0 if there's an error
			e.printStackTrace();
		}
        
        lbl_Username.setText(username);
        lbl_Email.setText(email);
        lbl_Username_Value.setText(username);
        lbl_Email_Value.setText(email);
        lbl_Total_Submissions_Value.setText(String.valueOf(totalSubmissions));
        lbl_Electricity_Count.setText(String.valueOf(electricityCount)); // Assuming getter exists
        lbl_Water_Count.setText(String.valueOf(waterCount)); // Assuming getter exists
        lbl_Gas_Count.setText(String.valueOf(gasCount)); // Assuming getter exists
        
        if (username != null && !username.isEmpty()) {
            lbl_Profile_Initials.setText(username.substring(0, Math.min(2, username.length())).toUpperCase());
        }
        
    }
    
    private void createChangePasswordWindow() {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Change_Password_Window window = new Change_Password_Window((JFrame) SwingUtilities.getWindowAncestor(Profile_Panel.this), database_manager, current_user);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    private void openEditProfileWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Create and show the edit profile window
					 Edit_Profile_Window window = new Edit_Profile_Window(profile_Panel ,database_manager, current_user);
					 window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

/*
 * File: Profile_Panel.java
 *
 * Description:
 * This file defines the `Profile_Panel` class, which is a `JPanel` used for displaying and managing the user's profile information.
 * It provides a graphical interface for viewing account details, utility submission statistics, and performing actions such as editing the profile or changing the password.
 * The class interacts with the `Database_Manager` to fetch and update user-related data.
 *
 * Variables:
 *
 * - **Data Fields**:
 *   - `database_manager` (Database_Manager): Manages database operations, including user and reading-related actions.
 *   - `current_user` (User): Represents the currently logged-in user.
 *   - `main_frame` (Main_Frame): Reference to the main application frame for navigation.
 *   - `profile_Panel` (Profile_Panel): Reference to the current instance of the profile panel.
 *
 * - **UI Components**:
 *   - `panel_Main` (Rounded_Panel): The main container for the profile panel.
 *   - `panel_Header` (JPanel): The header section containing the user's profile image and basic information.
 *   - `panel_Content` (JPanel): The content section containing account details and statistics.
 *   - `panel_Profile_Image` (Circle_Panel): A circular panel displaying the user's initials as a profile image.
 *   - `panel_Statistics` (JPanel): A container for utility submission statistics.
 *   - `panel_Electricity_Stats`, `panel_Water_Stats`, `panel_Gas_Stats` (Rounded_Panel): Panels displaying statistics for electricity, water, and gas submissions.
 *
 * - **Labels**:
 *   - `lbl_Profile_Initials` (JLabel): Displays the user's initials in the profile image panel.
 *   - `lbl_Edit_Profile` (JLabel): A clickable label for opening the edit profile window.
 *   - `lbl_Email`, `lbl_Username` (JLabel): Display the user's email and username in the header.
 *   - `lbl_Account_Information`, `lbl_Username_Prop`, `lbl_Username_Value`, `lbl_Email_Prop`, `lbl_Email_Value`, `lbl_Total_Submissions_Prop`, `lbl_Total_Submissions_Value` (JLabel): Display account details in the content section.
 *   - `lbl_Usage_Statistics`, `lbl_Electricity_Count`, `lbl_Electricity_Label`, `lbl_Electricity_Readings`, `lbl_Water_Count`, `lbl_Water_Label`, `lbl_Water_Readings`, `lbl_Gas_Count`, `lbl_Gas_Label`, `lbl_Gas_Readings` (JLabel): Display utility submission statistics.
 *
 * - **Buttons**:
 *   - `btn_Change_Password` (Rounded_Button): Button to open the change password window.
 *
 * - **Separators**:
 *   - `separator_Account_Info`, `separator_Statistics`, `separator_Actions` (JSeparator): Separators for organizing sections in the content panel.
 *
 * - **Tooltips**:
 *   - `tooltip_Electricity`, `tooltip_Water`, `tooltip_Gas` (Following_Tool_Tip): Tooltips for the utility statistics panels.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Profile_Panel(Main_Frame, Database_Manager, User)`:
 *      - Initializes the profile panel with the provided main frame, database manager, and current user.
 *      - Calls `initialize_UI()` to set up the UI, `createActionListeners()` to add event listeners, and `updateUserInfo()` to populate the panel with user data.
 *
 * 2. **initialize_UI()**:
 *    - Sets up the layout and properties of the profile panel.
 *    - Creates and positions all UI components, including the header, content, and statistics sections.
 *
 * 3. **createActionListeners()**:
 *    - Adds event listeners to buttons and labels for handling user interactions.
 *    - Handles actions such as opening the edit profile window, changing the password, and navigating to utility panels.
 *
 * 4. **updateUserInfo(User)**:
 *    - Updates the profile panel with the current user's information.
 *    - Fetches and displays the user's username, email, total submissions, and utility statistics.
 *    - Updates the profile initials based on the username.
 *
 * 5. **createChangePasswordWindow()**:
 *    - Opens the `Change_Password_Window` dialog for changing the user's password.
 *
 * 6. **openEditProfileWindow()**:
 *    - Opens the `Edit_Profile_Window` dialog for editing the user's profile.
 *
 * 7. **addPanelClickListener(JPanel, Runnable)**:
 *    - Adds a mouse click listener to a panel to trigger a specified action.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for managing the user's profile.
 * It allows the user to view account details, utility submission statistics, and perform actions such as editing the profile or changing the password.
 */
