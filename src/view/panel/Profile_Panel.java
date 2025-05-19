package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import database.Database_Manager;
import model.User;
import visuals.Circle_Panel;
import visuals.Rounded_Panel;

public class Profile_Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Data fields
    private Database_Manager database_manager;
    private User current_user;
    
    // UI Components
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel profileImagePanel;
    private JPanel statisticsPanel;
    private JPanel electricityStatsPanel;
    private JPanel waterStatsPanel;
    private JPanel gasStatsPanel;
    
    // Labels
    private JLabel lblProfileInitials;
    private JLabel lblEmail;
    private JLabel lblUsername;
    private JLabel lblAccountInformation;
    private JLabel lblUsernameProp;
    private JLabel lblUsernameValue;
    private JLabel lblEmailProp;
    private JLabel lblEmailValue;
    private JLabel lblDateJoinedProp;
    private JLabel lblDateJoinedValue;
    private JLabel lblUsageStatistics;
    private JLabel lblActions;
    
    // Statistics Labels
    private JLabel lblElectricityCount;
    private JLabel lblElectricityLabel;
    private JLabel lblElectricityReadings;
    private JLabel lblWaterCount;
    private JLabel lblWaterLabel;
    private JLabel lblWaterReadings;
    private JLabel lblGasCount;
    private JLabel lblGasLabel;
    private JLabel lblGasReadings;
    
    // Buttons
    private JButton btnChangePassword;
    
    // Separators
    private JSeparator separatorAccountInfo;
    private JSeparator separatorStatistics;
    private JSeparator separatorActions;
    
    /**
     * Create the panel.
     */
    public Profile_Panel(Database_Manager database_manager, User current_user) {
        this.database_manager = database_manager;
        this.current_user = current_user;
        
        initializePanelProperties();
        createMainPanels();
        createHeaderPanel();
        createContentPanel();
        createStatisticsPanel();
        createActionsSection();
    }
    
    /**
     * Initialize the panel's properties
     */
    private void initializePanelProperties() {
        setPreferredSize(new Dimension(986, 688));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
    
    /**
     * Create main panel structure
     */
    private void createMainPanels() {
        mainPanel = new Rounded_Panel(100, Color.BLACK, 0);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create the header panel with profile image and info
     */
    private void createHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(68, 162, 255));
        headerPanel.setPreferredSize(new Dimension(0, 300));
        headerPanel.setLayout(null);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        createProfileImagePanel();
        createHeaderLabels();
    }
    
    /**
     * Create the profile image panel
     */
    private void createProfileImagePanel() {
        profileImagePanel = new Circle_Panel(245);
        profileImagePanel.setBackground(new Color(68, 162, 255));
        profileImagePanel.setBounds(10, 23, 250, 250);
        profileImagePanel.setLayout(null);
        headerPanel.add(profileImagePanel);
        
        lblProfileInitials = new JLabel("UN");
        lblProfileInitials.setHorizontalAlignment(SwingConstants.CENTER);
        lblProfileInitials.setBounds(10, 11, 230, 239);
        lblProfileInitials.setForeground(Color.WHITE);
        lblProfileInitials.setFont(new Font("Tahoma", Font.PLAIN, 99));
        profileImagePanel.add(lblProfileInitials);
    }
    
    /**
     * Create header labels for username and email
     */
    private void createHeaderLabels() {
        lblEmail = new JLabel("Email@gmail.com");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmail.setBounds(270, 177, 263, 55);
        headerPanel.add(lblEmail);
        
        lblUsername = new JLabel("User Name");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 44));
        lblUsername.setBounds(270, 107, 293, 82);
        headerPanel.add(lblUsername);
    }
    
    /**
     * Create the content panel with account information
     */
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        createAccountInformationSection();
    }
    
    /**
     * Create account information section
     */
    private void createAccountInformationSection() {
        lblAccountInformation = new JLabel("Account Inforamtion");
        lblAccountInformation.setForeground(Color.BLACK);
        lblAccountInformation.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblAccountInformation.setBounds(10, 11, 263, 25);
        contentPanel.add(lblAccountInformation);
        
        separatorAccountInfo = new JSeparator(SwingConstants.HORIZONTAL);
        separatorAccountInfo.setForeground(new Color(64, 64, 64));
        separatorAccountInfo.setBounds(10, 42, 455, 25);
        contentPanel.add(separatorAccountInfo);
        
        // Username section
        lblUsernameProp = new JLabel("Username");
        lblUsernameProp.setForeground(Color.BLACK);
        lblUsernameProp.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsernameProp.setBounds(20, 58, 134, 25);
        contentPanel.add(lblUsernameProp);
        
        lblUsernameValue = new JLabel("Username");
        lblUsernameValue.setForeground(Color.BLACK);
        lblUsernameValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsernameValue.setBounds(183, 58, 263, 25);
        contentPanel.add(lblUsernameValue);
        
        // Email section
        lblEmailProp = new JLabel("Email");
        lblEmailProp.setForeground(Color.BLACK);
        lblEmailProp.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailProp.setBounds(20, 94, 134, 25);
        contentPanel.add(lblEmailProp);
        
        lblEmailValue = new JLabel("Email@gmail.com");
        lblEmailValue.setForeground(Color.BLACK);
        lblEmailValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailValue.setBounds(183, 94, 263, 25);
        contentPanel.add(lblEmailValue);
        
        // Date joined section
        lblDateJoinedProp = new JLabel("Date Joined");
        lblDateJoinedProp.setForeground(Color.BLACK);
        lblDateJoinedProp.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblDateJoinedProp.setBounds(20, 130, 134, 25);
        contentPanel.add(lblDateJoinedProp);
        
        lblDateJoinedValue = new JLabel("4/5/2025");
        lblDateJoinedValue.setForeground(Color.BLACK);
        lblDateJoinedValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblDateJoinedValue.setBounds(183, 130, 263, 25);
        contentPanel.add(lblDateJoinedValue);
    }
    
    /**
     * Create statistics panel with usage metrics
     */
    private void createStatisticsPanel() {
        statisticsPanel = new JPanel();
        statisticsPanel.setBounds(475, 11, 471, 220);
        statisticsPanel.setLayout(null);
        contentPanel.add(statisticsPanel);
        
        lblUsageStatistics = new JLabel("Usage Statistics");
        lblUsageStatistics.setForeground(Color.BLACK);
        lblUsageStatistics.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsageStatistics.setBounds(10, 0, 263, 25);
        statisticsPanel.add(lblUsageStatistics);
        
        separatorStatistics = new JSeparator(SwingConstants.HORIZONTAL);
        separatorStatistics.setForeground(Color.DARK_GRAY);
        separatorStatistics.setBounds(10, 29, 451, 25);
        statisticsPanel.add(separatorStatistics);
        
        createElectricityPanel();
        createWaterPanel();
        createGasPanel();
    }
    
    /**
     * Create electricity statistics panel
     */
    private void createElectricityPanel() {
        electricityStatsPanel = new Rounded_Panel(15);
        electricityStatsPanel.setBackground(new Color(218, 218, 218));
        electricityStatsPanel.setBounds(10, 36, 140, 174);
        electricityStatsPanel.setLayout(null);
        statisticsPanel.add(electricityStatsPanel);
        
        lblElectricityCount = new JLabel("12");
        lblElectricityCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblElectricityCount.setForeground(Color.BLACK);
        lblElectricityCount.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lblElectricityCount.setBounds(0, 11, 140, 72);
        electricityStatsPanel.add(lblElectricityCount);
        
        lblElectricityLabel = new JLabel("Electricity");
        lblElectricityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblElectricityLabel.setForeground(Color.BLACK);
        lblElectricityLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblElectricityLabel.setBounds(0, 90, 140, 25);
        electricityStatsPanel.add(lblElectricityLabel);
        
        lblElectricityReadings = new JLabel("Readings");
        lblElectricityReadings.setHorizontalAlignment(SwingConstants.CENTER);
        lblElectricityReadings.setForeground(Color.BLACK);
        lblElectricityReadings.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblElectricityReadings.setBounds(0, 115, 140, 25);
        electricityStatsPanel.add(lblElectricityReadings);
    }
    
    /**
     * Create water statistics panel
     */
    private void createWaterPanel() {
        waterStatsPanel = new Rounded_Panel(15);
        waterStatsPanel.setBackground(new Color(218, 218, 218));
        waterStatsPanel.setBounds(166, 36, 140, 174);
        waterStatsPanel.setLayout(null);
        statisticsPanel.add(waterStatsPanel);
        
        lblWaterReadings = new JLabel("Readings");
        lblWaterReadings.setHorizontalAlignment(SwingConstants.CENTER);
        lblWaterReadings.setForeground(Color.BLACK);
        lblWaterReadings.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblWaterReadings.setBounds(0, 115, 140, 25);
        waterStatsPanel.add(lblWaterReadings);
        
        lblWaterLabel = new JLabel("Water");
        lblWaterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblWaterLabel.setForeground(Color.BLACK);
        lblWaterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblWaterLabel.setBounds(0, 90, 140, 25);
        waterStatsPanel.add(lblWaterLabel);
        
        lblWaterCount = new JLabel("6");
        lblWaterCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblWaterCount.setForeground(Color.BLACK);
        lblWaterCount.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lblWaterCount.setBounds(0, 11, 140, 72);
        waterStatsPanel.add(lblWaterCount);
    }
    
    /**
     * Create gas statistics panel
     */
    private void createGasPanel() {
        gasStatsPanel = new Rounded_Panel(15);
        gasStatsPanel.setBackground(new Color(218, 218, 218));
        gasStatsPanel.setBounds(321, 36, 140, 174);
        gasStatsPanel.setLayout(null);
        statisticsPanel.add(gasStatsPanel);
        
        lblGasReadings = new JLabel("Readings");
        lblGasReadings.setHorizontalAlignment(SwingConstants.CENTER);
        lblGasReadings.setForeground(Color.BLACK);
        lblGasReadings.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblGasReadings.setBounds(0, 115, 140, 25);
        gasStatsPanel.add(lblGasReadings);
        
        lblGasLabel = new JLabel("Gas");
        lblGasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblGasLabel.setForeground(Color.BLACK);
        lblGasLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblGasLabel.setBounds(0, 90, 140, 25);
        gasStatsPanel.add(lblGasLabel);
        
        lblGasCount = new JLabel("4");
        lblGasCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblGasCount.setForeground(Color.BLACK);
        lblGasCount.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lblGasCount.setBounds(0, 11, 140, 72);
        gasStatsPanel.add(lblGasCount);
    }
    
    /**
     * Create actions section with buttons
     */
    private void createActionsSection() {
        btnChangePassword = new JButton("Change Password");
        btnChangePassword.setBounds(760, 286, 165, 40);
        contentPanel.add(btnChangePassword);
        
        lblActions = new JLabel("Actions");
        lblActions.setForeground(Color.BLACK);
        lblActions.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblActions.setBounds(10, 219, 263, 25);
        contentPanel.add(lblActions);
        
        separatorActions = new JSeparator(SwingConstants.HORIZONTAL);
        separatorActions.setForeground(Color.DARK_GRAY);
        separatorActions.setBounds(10, 250, 926, 25);
        contentPanel.add(separatorActions);
    }
    
    /**
     * Update the panel with user information
     * Note: This method would be called after construction to populate user data
     */
    public void updateUserInfo() {
        if (current_user == null) {
            return;
        }
        
        // Update header information
        String username = current_user.getUsername(); // Assuming getter exists
        String email = current_user.getEmail(); // Assuming getter exists
        
        lblUsername.setText(username);
        lblEmail.setText(email);
        lblUsernameValue.setText(username);
        lblEmailValue.setText(email);
        
        // Set profile initials
        if (username != null && !username.isEmpty()) {
            lblProfileInitials.setText(username.substring(0, Math.min(2, username.length())).toUpperCase());
        }
        
        // Update other user-specific information
        // This would be implemented based on your User class structure
    }
}