package view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;

import database.Database_Manager;
import model.Reading;
import model.User;

public class Development_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private User current_user;
	
	private JLabel dimensionLabel1;
    private JLabel dimensionLabel2;
    private JLabel dimensionLabel3;
    private JLabel dimensionLabel4;
	
    public Development_Panel(database.Database_Manager database_manager, User current_user) {
        this.database_manager = database_manager;
        this.current_user = current_user;

        setLayout(null);
        setPreferredSize(new Dimension(986, 688));

        dimensionLabel1 = new JLabel("Side Panel Dimensions");
        dimensionLabel1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        dimensionLabel1.setBounds(10, 11, 187, 26);
        add(dimensionLabel1);

        dimensionLabel2 = new JLabel("Card Panel Dimensions");
        dimensionLabel2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        dimensionLabel2.setBounds(10, 30, 187, 26);
        add(dimensionLabel2);
        
        
        // Sample data for the list
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
                          "Item 6", "Item 7", "Item 8", "Item 9", "Item 10"};

        // Create the JList
        JList<String> list = new JList<>(items);

        // Put the JList into a JScrollPane
        JScrollPane scrollPane_1 = new JScrollPane(list);
        scrollPane_1.setBounds(341, 94, 431, 313);
        add(scrollPane_1);
        
//        double reading1 = 100;
//        double reading2 = 20;
//        double reading3 = 30;
//        
//        double newReading = 50;
//        LocalDate date = LocalDate.of(2023, 10, 1);
//        Reading reading = new Reading(100, date, "other", reading1, reading2, reading3);
//        reading.setRate(newReading);
        
        
        // Create a frame to show it


//        // Panel to hold the chart
//        JPanel chartHolder = new JPanel();
//        chartHolder.setLayout(new BorderLayout()); // Important to use layout that respects component sizing
//        chartHolder.setBounds(244, 48, 456, 261);
//        add(chartHolder);
//
//        // Sample data for electricity usage
//        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun");
//        List<Double> wattData = Arrays.asList(320.0, 290.0, 310.0, 270.0, 350.0, 330.0);
//
//        // Create the chart
//        CategoryChart chart = new CategoryChartBuilder()
//                .width(456)
//                .height(261)
//                .title("Monthly Electricity Usage")
//                .xAxisTitle("Month")
//                .yAxisTitle("Watts")
//                .build();
//
//        chart.getStyler().setLegendVisible(false);
//        chart.addSeries("Watts", months, wattData);
//
//        // Add XChartPanel to Swing panel
//        XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
//        chartHolder.add(chartPanel, BorderLayout.CENTER);
    }
    
    public void updateDimensions(JPanel sidePanel) {
		Dimension sidePanelSize = sidePanel.getSize();
		dimensionLabel1.setText("Side Panel Dimensions: " + sidePanelSize.width + " x " + sidePanelSize.height);
		
		Dimension cardPanelSize = this.getSize();
		dimensionLabel2.setText("Card Panel Dimensions: " + cardPanelSize.width + " x " + cardPanelSize.height);
	}
}
