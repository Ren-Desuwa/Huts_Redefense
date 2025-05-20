package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.Reading;
import model.User;
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
    private Database_Manager databaseManager;
    private User currentUser;
    
    /** Main panel containers */
    private JPanel panelWelcomeTitle;
    private JPanel panelInformation;
    private JPanel panelTips;
    private JPanel panelGraphContainer;
    
    /** Utility info panels */
    private JPanel panelElectricityInfo;
    private JPanel panelWaterInfo;
    private JPanel panelGasInfo;
    private JPanel panelOverallInfo;
    
    /** Reading value labels */
    private JLabel lblElectricityReadingValue;
    private JLabel lblWaterReadingValue;
    private JLabel lblGasReadingValue;
    private JLabel lblOverAllReadingValue;
    
    /** Panel title labels */
    private JLabel lblTitleWelcome;
    private JLabel lblUsername;
    private JLabel lblDate;
    private JLabel lblTime;
    private JLabel lblTitleElectricityInfo;
    private JLabel lblTitleWaterInfo;
    private JLabel lblTitleGasInfo;
    private JLabel lblTitleOverAllInfo;
    
    /** Unit labels */
    private JLabel lblElectricityReadingUnit;
    private JLabel lblWaterReadingUnit;
    private JLabel lblGasReadingUnit;
    private JLabel lblOverAllReadingUnit;
    
    /** Graph components */
    private Graph_Panel graphPanel;
    private JPanel panelBehind1;
    private JPanel panelBehind2;
    private JPanel panelBehind3;
    private JLabel lblTrendOfReadingOverall;
    private JLabel lblTrendOfReadingGas;
    private JLabel lblTrendOfReadingWater;
    private JLabel lblTrendOfReadingElectricity;
    
    //==============================================================================================
    // CONSTRUCTOR
    //==============================================================================================
    
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
        initializeUI();
        setupData();
    }
    
    //==============================================================================================
    // INITIALIZATION METHODS
    //==============================================================================================
    
    /**
     * Initialize the panel's base properties
     */
    private void initializePanelProperties() {
        setPreferredSize(new Dimension(986, 688));
        setLayout(null);
    }
    
    /**
     * Initialize all UI components
     */
    private void initializeUI() {
        createHeaderPanel();
        createContentPanel();
        createGraphPanel();
        createTipsPanel();
        createActionListeners();
    }
    
    //==============================================================================================
    // UI CREATION - HEADER SECTION
    //==============================================================================================
    
    /**
     * Create the header panel with welcome title
     */
    private void createHeaderPanel() {
        panelWelcomeTitle = new Rounded_Panel(25, Color.BLACK, 1);
        panelWelcomeTitle.setBackground(new Color(255, 255, 255));
        panelWelcomeTitle.setBounds(21, 11, 944, 85);
        panelWelcomeTitle.setLayout(null);
        add(panelWelcomeTitle);
        
        lblTitleWelcome = new JLabel("Welcome");
        lblTitleWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitleWelcome.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lblTitleWelcome.setBounds(10, 0, 182, 60);
        panelWelcomeTitle.add(lblTitleWelcome);
        
        lblUsername = new JLabel("User");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lblUsername.setBounds(202, 0, 206, 60);
        lblUsername.setText(currentUser.getUsername());
        panelWelcomeTitle.add(lblUsername);
        
        lblDate = new JLabel("Date");
        lblDate.setVerticalAlignment(SwingConstants.TOP);
        lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblDate.setBounds(764, 11, 170, 54);
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panelWelcomeTitle.add(lblDate);
        
        lblTime = new JLabel("Time");
        lblTime.setVerticalAlignment(SwingConstants.TOP);
        lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTime.setBounds(764, 46, 170, 41);
        lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm")));
        panelWelcomeTitle.add(lblTime);
        
        JLabel lbl_SubTitle_Welcome = new JLabel("Here is Your Summary of Expenses\r\n\r\n");
        lbl_SubTitle_Welcome.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_SubTitle_Welcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbl_SubTitle_Welcome.setBounds(20, 58, 393, 22);
        panelWelcomeTitle.add(lbl_SubTitle_Welcome);
    }
    
    //==============================================================================================
    // UI CREATION - CONTENT PANELS
    //==============================================================================================
    
    /**
     * Create the content panel with utility information
     */
    private void createContentPanel() {
        panelInformation = new Rounded_Panel();
        panelInformation.setBackground(new Color(255, 255, 255));
//        panelInformation.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
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
        panelElectricityInfo = new Rounded_Panel();
        panelElectricityInfo.setBackground(new Color(220, 220, 220));
        panelElectricityInfo.setBounds(10, 11, 447, 87);
        panelElectricityInfo.setLayout(null);
        panelInformation.add(panelElectricityInfo);
        
        lblTitleElectricityInfo = new JLabel("Electricity");
        lblTitleElectricityInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblTitleElectricityInfo.setBounds(10, 15, 156, 32);
        panelElectricityInfo.add(lblTitleElectricityInfo);
        
        lblElectricityReadingValue = new JLabel();
        lblElectricityReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblElectricityReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblElectricityReadingValue.setBounds(259, 15, 100, 32);
        panelElectricityInfo.add(lblElectricityReadingValue);
        
        lblElectricityReadingUnit = new JLabel("KwH");
        lblElectricityReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblElectricityReadingUnit.setBounds(369, 15, 68, 32);
        panelElectricityInfo.add(lblElectricityReadingUnit);
        
        lblTrendOfReadingElectricity = new JLabel("No avilable data");
        lblTrendOfReadingElectricity.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrendOfReadingElectricity.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblTrendOfReadingElectricity.setBounds(176, 44, 261, 32);
        panelElectricityInfo.add(lblTrendOfReadingElectricity);
    }
    
    /**
     * Create the water information panel
     */
    private void createWaterInfoPanel() {
        panelWaterInfo = new Rounded_Panel();
        panelWaterInfo.setBackground(new Color(220, 220, 220));
        panelWaterInfo.setBounds(10, 109, 447, 87);
        panelWaterInfo.setLayout(null);
        panelInformation.add(panelWaterInfo);
        
        lblTitleWaterInfo = new JLabel("Water");
        lblTitleWaterInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblTitleWaterInfo.setBounds(10, 15, 156, 32);
        panelWaterInfo.add(lblTitleWaterInfo);
        
        lblWaterReadingValue = new JLabel();
        lblWaterReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblWaterReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWaterReadingValue.setBounds(261, 15, 100, 32);
        panelWaterInfo.add(lblWaterReadingValue);
        
        lblWaterReadingUnit = new JLabel("m³");
        lblWaterReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblWaterReadingUnit.setBounds(369, 15, 68, 32);
        panelWaterInfo.add(lblWaterReadingUnit);
        
        lblTrendOfReadingWater = new JLabel("No avilable data");
        lblTrendOfReadingWater.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrendOfReadingWater.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblTrendOfReadingWater.setBounds(176, 44, 261, 32);
        panelWaterInfo.add(lblTrendOfReadingWater);
    }
    
    /**
     * Create the gas information panel
     */
    private void createGasInfoPanel() {
        panelGasInfo = new Rounded_Panel();
        panelGasInfo.setBackground(new Color(220, 220, 220));
        panelGasInfo.setBounds(10, 207, 447, 87);
        panelGasInfo.setLayout(null);
        panelInformation.add(panelGasInfo);
        
        lblTitleGasInfo = new JLabel("Gas");
        lblTitleGasInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblTitleGasInfo.setBounds(10, 15, 156, 32);
        panelGasInfo.add(lblTitleGasInfo);
        
        lblGasReadingValue = new JLabel();
        lblGasReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblGasReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblGasReadingValue.setBounds(259, 15, 100, 32);
        panelGasInfo.add(lblGasReadingValue);
        
        lblGasReadingUnit = new JLabel("Qty");
        lblGasReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblGasReadingUnit.setBounds(369, 15, 68, 32);
        panelGasInfo.add(lblGasReadingUnit);
        
        lblTrendOfReadingGas = new JLabel("No avilable data");
        lblTrendOfReadingGas.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrendOfReadingGas.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblTrendOfReadingGas.setBounds(176, 44, 261, 32);
        panelGasInfo.add(lblTrendOfReadingGas);
    }
    
    /**
     * Create the overall information panel
     */
    private void createOverallInfoPanel() {
        panelOverallInfo = new Rounded_Panel();
        panelOverallInfo.setBackground(new Color(220, 220, 220));
        panelOverallInfo.setBounds(10, 305, 447, 87);
        panelOverallInfo.setLayout(null);
        panelInformation.add(panelOverallInfo);
        
        lblTitleOverAllInfo = new JLabel("Overall Expenses");
        lblTitleOverAllInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblTitleOverAllInfo.setBounds(10, 15, 260, 32);
        panelOverallInfo.add(lblTitleOverAllInfo);
        
        lblOverAllReadingValue = new JLabel();
        lblOverAllReadingValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblOverAllReadingValue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblOverAllReadingValue.setBounds(258, 15, 101, 32);
        panelOverallInfo.add(lblOverAllReadingValue);
        
        lblOverAllReadingUnit = new JLabel("Php");
        lblOverAllReadingUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblOverAllReadingUnit.setBounds(369, 15, 68, 32);
        panelOverallInfo.add(lblOverAllReadingUnit);
        
        lblTrendOfReadingOverall = new JLabel("No avilable data");
        lblTrendOfReadingOverall.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrendOfReadingOverall.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblTrendOfReadingOverall.setBounds(177, 44, 260, 32);
        panelOverallInfo.add(lblTrendOfReadingOverall);
    }
    
    //==============================================================================================
    // UI CREATION - GRAPH SECTION
    //==============================================================================================
    
    /**
     * Create the graph panel with CardLayout for different utilities
     */
    private void createGraphPanel() {
        // Create the main graph container (visible to WindowBuilder)
        panelGraphContainer = new Rounded_Panel();
//        panelGraphContainer.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelGraphContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelGraphContainer.setBounds(504, 157, 413, 365);
        panelGraphContainer.setBackground(new Color(255, 255, 255));
        panelGraphContainer.setLayout(new BorderLayout()); // Use BorderLayout for easy replacement
        add(panelGraphContainer);
        
        // Create Graph_Panel instance
        Rounded_Panel rounded_Panel = new Rounded_Panel();
        rounded_Panel.setBackground(new Color(255, 255, 255));
        graphPanel = new Graph_Panel(rounded_Panel);
        
        // Add Graph_Panel to the container
        panelGraphContainer.add(graphPanel, BorderLayout.CENTER);
        
        createGraphShadowPanels();
    }
    
    /**
     * Creates shadow panels for visual design effect behind the graph
     */
    private void createGraphShadowPanels() {
        panelBehind1 = new Rounded_Panel();
        panelBehind1.setBackground(new Color(255, 255, 255));
//        panelBehind1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelBehind1.setBounds(520, 142, 413, 365);
        add(panelBehind1);
        
        panelBehind2 = new Rounded_Panel();
        panelBehind2.setBackground(new Color(255, 255, 255));
//        panelBehind2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelBehind2.setBounds(536, 129, 413, 356);
        add(panelBehind2);
        
        panelBehind3 = new Rounded_Panel();
        panelBehind3.setBackground(new Color(255, 255, 255));
//        panelBehind3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelBehind3.setBounds(552, 114, 413, 347);
        add(panelBehind3);
    }
    
    //==============================================================================================
    // UI CREATION - TIPS SECTION
    //==============================================================================================
    
    /**
     * Create the tips panel with money-saving advice
     */
    private void createTipsPanel() {
        panelTips = new  Rounded_Panel();
        panelTips.setBackground(new Color(255, 255, 255));
//        panelTips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
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
    
    //==============================================================================================
    // INTERACTION HANDLING
    //==============================================================================================
    
    /**
     * Setup event listeners for interactive elements
     */
    private void createActionListeners() {
        Timer timer = new Timer(60_000, e -> {
            lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        });
        LocalTime now = LocalTime.now();
        int millisUntilNextMinute = (60 - now.getSecond()) * 1000 - now.getNano() / 1_000_000;
        timer.setInitialDelay(millisUntilNextMinute); // optional: sync with real time
        timer.start();
        
        // Electricity panel click event
        panelElectricityInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphPanel.showElectricityGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
				panelElectricityInfo.setBackground(new Color(200, 200, 200));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panelElectricityInfo.setBackground(new Color(220, 220, 220));
			}
        });
        
        // Water panel click event
        panelWaterInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphPanel.showWaterGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            	panelWaterInfo.setBackground(new Color(200, 200, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
				panelWaterInfo.setBackground(new Color(220, 220, 220));
            }
        });
        
        // Gas panel click event
        panelGasInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphPanel.showGasGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
				panelGasInfo.setBackground(new Color(200, 200, 200));
			}
            @Override
            public void mouseExited(MouseEvent e) {
            	panelGasInfo.setBackground(new Color(220, 220, 220));
            }
            
        });
        
        // Overall panel click event
        panelOverallInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphPanel.showOverallGraph();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            	panelOverallInfo.setBackground(new Color(200, 200, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
				panelOverallInfo.setBackground(new Color(220, 220, 220));
			}
        });
    }
    
    //==============================================================================================
    // DATA HANDLING
    //==============================================================================================
    
    /**
     * Public method to refresh the panel data
     */
    public void Home_Panel_Refresh() {
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
            
            // Initialize graph panels (needed because we're using a delayed initialization approach)
            graphPanel.initialize();
            
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
            
            try {
            	lblTrendOfReadingElectricity.setText(databaseManager.getReadingManager().getTrend(currentUser, "electricity"));
                lblTrendOfReadingElectricity.setForeground(databaseManager.getReadingManager().getTrendColor(currentUser, "electricity"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
        // Set water reading value
        if (waterReading == null) {
            lblWaterReadingValue.setText("No Data");
        } else {
            lblWaterReadingValue.setText(String.valueOf(waterReading.getReading()));
            
            try {
            	lblTrendOfReadingWater.setText(databaseManager.getReadingManager().getTrend(currentUser, "water"));
                lblTrendOfReadingWater.setForeground(databaseManager.getReadingManager().getTrendColor(currentUser, "water"));
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
        
        // Set gas reading value
        if (gasReading == null) {
            lblGasReadingValue.setText("No Data");
        } else {
            lblGasReadingValue.setText(String.valueOf(gasReading.getReading()));
            
            try {
            	lblTrendOfReadingGas.setText(databaseManager.getReadingManager().getTrend(currentUser, "gas"));
                lblTrendOfReadingGas.setForeground(databaseManager.getReadingManager().getTrendColor(currentUser, "gas"));
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
        
     // Calculate and set overall expenses
        double totalPrice = 0.0;
        boolean hasAnyReading = false;

        // Add electricity price if available
        if (electricityReading != null) {
            totalPrice += electricityReading.getTotal_Price();
            hasAnyReading = true;
        }

        // Add water price if available
        if (waterReading != null) {
            totalPrice += waterReading.getTotal_Price();
            hasAnyReading = true;
        }

        // Add gas price if available
        if (gasReading != null) {
            totalPrice += gasReading.getTotal_Price();
            hasAnyReading = true;
        }

        if (!hasAnyReading) {
            lblOverAllReadingValue.setText("No Data");
        } else {
            lblOverAllReadingValue.setText(String.valueOf(totalPrice));
            
            // Get previous month's total expenses
            LocalDate currentDate = LocalDate.now();
            LocalDate previousMonth = currentDate.minusMonths(1);
            
            double previousMonthTotal = 0.0;
            boolean hasPreviousReading = false;
            
            Reading previousElectricity = databaseManager.getReadingManager().getReadingByMonth(currentUser, "electricity", previousMonth);
            Reading previousWater = databaseManager.getReadingManager().getReadingByMonth(currentUser, "water", previousMonth);
            Reading previousGas = databaseManager.getReadingManager().getReadingByMonth(currentUser, "gas", previousMonth);
            
            // Add previous electricity price if available
            if (previousElectricity != null && electricityReading != null) {
                previousMonthTotal += previousElectricity.getTotal_Price();
                hasPreviousReading = true;
            }
            
            // Add previous water price if available
            if (previousWater != null && waterReading != null) {
                previousMonthTotal += previousWater.getTotal_Price();
                hasPreviousReading = true;
            }
            
            // Add previous gas price if available
            if (previousGas != null && gasReading != null) {
                previousMonthTotal += previousGas.getTotal_Price();
                hasPreviousReading = true;
            }
            
            if (hasPreviousReading && previousMonthTotal > 0) {
                double percentageChange = ((totalPrice - previousMonthTotal) / previousMonthTotal) * 100;
                String trendText = String.format("%.1f%% from last month", percentageChange);
                lblTrendOfReadingOverall.setText(trendText);
                
                // Set color based on trend (red for increase, green for decrease)
                Color trendColor = percentageChange > 0 ? new Color(255, 0, 0) : new Color(0, 150, 0);
                lblTrendOfReadingOverall.setForeground(trendColor);
            } else {
                lblTrendOfReadingOverall.setText("No previous data");
            }
        }


    }
    
    /**
     * Updates all bar graphs with latest data
     * This is a placeholder implementation that could be filled in later
     */
    private void updateBarGraphs() {
        try {
            // Prepare monthly data maps
            Map<Month, Double> electricityData = new HashMap<>();
            Map<Month, Double> waterData = new HashMap<>();
            Map<Month, Double> gasData = new HashMap<>();
            Map<Month, Double> overallData = new HashMap<>();
            
            // Fetch last 6 months of readings
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusMonths(6);
            
            List<Reading> electricityReadings = databaseManager.getReadingManager()
                .getReadingsByDateAndType(currentUser, startDate, endDate, "electricity");
            List<Reading> waterReadings = databaseManager.getReadingManager()
                .getReadingsByDateAndType(currentUser, startDate, endDate, "water");
            List<Reading> gasReadings = databaseManager.getReadingManager()
                .getReadingsByDateAndType(currentUser, startDate, endDate, "gas");
            
            // Group readings by month
            electricityData = groupReadingsByMonth(electricityReadings, false);
            waterData = groupReadingsByMonth(waterReadings, false);
            gasData = groupReadingsByMonth(gasReadings, false);
            
            // Group price data by month for overall expenses
            Map<Month, Double> electricityPrices = groupReadingsByMonth(electricityReadings, true);
            Map<Month, Double> waterPrices = groupReadingsByMonth(waterReadings, true);
            Map<Month, Double> gasPrices = groupReadingsByMonth(gasReadings, true);
            
            // Combine all price data for total expenses
            for (Month month : electricityPrices.keySet()) {
                double totalPrice = electricityPrices.getOrDefault(month, 0.0) +
                                    waterPrices.getOrDefault(month, 0.0) +
                                    gasPrices.getOrDefault(month, 0.0);
                overallData.put(month, totalPrice);
            }
            
            // Update graphs with data
            graphPanel.updateGraphs(electricityData, waterData, gasData, overallData, 5);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //==============================================================================================
    // UTILITY METHODS
    //==============================================================================================
    
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
        
        if (readings != null) {
            for (Reading reading : readings) {
                LocalDate readingDate = reading.getDate();
                Month month = readingDate.getMonth();
                
                double value = usePrice ? reading.getTotal_Price() : reading.getReading();
                
                // Add value to existing month or create new entry
                monthlyData.put(month, monthlyData.getOrDefault(month, 0.0) + value);
            }
        }
        
        return monthlyData;
    }
}