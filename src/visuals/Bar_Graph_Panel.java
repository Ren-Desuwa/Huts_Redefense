package visuals;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.util.List;

public class Bar_Graph_Panel extends JPanel {

    private CategoryChart chart;
    private XChartPanel<CategoryChart> chartPanel;

    public Bar_Graph_Panel(String title, String xAxisTitle, String yAxisTitle) {
        // Create chart
        chart = new CategoryChartBuilder()
                .width(600)
                .height(400)
                .title(title)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        // Customize chart style
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(.7);
        chart.getStyler().setOverlapped(true);

        // Wrap it in XChartPanel
        chartPanel = new XChartPanel<>(chart);

        // Add to this panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chartPanel);
    }

    // Method to set or update data
    public void setData(String seriesName, List<String> categories, List<? extends Number> values) {
        chart.updateCategorySeries(seriesName, categories, values, null);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}
