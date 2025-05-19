package visuals;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

import javax.swing.*;
import java.awt.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Bar_Graph_Panel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryChart chart;
    private XChartPanel<CategoryChart> chartPanel;

    /**
     * Creates a bar graph panel with the specified title and axis labels
     * 
     * @param title Chart title
     * @param xAxisTitle X-axis label
     * @param yAxisTitle Y-axis label
     */
    public Bar_Graph_Panel(String title, String xAxisTitle, String yAxisTitle) {
        setLayout(new BorderLayout());
        
        // Create chart
        chart = new CategoryChartBuilder()
                .width(380)
                .height(300)
                .title(title)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        // Customize chart style
        chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setXAxisLabelRotation(45);

        // Create chart panel
        chartPanel = new XChartPanel<>(chart);
        
        // Add to this panel
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Updates the chart with new data
     * 
     * @param seriesName Name of the data series
     * @param categories List of category names (x-axis)
     * @param values List of values (y-axis)
     */
    public void setData(String seriesName, List<String> categories, List<? extends Number> values) {
        // Remove old series if it exists
        chart.removeSeries(seriesName);
        
        // Add new series
        chart.addSeries(seriesName, categories, values);
        
        // Refresh the chart
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    /**
     * Updates the chart with monthly data
     * 
     * @param seriesName Name of the data series
     * @param monthlyData Map with Month as key and value as double
     */
    public void setMonthlyData(String seriesName, Map<Month, Double> monthlyData, int monthsToShowBefore) {
        List<String> monthNames = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        // Get current month
        Month currentMonth = LocalDate.now().getMonth();

        // Build the list of months to show (latest on the right)
        for (int i = monthsToShowBefore; i >= 0; i--) {
            Month month = currentMonth.minus(i); // Handles wrap-around internally
            String shortName = month.toString().substring(0, 3);
            monthNames.add(shortName);

            // Use data if available, else default to 0
            values.add(monthlyData.getOrDefault(month, 0.0));
        }

        // Update chart with new data
        setData(seriesName, monthNames, values);
    }
}