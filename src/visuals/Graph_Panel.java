package visuals;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.time.Month;
import java.util.Map;
import java.awt.Color;

/**
 * Panel for displaying utility usage graphs with horizontal scrolling
 * Designed to be WindowBuilder-friendly
 */
@SuppressWarnings("serial")
public class Graph_Panel extends JPanel {
    
    // CardLayout for switching between different utility graphs
    private CardLayout graphCardLayout;
    private JPanel cardContainer;
    
    // Graph panel references
    private Scrollable_Bar_Graph_Panel electricityGraphPanel;
    private Scrollable_Bar_Graph_Panel waterGraphPanel;
    private Scrollable_Bar_Graph_Panel gasGraphPanel;
    private Scrollable_Bar_Graph_Panel overallGraphPanel;
    
    /**
     * Creates a new graph panel with placeholder
     * WindowBuilder-friendly constructor
     */
    public Graph_Panel(JPanel placeholderPanel) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        
        // Create simple placeholder that WindowBuilder can understand
        JPanel placeholder = placeholderPanel;
        placeholder.setLayout(new BorderLayout());
        add(placeholder, BorderLayout.CENTER);
    }
    
    public Graph_Panel() {
		new Graph_Panel(new JPanel());
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
        
        // Initialize the individual graph panels with scrollable bar graphs
        electricityGraphPanel = new Scrollable_Bar_Graph_Panel("Monthly Electricity Usage", "Month", "KwH");
        electricityGraphPanel.setBarColor(new Color(79, 129, 189)); // Blue
        
        waterGraphPanel = new Scrollable_Bar_Graph_Panel("Monthly Water Usage", "Month", "m³");
        waterGraphPanel.setBarColor(new Color(155, 187, 89)); // Green
        
        gasGraphPanel = new Scrollable_Bar_Graph_Panel("Monthly Gas Usage", "Month", "Qty");
        gasGraphPanel.setBarColor(new Color(192, 80, 77)); // Red
        
        overallGraphPanel = new Scrollable_Bar_Graph_Panel("Monthly Total Expenses", "Month", "Php");
        overallGraphPanel.setBarColor(new Color(128, 100, 162)); // Purple
        
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