package visuals;

import model.User;
import database.Reading_Manager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.Month;
import java.util.Map;

public class Graph_Panel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final CardLayout card_layout;
    private final JPanel graph_container;
    private final Reading_Manager reading_manager;
    private final User current_user;

    private Scrollable_Bar_Graph_Panel electricity_graph;
    private Scrollable_Bar_Graph_Panel water_graph;
    private Scrollable_Bar_Graph_Panel gas_graph;
    private Scrollable_Bar_Graph_Panel overall_graph;

    private String utility_type;
    private boolean is_single_utility;

    /**
     * Constructor for multi-utility graph panel
     */
    public Graph_Panel(Reading_Manager reading_manager, User current_user) {
        this.reading_manager = reading_manager;
        this.current_user = current_user;
        this.is_single_utility = false;

        setLayout(new BorderLayout());
        card_layout = new CardLayout();
        graph_container = new JPanel(card_layout);
        add(graph_container, BorderLayout.CENTER);

        initialize();
    }

    /**
     * Constructor for single utility graph panel
     */
    public Graph_Panel(Reading_Manager reading_manager, User current_user, String utility_type) {
        this.reading_manager = reading_manager;
        this.current_user = current_user;
        this.utility_type = utility_type;
        this.is_single_utility = true;

        setLayout(new BorderLayout());
        card_layout = new CardLayout();
        graph_container = new JPanel(card_layout);
        add(graph_container, BorderLayout.CENTER);

        initializeSingleUtility();
    }

    /**
     * Initialize graphs for multi-utility view
     */
    public void initialize() {
        // Create rounded panels for each graph
        Rounded_Panel electricity_container = new Rounded_Panel(25, Color.BLACK, 0);
        Rounded_Panel water_container = new Rounded_Panel(25, Color.BLACK, 0);
        Rounded_Panel gas_container = new Rounded_Panel(25, Color.BLACK, 0);
        Rounded_Panel overall_container = new Rounded_Panel(25, Color.BLACK, 0);

        // Set layouts and properties
        electricity_container.setLayout(new BorderLayout());
        water_container.setLayout(new BorderLayout());
        gas_container.setLayout(new BorderLayout());
        overall_container.setLayout(new BorderLayout());

        // Initialize scrollable bar graphs
        electricity_graph = new Scrollable_Bar_Graph_Panel("Monthly Electricity Usage", "Month", "KwH");
        water_graph = new Scrollable_Bar_Graph_Panel("Monthly Water Usage", "Month", "m³");
        gas_graph = new Scrollable_Bar_Graph_Panel("Monthly Gas Usage", "Month", "Qty");
        overall_graph = new Scrollable_Bar_Graph_Panel("Monthly Total Expenses", "Month", "Php");

        // Set bar colors
        electricity_graph.setBarColor(new Color(213,205,0));
        water_graph.setBarColor(new Color(79, 129, 189));
        gas_graph.setBarColor(new Color(192, 80, 77));
        overall_graph.setBarColor(new Color(128, 100, 162));

        // Add graphs to containers
        electricity_container.add(electricity_graph, BorderLayout.CENTER);
        water_container.add(water_graph, BorderLayout.CENTER);
        gas_container.add(gas_graph, BorderLayout.CENTER);
        overall_container.add(overall_graph, BorderLayout.CENTER);

        // Add containers to card layout
        graph_container.add(electricity_container, "electricity");
        graph_container.add(water_container, "water");
        graph_container.add(gas_container, "gas");
        graph_container.add(overall_container, "overall");

        updateGraphData();
    }

    /**
     * Initialize graph for single utility view
     */
    private void initializeSingleUtility() {
        Rounded_Panel container = new Rounded_Panel(25, Color.BLACK, 0);
        container.setLayout(new BorderLayout());

        String units = switch (utility_type) {
            case "electricity" -> "Total Price (Php)";
            case "water" -> "Total Price (Php)";
            case "gas" -> "Total Price (Php)";
            default -> "";
        };

        Scrollable_Bar_Graph_Panel utility_graph = new Scrollable_Bar_Graph_Panel("", "Month", units);
        Color barColor = switch (utility_type) {
            case "electricity" -> new Color(213,205,0);
            case "water" -> new Color(79, 129, 189);
            case "gas" -> new Color(192, 80, 77);
            default -> Color.GRAY;
        };
        utility_graph.setBarColor(barColor);

        container.add(utility_graph, BorderLayout.CENTER);
        graph_container.add(container, utility_type);

        // Store reference to update data
        switch (utility_type) {
            case "electricity" -> electricity_graph = utility_graph;
            case "water" -> water_graph = utility_graph;
            case "gas" -> gas_graph = utility_graph;
        }

        updateSingleGraphData();
    }

    private void updateGraphData() {
        try {
            Map<Month, Double> electricity_data = reading_manager.getMonthlyUtilityData(current_user, "electricity", 6, false);
            Map<Month, Double> water_data = reading_manager.getMonthlyUtilityData(current_user, "water", 6, false);
            Map<Month, Double> gas_data = reading_manager.getMonthlyUtilityData(current_user, "gas", 6, false);
            Map<Month, Double> overall_data = reading_manager.getMonthlyTotalExpenses(current_user, 6);

            electricity_graph.setMonthlyData("Electricity", electricity_data, 6);
            water_graph.setMonthlyData("Water", water_data, 6);
            gas_graph.setMonthlyData("Gas", gas_data, 6);
            overall_graph.setMonthlyData("Total Cost", overall_data, 6);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateSingleGraphData() {
        try {
            Map<Month, Double> data = reading_manager.getMonthlyUtilityData(current_user, utility_type, 6, true);
            switch (utility_type) {
                case "electricity" -> electricity_graph.setMonthlyData("Electricity", data, 6);
                case "water" -> water_graph.setMonthlyData("Water", data, 6);
                case "gas" -> gas_graph.setMonthlyData("Gas", data, 6);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods for switching between graphs in multi-utility view
    public void showElectricityGraph() { if (!is_single_utility) card_layout.show(graph_container, "electricity"); }
    public void showWaterGraph() { if (!is_single_utility) card_layout.show(graph_container, "water"); }
    public void showGasGraph() { if (!is_single_utility) card_layout.show(graph_container, "gas"); }
    public void showOverallGraph() { if (!is_single_utility) card_layout.show(graph_container, "overall"); }

    public void refreshData() {
        if (is_single_utility) {
            updateSingleGraphData();
        } else {
            updateGraphData();
        }
    }
}
