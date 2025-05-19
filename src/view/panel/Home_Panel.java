package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.Reading;
import model.User;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.CardLayout;

/**
 * Home Panel for displaying user utility data and graphs
 */
public class Home_Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Database and user fields
    private Database_Manager databaseManager;
    private User currentUser;
    
    // Main panels
    private JPanel panelWelcomeTitle;
    private JPanel panelInformation;
    private JPanel panelTips;
    private JPanel panelGraphContainer;
    
    // Utility info panels
    private JPanel panelElectricityInfo;
    private JPanel panelWaterInfo;
    private JPanel panelGasInfo;
    private JPanel panelOverallInfo;
    
    // Reading value labels
    private JLabel lblElectricityReadingValue;
    private JLabel lblWaterReadingValue;
    private JLabel lblGasReadingValue;
    private JLabel lblOverAllReadingValue;
    
    // Title labels
    private JLabel lblTitleWelcome;
    private JLabel lblUsername;
    private JLabel lblDate;
    private JLabel lblTitleElectricityInfo;
    private JLabel lblTitleWaterInfo;
    private JLabel lblTitleGasInfo;
    private JLabel lblTitleOverAllInfo;
    
    // Unit labels
    private JLabel lblElectricityReadingUnit;
    private JLabel lblWaterReadingUnit;
    private JLabel lblGasReadingUnit;
    private JLabel lblOverAllReadingUnit;
    
    // Graph panels
    private CardLayout graphCardLayout;
    private JPanel electricityGraphPanel;
    private JPanel waterGraphPanel;
    private JPanel gasGraphPanel;
    private JPanel overallGraphPanel;
    
    /**
     * Constructor for the Home Panel
     * 
     * @param databaseManager Database manager for accessing data
     * @param currentUser The current logged-in user
     */
    public Home_Panel(Database_Manager databaseManager, User currentUser) {
        this.databaseManager = databaseManager;
        this.currentUser = currentUser;
        
        initializePanelProperties();
        createMainPanels();
        createHeaderPanel();
        createContentPanel();
        createTipsPanel();
        createGraphPanel();
        createActionListeners();
        setupData();
    }
    
    /**
     * Initialize the panel's properties
     */
    private void initializePanelProperties() {
        setPreferredSize(new Dimension(986, 688));
        setLayout(null);
    }
    
    /**
     * Create main panels structure
     */
    private void createMainPanels() {
        // This is a container method - the actual panels are created in their respective methods
    }
    
    /**
     * Create the header panel with welcome title
     */
    private void createHeaderPanel() {
        panelWelcomeTitle = new JPanel();
        panelWelcomeTitle.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelWelcomeTitle.setBounds(21, 11, 944, 85);
        panelWelcomeTitle.setLayout(null);
        add(panelWelcomeTitle);
        
        lblTitleWelcome = new JLabel("Welcome");
        lblTitleWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitleWelcome.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lblTitleWelcome.setBounds(10, 0, 182, 87);
        panelWelcomeTitle.add(lblTitleWelcome);
        
        lblUsername = new JLabel("User");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lblUsername.setBounds(202, 0, 206, 87);
        lblUsername.setText(currentUser.getUsername());
        panelWelcomeTitle.add(lblUsername);
        
        lblDate = new JLabel("Date");
        lblDate.setVerticalAlignment(SwingConstants.TOP);
        lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblDate.setBounds(764, 11, 170, 54);
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panelWelcomeTitle.add(lblDate);
    }
    
    /**
     * Create the content panel with utility information
     */
    private void createContentPanel() {
        panelInformation = new JPanel();
        panelInformation.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelInformation.setBounds(21, 114, 467, 408);
        panelInformation.setLayout(null);
        add(panelInformation);
        
        createElectricityInfoPanel();
        createWaterInfoPanel();
        createGasInfoPanel();
        createOverallInfoPanel();
    }
    
    /**
     * Create the electricity information panel
     */
    private void createElectricityInfoPanel() {
        panelElectricityInfo = new JPanel();
        panelElectricityInfo.setBounds(10, 28, 447, 77);
        panelElectricityInfo.setLayout(null);
        panelInformation.add(panelElectricityInfo);
        
        lblTitleElectricityInfo = new JLabel("Electricity");
        lblTitleElectricityInfo.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTitleElectricityInfo.setBounds(10, 21, 156, 32);
        panelElectricityInfo.add(lblTitleElectricityInfo);
        
        lblElectricityReadingValue = new JLabel();
        lblElectricityReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblElectricityReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblElectricityReadingValue.setBounds(259, 21, 100, 32);
        panelElectricityInfo.add(lblElectricityReadingValue);
        
        lblElectricityReadingUnit = new JLabel("KwH");
        lblElectricityReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblElectricityReadingUnit.setBounds(369, 22, 68, 32);
        panelElectricityInfo.add(lblElectricityReadingUnit);
    }
    
    /**
     * Create the water information panel
     */
    private void createWaterInfoPanel() {
        panelWaterInfo = new JPanel();
        panelWaterInfo.setBounds(10, 116, 447, 77);
        panelWaterInfo.setLayout(null);
        panelInformation.add(panelWaterInfo);
        
        lblTitleWaterInfo = new JLabel("Water");
        lblTitleWaterInfo.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTitleWaterInfo.setBounds(10, 22, 156, 32);
        panelWaterInfo.add(lblTitleWaterInfo);
        
        lblWaterReadingValue = new JLabel();
        lblWaterReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblWaterReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWaterReadingValue.setBounds(261, 21, 100, 32);
        panelWaterInfo.add(lblWaterReadingValue);
        
        lblWaterReadingUnit = new JLabel("m³");
        lblWaterReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblWaterReadingUnit.setBounds(369, 22, 68, 32);
        panelWaterInfo.add(lblWaterReadingUnit);
    }
    
    /**
     * Create the gas information panel
     */
    private void createGasInfoPanel() {
        panelGasInfo = new JPanel();
        panelGasInfo.setBounds(10, 204, 447, 77);
        panelGasInfo.setLayout(null);
        panelInformation.add(panelGasInfo);
        
        lblTitleGasInfo = new JLabel("Gas");
        lblTitleGasInfo.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTitleGasInfo.setBounds(10, 23, 156, 32);
        panelGasInfo.add(lblTitleGasInfo);
        
        lblGasReadingValue = new JLabel();
        lblGasReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblGasReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblGasReadingValue.setBounds(259, 22, 100, 32);
        panelGasInfo.add(lblGasReadingValue);
        
        lblGasReadingUnit = new JLabel("Qty");
        lblGasReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblGasReadingUnit.setBounds(369, 23, 68, 32);
        panelGasInfo.add(lblGasReadingUnit);
    }
    
    /**
     * Create the overall information panel
     */
    private void createOverallInfoPanel() {
        panelOverallInfo = new JPanel();
        panelOverallInfo.setBounds(10, 292, 447, 77);
        panelOverallInfo.setLayout(null);
        panelInformation.add(panelOverallInfo);
        
        lblTitleOverAllInfo = new JLabel("Overall Expenses");
        lblTitleOverAllInfo.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTitleOverAllInfo.setBounds(10, 22, 260, 32);
        panelOverallInfo.add(lblTitleOverAllInfo);
        
        lblOverAllReadingValue = new JLabel();
        lblOverAllReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblOverAllReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblOverAllReadingValue.setBounds(258, 21, 101, 32);
        panelOverallInfo.add(lblOverAllReadingValue);
        
        lblOverAllReadingUnit = new JLabel("Php");
        lblOverAllReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblOverAllReadingUnit.setBounds(369, 22, 68, 32);
        panelOverallInfo.add(lblOverAllReadingUnit);
    }
    
    /**
     * Create the graph panel with CardLayout for different utilities
     */
    private void createGraphPanel() {
        // Graph Container with CardLayout
        panelGraphContainer = new JPanel();
        panelGraphContainer.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelGraphContainer.setBounds(504, 157, 413, 365);
        graphCardLayout = new CardLayout();
        panelGraphContainer.setLayout(graphCardLayout);
        add(panelGraphContainer);
        
        // Create graph panels with placeholders
        electricityGraphPanel = createPlaceholderGraphPanel("Monthly Electricity Usage");
        waterGraphPanel = createPlaceholderGraphPanel("Monthly Water Usage");
        gasGraphPanel = createPlaceholderGraphPanel("Monthly Gas Usage");
        overallGraphPanel = createPlaceholderGraphPanel("Monthly Total Expenses");
        
        // Add graph panels to container with card names
        panelGraphContainer.add(electricityGraphPanel, "electricity");
        panelGraphContainer.add(waterGraphPanel, "water");
        panelGraphContainer.add(gasGraphPanel, "gas");
        panelGraphContainer.add(overallGraphPanel, "overall");
        
        // Graph shadow panels for design effect
        JPanel panelBehind1 = new JPanel();
        panelBehind1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelBehind1.setBounds(520, 142, 413, 365);
        add(panelBehind1);
        
        JPanel panelBehind2 = new JPanel();
        panelBehind2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelBehind2.setBounds(536, 129, 413, 356);
        add(panelBehind2);
        
        JPanel panelBehind3 = new JPanel();
        panelBehind3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelBehind3.setBounds(552, 114, 413, 347);
        add(panelBehind3);
    }
    
    /**
     * Create the tips panel with money-saving advice
     */
    private void createTipsPanel() {
        panelTips = new JPanel();
        panelTips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelTips.setBounds(21, 543, 944, 134);
        panelTips.setLayout(null);
        add(panelTips);
        
        JLabel lblTitleTips = new JLabel("Money Saving Tips");
        lblTitleTips.setBounds(10, 0, 243, 36);
        lblTitleTips.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitleTips.setFont(new Font("Tahoma", Font.PLAIN, 25));
        panelTips.add(lblTitleTips);
        
        JLabel lblElectricityTips = new JLabel("Electricity Tip - Replace traditional light bulbs with LED bulbs. They use up to 75% less energy and last much longer.");
        lblElectricityTips.setHorizontalAlignment(SwingConstants.LEFT);
        lblElectricityTips.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblElectricityTips.setBounds(10, 43, 902, 29);
        panelTips.add(lblElectricityTips);
        
        JLabel lblWaterTips = new JLabel("Gas Tip - Lower your water heater temperature to 120°F to save energy while still providing comfortable hot water.");
        lblWaterTips.setHorizontalAlignment(SwingConstants.LEFT);
        lblWaterTips.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblWaterTips.setBounds(10, 104, 909, 21);
        panelTips.add(lblWaterTips);
        
        JLabel lblGasTips = new JLabel("Water Tip - Fix leaky faucets promptly. Even a small drip can waste several gallons of water per day.");
        lblGasTips.setHorizontalAlignment(SwingConstants.LEFT);
        lblGasTips.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblGasTips.setBounds(10, 77, 924, 21);
        panelTips.add(lblGasTips);
    }
    
    /**
     * Setup event listeners for interactive elements
     */
    private void createActionListeners() {
        // Electricity panel click event
        panelElectricityInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panelGraphContainer, "electricity");
            }
        });
        
        // Water panel click event
        panelWaterInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panelGraphContainer, "water");
            }
        });
        
        // Gas panel click event
        panelGasInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panelGraphContainer, "gas");
            }
        });
        
        // Overall panel click event
        panelOverallInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panelGraphContainer, "overall");
            }
        });
    }
    
    /**
     * Public method to refresh the panel data
     */
    public void homePanel_Refresh() {
        setupData();
    }
    
    /**
     * Loads and displays data from the database
     */
    private void setupData() {
        try {
            // Get latest readings for each utility type
            Reading electricityReading = databaseManager.getReadingManager().getLatestReadingByType(currentUser, "electricity");
            Reading waterReading = databaseManager.getReadingManager().getLatestReadingByType(currentUser, "water");
            Reading gasReading = databaseManager.getReadingManager().getLatestReadingByType(currentUser, "gas");
            
            // Update labels with latest readings
            updateReadingLabels(electricityReading, waterReading, gasReading);
            
            // Setup the bar graphs with data from past 6 months
            updateBarGraphs();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the reading value labels based on latest readings
     */
    private void updateReadingLabels(Reading electricityReading, Reading waterReading, Reading gasReading) {
        // Set electricity reading value
        if (electricityReading == null) {
            lblElectricityReadingValue.setText("No Data");
        } else {
            lblElectricityReadingValue.setText(String.valueOf(electricityReading.getReading()));
        }
        
        // Set water reading value
        if (waterReading == null) {
            lblWaterReadingValue.setText("No Data");
        } else {
            lblWaterReadingValue.setText(String.valueOf(waterReading.getReading()));
        }
        
        // Set gas reading value
        if (gasReading == null) {
            lblGasReadingValue.setText("No Data");
        } else {
            lblGasReadingValue.setText(String.valueOf(gasReading.getReading()));
        }
        
        // Calculate and set overall expenses
        if (electricityReading == null || waterReading == null || gasReading == null) {
            lblOverAllReadingValue.setText("No Data");
        } else {
            double totalPrice = electricityReading.getTotal_Price() + 
                              waterReading.getTotal_Price() + 
                              gasReading.getTotal_Price();
            lblOverAllReadingValue.setText(String.valueOf(totalPrice));
        }
    }
    
    /**
     * Updates all bar graphs with latest data
     * This is a placeholder implementation that could be filled in later
     */
    private void updateBarGraphs() {
        try {
            // Show electricity graph by default
            graphCardLayout.show(panelGraphContainer, "electricity");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a placeholder panel for graphs
     * 
     * @param title Title to display on the graph panel
     * @return A configured JPanel
     */
    private JPanel createPlaceholderGraphPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        
        // Add a title label
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(10, 11, 393, 25);
        panel.add(lblTitle);
        
        // Add placeholder text
        JLabel lblPlaceholder = new JLabel("Graph Placeholder");
        lblPlaceholder.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaceholder.setBounds(10, 150, 393, 25);
        panel.add(lblPlaceholder);
        
        // Add border for visual clarity
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        
        return panel;
    }
    
    /**
     * Groups readings by month and calculates either sum of readings or sum of total price
     * This is kept as a placeholder for future implementation
     * 
     * @param readings List of readings to group
     * @param usePrice If true, uses total_price field; if false, uses reading field
     * @return Map with Month as key and summed value as value
     */
    private Map<Month, Double> groupReadingsByMonth(List<Reading> readings, boolean usePrice) {
        Map<Month, Double> monthlyData = new HashMap<>();
        // Placeholder implementation - could be completed later
        return monthlyData;
    }
}