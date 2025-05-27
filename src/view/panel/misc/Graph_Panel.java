package view.panel.misc;


import visuals.Scrollable_Bar_Graph_Panel;


import javax.swing.*;
import java.awt.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

public class Graph_Panel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final CardLayout card_layout; 
    private final JPanel graph_container;

    private Scrollable_Bar_Graph_Panel electricity_graph;
    private Scrollable_Bar_Graph_Panel water_graph;
    private Scrollable_Bar_Graph_Panel gas_graph;
    private Scrollable_Bar_Graph_Panel overall_graph;

    private int selected_year;
    String field;
    private String utility_type;
    private boolean is_single_utility;

    /**
     * Constructor for multi-utility graph panel
     */
    public Graph_Panel(String field) {
        this.field = field;
        this.is_single_utility = false;
        this.selected_year = LocalDate.now().getYear(); // Default to current year

        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        card_layout = new CardLayout();
        graph_container = new JPanel(card_layout);
        graph_container.setBackground(new Color(255, 255, 255));
        add(graph_container, BorderLayout.CENTER);

        initialize();
    }

    /**
     * Constructor for single utility graph panel
     */
    public Graph_Panel(String field, String utility_type) {
        this.field = field;
        this.utility_type = utility_type;
        this.is_single_utility = true;
        this.selected_year = LocalDate.now().getYear(); // Default to current year

        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        card_layout = new CardLayout();
        graph_container = new JPanel(card_layout);
        graph_container.setBackground(new Color(255, 255, 255));
        add(graph_container, BorderLayout.CENTER);

        initializeSingleUtility();
    }

    /**
     * Initialize graphs for multi-utility view
     */
    public void initialize() {
        // Create rounded panels for each graph
        JPanel electricity_container = new JPanel();
        JPanel water_container = new JPanel();
        JPanel gas_container = new JPanel();
        JPanel overall_container = new JPanel();

        // Set layouts and properties
        electricity_container.setLayout(new BorderLayout());
        electricity_container.setBackground(new Color(255, 255, 255));
        water_container.setLayout(new BorderLayout());
        water_container.setBackground(new Color(255, 255, 255));
        gas_container.setLayout(new BorderLayout());
        gas_container.setBackground(new Color(255, 255, 255));
        overall_container.setLayout(new BorderLayout());
        overall_container.setBackground(new Color(255, 255, 255));

        // Initialize scrollable bar graphs
        electricity_graph = new Scrollable_Bar_Graph_Panel("Monthly Electricity Usage", "Month", "KwH");
        water_graph = new Scrollable_Bar_Graph_Panel("Monthly Water Usage", "Month", "m³");
        gas_graph = new Scrollable_Bar_Graph_Panel("Monthly Gas Usage", "Month", "kg");
        overall_graph = new Scrollable_Bar_Graph_Panel("Monthly Total Expenses", "Month", "Php");

        // Set bar colors
        electricity_graph.setBarColor(new Color(255,206,0)); // Yellow for Electricity
        water_graph.setBarColor(new Color(79, 129, 189)); // Blue for Water
        gas_graph.setBarColor(new Color(192, 80, 77)); // Red for Gas
        overall_graph.setBarColor(new Color(128, 100, 162)); // Purple for Overall

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


        Map<Month, Double> mockOverallData = Map.of(
            Month.JANUARY, 100.0,
            Month.FEBRUARY, 150.0,
            Month.MARCH, 200.0
        );
        Map<Month, Double> mockElectricityData = Map.of(
            Month.JANUARY, 50.0,
            Month.FEBRUARY, 75.0,
            Month.MARCH, 100.0
        );
        Map<Month, Double> mockWaterData = Map.of(
            Month.JANUARY, 30.0,
            Month.FEBRUARY, 45.0,
            Month.MARCH, 60.0
        );
        Map<Month, Double> mockGasData = Map.of(
            Month.JANUARY, 20.0,
            Month.FEBRUARY, 30.0,
            Month.MARCH, 40.0
        );

        // Update graphs with mock data
        updateGraphData(mockOverallData, mockElectricityData, mockWaterData, mockGasData);
    }

    /**
     * Initialize graph for single utility view
     */
    private void initializeSingleUtility() {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(255, 255, 255));

        String units = switch (field) {
        	case "reading" -> 
        		switch (utility_type) {
	        		case "electricity" -> "KwH";
	        		case "water" -> "m³";
	        		case "gas" -> "kg";
	        		default -> "";
        };
	        case "rate" -> 
	        	switch (utility_type) {
		            case "electricity" -> "Rate (Php/KwH)";
		            case "water" -> "Rate (Php/m³)";
		            case "gas" -> "Rate (Php/kg)";
		            default -> "";
	        };
	        case "total" -> "Total Price (Php)";
	        default -> "";
        };

        Scrollable_Bar_Graph_Panel utility_graph = new Scrollable_Bar_Graph_Panel("Month", units);
        utility_graph.changeYAxisLabel(units); // Set Y-axis label based on field
        if (utility_type.equals("gas")) {
            utility_graph.setGasType(true);
        }
        Color barColor = switch (utility_type) {
            case "electricity" -> new Color(255,206,0); // Yellow for Electricity
            case "water" -> new Color(79, 129, 189); // Blue for Water
            case "gas" -> new Color(192, 80, 77); // Red for Gas
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

        Map<Month, Double> Data = Map.of(
                Month.JANUARY, 20.0,
                Month.FEBRUARY, 30.0,
                Month.MARCH, 40.0
            );
        updateSingleGraphData(Data);
    }



    // Update graph data for multi-utility view
    private void updateGraphData(Map<Month, Double> overall_data, Map<Month, Double> electricity_data, Map<Month, Double> water_data, Map<Month, Double> gas_data) {
        electricity_graph.setMonthlyData("Electricity " + field, electricity_data);
        water_graph.setMonthlyData("Water " + field, water_data);
        gas_graph.setMonthlyData("Gas " + field, gas_data);
        overall_graph.setMonthlyData("Overall " + field, overall_data);
    }

    // Update graph data for single-utility view
    private void updateSingleGraphData(Map<Month, Double> data) {
        switch (utility_type) {
            case "electricity" -> electricity_graph.setMonthlyData("Electricity " + field, data);
            case "water" -> water_graph.setMonthlyData("Water " + field, data);
            case "gas" -> gas_graph.setMonthlyData("Gas " + field, data);
        }
    }


    // Methods for switching between graphs in multi-utility view
    public void showElectricityGraph() { if (!is_single_utility) card_layout.show(graph_container, "electricity"); }
    public void showWaterGraph() { if (!is_single_utility) card_layout.show(graph_container, "water"); }
    public void showGasGraph() { if (!is_single_utility) card_layout.show(graph_container, "gas"); }
    public void showOverallGraph() { if (!is_single_utility) card_layout.show(graph_container, "overall"); }
    

    public void refreshDataHome(Map<Month, Double> overall_data, Map<Month, Double> electricity_data, Map<Month, Double> water_data, Map<Month, Double> gas_data) {
    	updateGraphData(overall_data, electricity_data, water_data, gas_data);
    }
    
    public void refreshDataSingle(Map<Month, Double> data) {
		updateSingleGraphData(data);
    }
    

    /**
     * Sets the year for data display and refreshes the graphs
     * @param year The year to display data for
     */
    public void setYear(int year, Map<Month, Double> overall_data, Map<Month, Double> electricity_data, Map<Month, Double> water_data, Map<Month, Double> gas_data) {
        this.selected_year = year;
        if (is_single_utility) {
            updateSingleGraphData(electricity_data); // Pass the relevant data
        } else {
            updateGraphData(overall_data, electricity_data, water_data, gas_data);
        }
    }

    /**
     * Gets the currently selected year
     * @return The selected year
     */
    public int getSelectedYear() {
        return selected_year;
    }
    
    public String getField() {
		return field;
	}

    public void setField(String field, Map<Month, Double> data) {
        this.field = field;

        if (is_single_utility) {
            String units = switch (field) {
                case "reading" -> switch (utility_type) {
                    case "electricity" -> "KwH";
                    case "water" -> "m³";
                    case "gas" -> "kg";
                    default -> "";
                };
                case "rate" -> switch (utility_type) {
                    case "electricity" -> "Rate (Php/KwH)";
                    case "water" -> "Rate (Php/m³)";
                    case "gas" -> "Rate (Php/kg)";
                    default -> "";
                };
                case "total" -> "Total Price (Php)";
                default -> "";
            };

            switch (utility_type) {
                case "electricity" -> electricity_graph.changeYAxisLabel(units);
                case "water" -> water_graph.changeYAxisLabel(units);
                case "gas" -> gas_graph.changeYAxisLabel(units);
            }

            refreshDataSingle(data); // Update single utility graph
        }
    }

    public void setField(String field, Map<Month, Double> overall_data, Map<Month, Double> electricity_data, Map<Month, Double> water_data, Map<Month, Double> gas_data) {
        this.field = field;

        if (!is_single_utility) {
            // Multi-utility mode: update all graphs
            electricity_graph.changeYAxisLabel(switch (field) {
                case "reading" -> "KwH";
                case "rate" -> "Rate (Php/KwH)";
                case "total" -> "Total Price (Php)";
                default -> "";
            });

            water_graph.changeYAxisLabel(switch (field) {
                case "reading" -> "m³";
                case "rate" -> "Rate (Php/m³)";
                case "total" -> "Total Price (Php)";
                default -> "";
            });

            gas_graph.changeYAxisLabel(switch (field) {
                case "reading" -> "kg";
                case "rate" -> "Rate (Php/kg)";
                case "total" -> "Total Price (Php)";
                default -> "";
            });

            overall_graph.changeYAxisLabel("Php"); // Always Php for overall

            refreshDataHome(overall_data, electricity_data, water_data, gas_data); // Update all graphs
        }
    }

}
