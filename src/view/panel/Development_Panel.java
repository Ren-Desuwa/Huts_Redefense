package view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;

import database.Database_Manager;
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

        // Panel to hold the chart
        JPanel chartHolder = new JPanel();
        chartHolder.setLayout(new BorderLayout()); // Important to use layout that respects component sizing
        chartHolder.setBounds(244, 48, 456, 261);
        add(chartHolder);

        // Sample data for electricity usage
        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun");
        List<Double> wattData = Arrays.asList(320.0, 290.0, 310.0, 270.0, 350.0, 330.0);

        // Create the chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(456)
                .height(261)
                .title("Monthly Electricity Usage")
                .xAxisTitle("Month")
                .yAxisTitle("Watts")
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.addSeries("Watts", months, wattData);

        // Add XChartPanel to Swing panel
        XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
        chartHolder.add(chartPanel, BorderLayout.CENTER);
    }
    
    public void updateDimensions(JPanel sidePanel) {
		Dimension sidePanelSize = sidePanel.getSize();
		dimensionLabel1.setText("Side Panel Dimensions: " + sidePanelSize.width + " x " + sidePanelSize.height);
		
		Dimension cardPanelSize = this.getSize();
		dimensionLabel2.setText("Card Panel Dimensions: " + cardPanelSize.width + " x " + cardPanelSize.height);
	}
}
