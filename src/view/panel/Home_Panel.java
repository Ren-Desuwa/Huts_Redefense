package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.Reading;
import model.User;
import visuals.Bar_Graph_Panel;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.CardLayout;

public class Home_Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Database_Manager database_manager;
    private User current_user;
    
    private JLabel lbl_Electricity_Reading_Value;
    private JLabel lbl_Water_Reading_Value;
    private JLabel lbl_Gas_Reading_Value;
    private JLabel lbl_OverAll_Reading_Value;
    private JPanel panel_Welcome_Title;
    private JPanel panel_Information;
    private JPanel panel_Electricity_Info;
    private JLabel lbl_Title_Electricity_Info;
    private JPanel panel_Gas_Info;
    private JPanel panel_Overall_Info;
    private JPanel panel_tips;
    private JLabel lblWater;
    private JLabel lbl_Gas;
    private JLabel lblNewLabel_1;
    
    // Graph panels
    private JPanel panel_Graph_Container;
    private CardLayout graphCardLayout;
    private Bar_Graph_Panel electricityGraphPanel;
    private Bar_Graph_Panel waterGraphPanel;
    private Bar_Graph_Panel gasGraphPanel;
    private Bar_Graph_Panel overallGraphPanel;
    
    public Home_Panel(Database_Manager database_manager, User current_user) {
        this.database_manager = database_manager;
        this.current_user = current_user;
        
        setPreferredSize(new Dimension(986, 688));
        setLayout(null);
        
        initializeComponents();
        setupEventListeners();
        setupData();
    }
    
    /**
     * Initialize all UI components for the Home Panel
     */
    private void initializeComponents() {
        // Welcome Title Panel
        panel_Welcome_Title = new JPanel();
        panel_Welcome_Title.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Welcome_Title.setBounds(21, 11, 944, 85);
        add(panel_Welcome_Title);
        panel_Welcome_Title.setLayout(null);
        
        JLabel lbl_Title_Welcome = new JLabel("Welcome");
        lbl_Title_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_Welcome.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lbl_Title_Welcome.setBounds(10, 0, 182, 87);
        panel_Welcome_Title.add(lbl_Title_Welcome);
        
        JLabel lbl_Username = new JLabel("User");
        lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lbl_Username.setBounds(202, 0, 206, 87);
        lbl_Username.setText(current_user.getUsername());
        panel_Welcome_Title.add(lbl_Username);
        
        JLabel lbl_Date = new JLabel("Date");
        lbl_Date.setVerticalAlignment(SwingConstants.TOP);
        lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl_Date.setBounds(764, 11, 170, 54);
        lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panel_Welcome_Title.add(lbl_Date);
        
        // Graph Container with CardLayout
        panel_Graph_Container = new JPanel();
        panel_Graph_Container.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Graph_Container.setBounds(504, 157, 413, 365);
        add(panel_Graph_Container);
        graphCardLayout = new CardLayout();
        panel_Graph_Container.setLayout(graphCardLayout);
        
        // Create graph panels
        electricityGraphPanel = new Bar_Graph_Panel("Monthly Electricity Usage", "Month", "kWh");
        waterGraphPanel = new Bar_Graph_Panel("Monthly Water Usage", "Month", "m³");
        gasGraphPanel = new Bar_Graph_Panel("Monthly Gas Usage", "Month", "Qty");
        overallGraphPanel = new Bar_Graph_Panel("Monthly Total Expenses", "Month", "PHP");
        
        // Add graph panels to container with card names
        panel_Graph_Container.add(electricityGraphPanel, "electricity");
        panel_Graph_Container.add(waterGraphPanel, "water");
        panel_Graph_Container.add(gasGraphPanel, "gas");
        panel_Graph_Container.add(overallGraphPanel, "overall");
        
        // Graph shadow panels for design effect
        JPanel panel_behind1 = new JPanel();
        panel_behind1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_behind1.setBounds(520, 142, 413, 365);
        add(panel_behind1);
        
        JPanel panel_behind2 = new JPanel();
        panel_behind2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_behind2.setBounds(536, 129, 413, 356);
        add(panel_behind2);
        
        JPanel panel_behind3 = new JPanel();
        panel_behind3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_behind3.setBounds(552, 114, 413, 347);
        add(panel_behind3);
        
        // Information Panel
        panel_Information = new JPanel();
        panel_Information.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Information.setBounds(21, 114, 467, 408);
        add(panel_Information);
        panel_Information.setLayout(null);
        
        // Electricity Info Panel
        panel_Electricity_Info = new JPanel();
        panel_Electricity_Info.setBounds(10, 28, 447, 77);
        panel_Information.add(panel_Electricity_Info);
        panel_Electricity_Info.setLayout(null);
        
        lbl_Title_Electricity_Info = new JLabel("Electricity");
        lbl_Title_Electricity_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl_Title_Electricity_Info.setBounds(10, 21, 156, 32);
        panel_Electricity_Info.add(lbl_Title_Electricity_Info);
        
        lbl_Electricity_Reading_Value = new JLabel();
        lbl_Electricity_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Electricity_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Electricity_Reading_Value.setBounds(259, 21, 100, 32);
        panel_Electricity_Info.add(lbl_Electricity_Reading_Value);
        
        JLabel lbl_Electricity_Reading_Unit = new JLabel("KwH");
        lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Electricity_Reading_Unit.setBounds(369, 22, 68, 32);
        panel_Electricity_Info.add(lbl_Electricity_Reading_Unit);
        
        // Water Info Panel
        JPanel panel_Water_Info = new JPanel();
        panel_Water_Info.setBounds(10, 116, 447, 77);
        panel_Information.add(panel_Water_Info);
        panel_Water_Info.setLayout(null);
        
        JLabel lbl_Title_Water_Info = new JLabel("Water");
        lbl_Title_Water_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl_Title_Water_Info.setBounds(10, 22, 156, 32);
        panel_Water_Info.add(lbl_Title_Water_Info);
        
        lbl_Water_Reading_Value = new JLabel();
        lbl_Water_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Water_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Water_Reading_Value.setBounds(261, 21, 100, 32);
        panel_Water_Info.add(lbl_Water_Reading_Value);
        
        JLabel lbl_Water_Reading_Unit = new JLabel("m³");
        lbl_Water_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Water_Reading_Unit.setBounds(369, 22, 68, 32);
        panel_Water_Info.add(lbl_Water_Reading_Unit);
        
        // Gas Info Panel
        panel_Gas_Info = new JPanel();
        panel_Gas_Info.setBounds(10, 204, 447, 77);
        panel_Information.add(panel_Gas_Info);
        panel_Gas_Info.setLayout(null);
        
        JLabel lbl_Title_Gas_Info = new JLabel("Gas");
        lbl_Title_Gas_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl_Title_Gas_Info.setBounds(10, 23, 156, 32);
        panel_Gas_Info.add(lbl_Title_Gas_Info);
        
        lbl_Gas_Reading_Value = new JLabel();
        lbl_Gas_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Gas_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_Gas_Reading_Value.setBounds(259, 22, 100, 32);
        panel_Gas_Info.add(lbl_Gas_Reading_Value);
        
        JLabel lbl_Gas_Reading_Unit = new JLabel("Qty");
        lbl_Gas_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_Gas_Reading_Unit.setBounds(369, 23, 68, 32);
        panel_Gas_Info.add(lbl_Gas_Reading_Unit);
        
        // Overall Info Panel
        panel_Overall_Info = new JPanel();
        panel_Overall_Info.setBounds(10, 292, 447, 77);
        panel_Information.add(panel_Overall_Info);
        panel_Overall_Info.setLayout(null);
        
        JLabel lbl_Title_OverAll_Info = new JLabel("Overall Expenses");
        lbl_Title_OverAll_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl_Title_OverAll_Info.setBounds(10, 22, 260, 32);
        panel_Overall_Info.add(lbl_Title_OverAll_Info);
        
        lbl_OverAll_Reading_Value = new JLabel();
        lbl_OverAll_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_OverAll_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
        lbl_OverAll_Reading_Value.setBounds(258, 21, 101, 32);
        panel_Overall_Info.add(lbl_OverAll_Reading_Value);
        
        JLabel lbl_OverAll_Reading_Unit = new JLabel("Php");
        lbl_OverAll_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbl_OverAll_Reading_Unit.setBounds(369, 22, 68, 32);
        panel_Overall_Info.add(lbl_OverAll_Reading_Unit);
        
        // Tips Panel
        panel_tips = new JPanel();
        panel_tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_tips.setBounds(21, 543, 944, 134);
        add(panel_tips);
        panel_tips.setLayout(null);
        
        JLabel lbl_Title_Tips = new JLabel("Money Saving Tips");
        lbl_Title_Tips.setBounds(10, 0, 243, 36);
        lbl_Title_Tips.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
        panel_tips.add(lbl_Title_Tips);
        
        JLabel lbl_Electricity_Tips = new JLabel("Electricity Tip - Replace traditional light bulbs with LED bulbs. They use up to 75% less energy and last much longer.");
        lbl_Electricity_Tips.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Electricity_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbl_Electricity_Tips.setBounds(10, 43, 902, 29);
        panel_tips.add(lbl_Electricity_Tips);
        
        JLabel lbl_Water_Tips = new JLabel("Gas Tip - Lower your water heater temperature to 120°F to save energy while still providing comfortable hot water.");
        lbl_Water_Tips.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_Water_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbl_Water_Tips.setBounds(10, 104, 909, 21);
        panel_tips.add(lbl_Water_Tips);
        
        JLabel lblGas_Tips = new JLabel("Water Tip - Fix leaky faucets promptly. Even a small drip can waste several gallons of water per day.");
        lblGas_Tips.setHorizontalAlignment(SwingConstants.LEFT);
        lblGas_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblGas_Tips.setBounds(10, 77, 924, 21);
        panel_tips.add(lblGas_Tips);
    }
    
    /**
     * Setup event listeners for interactive elements
     */
    private void setupEventListeners() {
        // Electricity panel click event
        panel_Electricity_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panel_Graph_Container, "electricity");
            }
        });
        
        // Add mouse listener to Water Info panel
        JPanel panel_Water_Info = (JPanel)panel_Information.getComponent(1);
        panel_Water_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panel_Graph_Container, "water");
            }
        });
        
        // Gas panel click event
        panel_Gas_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panel_Graph_Container, "gas");
            }
        });
        
        // Overall panel click event
        panel_Overall_Info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graphCardLayout.show(panel_Graph_Container, "overall");
            }
        });
    }
    
    /**
     * Public method to refresh the panel data
     */
    public void Home_Panel_Refresh() {
        setupData();
    }
    
    /**
     * Loads and displays data from the database
     */
    public void setupData() {
        try {
            // Get latest readings for each utility type
            Reading electricity_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "electricity");
            Reading water_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "water");
            Reading gas_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "gas");
            
            // Update labels with latest readings
            updateReadingLabels(electricity_reading, water_reading, gas_reading);
            
            // Setup the bar graphs with data from past 6 months
            updateBarGraphs();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the reading value labels based on latest readings
     */
    private void updateReadingLabels(Reading electricity_reading, Reading water_reading, Reading gas_reading) {
        // Set electricity reading value
        if (electricity_reading == null) {
            lbl_Electricity_Reading_Value.setText("No Data");
        } else {
            lbl_Electricity_Reading_Value.setText(String.valueOf(electricity_reading.getReading()));
        }
        
        // Set water reading value
        if (water_reading == null) {
            lbl_Water_Reading_Value.setText("No Data");
        } else {
            lbl_Water_Reading_Value.setText(String.valueOf(water_reading.getReading()));
        }
        
        // Set gas reading value
        if (gas_reading == null) {
            lbl_Gas_Reading_Value.setText("No Data");
        } else {
            lbl_Gas_Reading_Value.setText(String.valueOf(gas_reading.getReading()));
        }
        
        // Calculate and set overall expenses
        if (electricity_reading == null || water_reading == null || gas_reading == null) {
            lbl_OverAll_Reading_Value.setText("No Data");
        } else {
            double total_price = electricity_reading.getTotal_Price() + 
                                water_reading.getTotal_Price() + 
                                gas_reading.getTotal_Price();
            lbl_OverAll_Reading_Value.setText(String.valueOf(total_price));
        }
    }
    
    /**
     * Updates all bar graphs with latest data
     */
    private void updateBarGraphs() {
        try {
            // Get the current date and calculate date range (last 6 months)
            LocalDate currentDate = LocalDate.now();
            LocalDate startDate = currentDate.minusMonths(6);
            
            // Get all readings within the date range
            List<Reading> readings = database_manager.getReadingManager()
                    .getReadingsByTime(current_user, startDate, currentDate);
            
            // Update individual utility graphs
            updateUtilityGraph(readings, "electricity");
            updateUtilityGraph(readings, "water");
            updateUtilityGraph(readings, "gas");
            updateOverallGraph(readings);
            
            // Show electricity graph by default
            graphCardLayout.show(panel_Graph_Container, "electricity");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates a specific utility graph with filtered data
     * 
     * @param readings Complete list of readings
     * @param utilityType Type of utility ("electricity", "water", or "gas")
     */
    private void updateUtilityGraph(List<Reading> readings, String utilityType) {
        // Filter by utility type
        List<Reading> filteredReadings = readings.stream()
                .filter(reading -> reading.getType().equals(utilityType))
                .collect(Collectors.toList());
        
        // Group readings by month
        Map<Month, Double> monthlyReadings = groupReadingsByMonth(filteredReadings, false);
        
        // Update appropriate graph
        switch (utilityType) {
            case "electricity":
                electricityGraphPanel.setMonthlyData("Usage", monthlyReadings);
                break;
            case "water":
                waterGraphPanel.setMonthlyData("Usage", monthlyReadings);
                break;
            case "gas":
                gasGraphPanel.setMonthlyData("Usage", monthlyReadings);
                break;
        }
    }
    
    /**
     * Updates the overall expenses graph
     * 
     * @param readings Complete list of readings
     */
    private void updateOverallGraph(List<Reading> readings) {
        // Group readings by month using total price
        Map<Month, Double> monthlyExpenses = groupReadingsByMonth(readings, true);
        
        // Update overall graph
        overallGraphPanel.setMonthlyData("Expenses", monthlyExpenses);
    }
    
    /**
     * Groups readings by month and calculates either sum of readings or sum of total price
     * 
     * @param readings List of readings to group
     * @param usePrice If true, uses total_price field; if false, uses reading field
     * @return Map with Month as key and summed value as value
     */
    private Map<Month, Double> groupReadingsByMonth(List<Reading> readings, boolean usePrice) {
        Map<Month, Double> monthlyData = new HashMap<>();
        
        // Process each reading
        for (Reading reading : readings) {
            Month month = reading.getDate().getMonth();
            double value = usePrice ? reading.getTotal_Price() : reading.getReading();
            
            // Add to map, summing values for the same month
            monthlyData.put(month, monthlyData.getOrDefault(month, 0.0) + value);
        }
        
        return monthlyData;
    }
}