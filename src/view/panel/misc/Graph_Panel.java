package view.panel.misc;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.time.Month;
import java.util.Map;
import java.awt.Color;

import visuals.Bar_Graph_Panel;

/**
 * Panel for displaying utility usage graphs
 * Designed to be WindowBuilder-friendly
 */
@SuppressWarnings("serial")
public class Graph_Panel extends JPanel {
    
    // CardLayout for switching between different utility graphs
    private CardLayout graphCardLayout;
    private JPanel cardContainer;
    
    // Graph panel references
    private Bar_Graph_Panel electricityGraphPanel;
    private Bar_Graph_Panel waterGraphPanel;
    private Bar_Graph_Panel gasGraphPanel;
    private Bar_Graph_Panel overallGraphPanel;
    
    /**
     * Creates a new graph panel with placeholder
     * WindowBuilder-friendly constructor
     */
    public Graph_Panel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        
        // Create simple placeholder that WindowBuilder can understand
        JPanel placeholder = new JPanel();
        placeholder.setLayout(new BorderLayout());
        add(placeholder, BorderLayout.CENTER);
    }
    
    /**
     * Initializes the graph panels
     * Called after WindowBuilder has done its work
     */
    public void initialize() {
        // Clear any existing components
        removeAll();
        
        // Create card container with CardLayout
        graphCardLayout = new CardLayout();
        cardContainer = new JPanel(graphCardLayout);
        
        // Initialize the individual graph panels
        electricityGraphPanel = new Bar_Graph_Panel("Monthly Electricity Usage", "Month", "KwH");
        waterGraphPanel = new Bar_Graph_Panel("Monthly Water Usage", "Month", "m³");
        gasGraphPanel = new Bar_Graph_Panel("Monthly Gas Usage", "Month", "Qty");
        overallGraphPanel = new Bar_Graph_Panel("Monthly Total Expenses", "Month", "Php");
        
        // Add graph panels to the card container
        cardContainer.add(electricityGraphPanel, "electricity");
        cardContainer.add(waterGraphPanel, "water");
        cardContainer.add(gasGraphPanel, "gas");
        cardContainer.add(overallGraphPanel, "overall");
        
        // Add card container to this panel
        add(cardContainer, BorderLayout.CENTER);
        
        // Show electricity graph by default
        showElectricityGraph();
        
        // Ensure layout updates
        revalidate();
        repaint();
    }
    
    /**
     * Updates all graphs with the provided reading data
     * 
     * @param electricityData Map of monthly electricity readings
     * @param waterData Map of monthly water readings
     * @param gasData Map of monthly gas readings
     * @param overallData Map of monthly expense totals
     * @param monthsToShow Number of months to display
     */
    public void updateGraphs(
            Map<Month, Double> electricityData, 
            Map<Month, Double> waterData,
            Map<Month, Double> gasData,
            Map<Month, Double> overallData,
            int monthsToShow) {
        
        // Check if initialization has been done
        if (electricityGraphPanel == null) {
            initialize();
        }
        
        electricityGraphPanel.setMonthlyData("Electricity", electricityData, monthsToShow);
        waterGraphPanel.setMonthlyData("Water", waterData, monthsToShow);
        gasGraphPanel.setMonthlyData("Gas", gasData, monthsToShow);
        overallGraphPanel.setMonthlyData("Total Cost", overallData, monthsToShow);
    }
    
    /**
     * Shows the electricity graph
     */
    public void showElectricityGraph() {
        if (graphCardLayout != null && cardContainer != null) {
            graphCardLayout.show(cardContainer, "electricity");
        }
    }
    
    /**
     * Shows the water graph
     */
    public void showWaterGraph() {
        if (graphCardLayout != null && cardContainer != null) {
            graphCardLayout.show(cardContainer, "water");
        }
    }
    
    /**
     * Shows the gas graph
     */
    public void showGasGraph() {
        if (graphCardLayout != null && cardContainer != null) {
            graphCardLayout.show(cardContainer, "gas");
        }
    }
    
    /**
     * Shows the overall expenses graph
     */
    public void showOverallGraph() {
        if (graphCardLayout != null && cardContainer != null) {
            graphCardLayout.show(cardContainer, "overall");
        }
    }
}