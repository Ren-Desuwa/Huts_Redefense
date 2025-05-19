package visuals;

import javax.swing.*;
import java.awt.*;
import java.time.Month;
import java.util.Map;

/**
 * Test class for demonstrating the Scrollable_Bar_Graph_Panel
 */
public class GraphTest {
    
    /**
     * Main method to run the test application
     */
    public static void main(String[] args) {
        // Create and configure the test frame on the EDT
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("Scrollable Graph Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            
            // Create a tabbed pane for multiple graph examples
            JTabbedPane tabbedPane = new JTabbedPane();
            
            // Generate test data
            Map<String, Map<Month, Double>> testData = GraphDataUtil.generateAllTestData(12);
            
            // Create electricity graph
            Scrollable_Bar_Graph_Panel electricityGraph = new Scrollable_Bar_Graph_Panel(
                    "Monthly Electricity Usage", "Month", "KwH");
            electricityGraph.setBarColor(new Color(79, 129, 189)); // Blue
            electricityGraph.setMonthlyData("Electricity", testData.get("electricity"), 12);
            
            // Create water graph
            Scrollable_Bar_Graph_Panel waterGraph = new Scrollable_Bar_Graph_Panel(
                    "Monthly Water Usage", "Month", "m³");
            waterGraph.setBarColor(new Color(155, 187, 89)); // Green
            waterGraph.setMonthlyData("Water", testData.get("water"), 12);
            
            // Create gas graph
            Scrollable_Bar_Graph_Panel gasGraph = new Scrollable_Bar_Graph_Panel(
                    "Monthly Gas Usage", "Month", "Qty");
            gasGraph.setBarColor(new Color(192, 80, 77)); // Red
            gasGraph.setMonthlyData("Gas", testData.get("gas"), 12);
            
            // Create overall graph
            Scrollable_Bar_Graph_Panel overallGraph = new Scrollable_Bar_Graph_Panel(
                    "Monthly Total Expenses", "Month", "Php");
            overallGraph.setBarColor(new Color(128, 100, 162)); // Purple
            overallGraph.setMonthlyData("Total", testData.get("overall"), 12);
            
            // Add all graphs to tabbed pane
            tabbedPane.addTab("Electricity", electricityGraph);
            tabbedPane.addTab("Water", waterGraph);
            tabbedPane.addTab("Gas", gasGraph);
            tabbedPane.addTab("Overall", overallGraph);
            
            // Add tabbed pane to frame
            frame.getContentPane().add(tabbedPane);
            
            // Display the frame
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}