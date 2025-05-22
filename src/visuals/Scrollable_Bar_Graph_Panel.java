package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.Month;
import java.util.*;
import java.util.List;

/**
 * A customized bar graph panel that supports horizontal scrolling
 * This panel is WindowBuilder-compatible
 */
public class Scrollable_Bar_Graph_Panel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    // UI Components
    private JPanel chartPanel;          // Container for the bar chart 
    private JPanel axisLabelPanel;      // Fixed panel for Y-axis labels and title
    private JPanel headerPanel;         // Fixed panel for title and series name
    private JScrollPane scrollPane;     // Scrollpane for horizontal scrolling
    
    // Graph Data
    private Map<Month, Double> data;    // Data points to display
    private String title;               // Graph title
    private String xAxisLabel;          // X-axis label
    private String yAxisLabel;          // Y-axis label
    private String seriesName;          // Name of the data series
    private int monthsToShow;           // How many months to display
    private double maxValue;            // Maximum value for Y-axis scaling
    private boolean hasHeader = true;
    
 // Common drawing constants
    private static final int BAR_WIDTH = 50;         // Width of each bar
    private static final int BAR_SPACING = 20;       // Space between bars
    private static final int Y_AXIS_WIDTH = 60;      // Width of y-axis panel
    private static final int HEADER_HEIGHT = 40;     // Height of the header panel
    private static final int DEFAULT_HEIGHT = 300;   // Default height of the panel

    // Margin constants that vary based on header presence      // Top margin
    
    // Color scheme
    private Color barColor = new Color(79, 129, 189); // Blue color for bars
    private Color textColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE; // Transparent background
    
    /**
     * Default constructor for WindowBuilder compatibility
     */
    public Scrollable_Bar_Graph_Panel() {
        this("Graph Title", "X Axis", "Y Axis");
    }
    
    /**
     * Creates a new bar graph panel without a header
     * 
     * @param xAxisLabel Label for the x-axis
     * @param yAxisLabel Label for the y-axis
     */
    public Scrollable_Bar_Graph_Panel(String xAxisLabel, String yAxisLabel) {
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.data = new HashMap<>();
        this.monthsToShow = 6;
        this.maxValue = 100.0;
        this.seriesName = "";
        this.hasHeader = false;
        
        initializeUI();
     // Set a larger height when there's no header
        setPreferredSize(new Dimension(getWidth(), 400)); // Increased from 300 to 400
    }

    
    /**
     * Creates a new bar graph panel with the specified labels
     * 
     * @param title Title of the graph
     * @param xAxisLabel Label for the x-axis
     * @param yAxisLabel Label for the y-axis
     */
    public Scrollable_Bar_Graph_Panel(String title, String xAxisLabel, String yAxisLabel) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.data = new HashMap<>();
        this.monthsToShow = 6;
        this.maxValue = 100.0; // Default max value
        this.seriesName = ""; // Default empty series name
        
        initializeUI();
        
    }

    /**
     * Initialize the UI components
     */
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(0,0,0,0));

        if (hasHeader) {
            // Create header panel
            headerPanel = new JPanel() {
                private static final long serialVersionUID = 1L;
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawHeader(g);
                }
            };
            headerPanel.setPreferredSize(new Dimension(getWidth(), HEADER_HEIGHT));
            headerPanel.setBackground(backgroundColor);
            add(headerPanel, BorderLayout.NORTH);
        }

        // Create Y-axis labels panel
        axisLabelPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawYAxisLabels(g);
            }
        };
        axisLabelPanel.setPreferredSize(new Dimension(Y_AXIS_WIDTH, 
            hasHeader ? DEFAULT_HEIGHT - HEADER_HEIGHT : DEFAULT_HEIGHT - 100));
        axisLabelPanel.setBackground(backgroundColor);

        chartPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBars(g);
            }
        };
        chartPanel.setBackground(backgroundColor);

        scrollPane = new JScrollPane(chartPanel,
                                   JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(backgroundColor);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(axisLabelPanel, BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.setBackground(backgroundColor);

        add(contentPanel, BorderLayout.CENTER);
    }


    /**
     * Sets the monthly data to be displayed
     * 
     * @param seriesName Name of the data series
     * @param data Map of Month to data values
     * @param monthsToShow Number of months to display
     */
    public void setMonthlyData(String seriesName, Map<Month, Double> data, int monthsToShow) {
        this.seriesName = seriesName;
        this.data = data != null ? data : new HashMap<>();
        this.monthsToShow = monthsToShow;
        
        // Calculate the maximum value for y-axis scaling
        this.maxValue = calculateMaxValue();
        
        // Update the size of the chart panel based on the number of bars
        updateChartPanelSize();
        
        // Repaint the panels
        repaint();
    }
    
    /**
     * Sets the color of the bars
     * 
     * @param barColor The color to use for the bars
     */
    public void setBarColor(Color barColor) {
        this.barColor = barColor;
        repaint();
    }
    
    /**
     * Updates the chart panel size based on the number of months to show
     */
    private void updateChartPanelSize() {
        int width;

        if (data == null || data.isEmpty()) {
            width = scrollPane != null ? scrollPane.getViewport().getWidth() : getWidth();
            if (width <= 0) width = 400;
        } else {
            width = calculateChartWidth();
        }

        // Adjust height based on whether header exists
        int height = getHeight() > 0 ? 
                     getHeight() : 
                     (hasHeader ? DEFAULT_HEIGHT : DEFAULT_HEIGHT + 100); // Add extra height when no header

        chartPanel.setPreferredSize(new Dimension(width, height));
        chartPanel.revalidate();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (headerPanel != null && hasHeader) {
            headerPanel.setPreferredSize(new Dimension(width, HEADER_HEIGHT));
        }
        updateChartPanelSize();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (headerPanel != null && hasHeader) {
            headerPanel.setPreferredSize(new Dimension(width, HEADER_HEIGHT));
        }
        updateChartPanelSize();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        if (headerPanel != null && hasHeader) {
            headerPanel.setPreferredSize(new Dimension(d.width, HEADER_HEIGHT));
        }
        updateChartPanelSize();
    }

    
    /**
     * Calculate the width needed for the chart based on data
     */
    private int calculateChartWidth() {
        // Calculate how many bars we will draw
        int barCount = data.size() > 0 ? data.size() : monthsToShow;
        
        // Calculate width based on bars and spacing
        return barCount * (BAR_WIDTH + BAR_SPACING) + BAR_SPACING;
    }
    
    /**
     * Determine the maximum value in the data for scaling
     */
    private double calculateMaxValue() {
        double max = 10.0; // Default minimum max value
        
        if (data != null && !data.isEmpty()) {
            max = Collections.max(data.values());
            // Round up to a nicer number
            max = Math.ceil(max * 1.1); // Add 10% headroom
        }
        
        return max > 0 ? max : 10.0;
    }
    
    /**
     * Draw the fixed header containing the title and series name
     */
    private void drawHeader(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw the title
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.setColor(textColor);
        g2d.drawString(title, 10, 20);
        
        // Draw the series name (if provided)
        if (seriesName != null && !seriesName.isEmpty()) {
            g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
            
        }
    }
    
    /**
     * Draw the Y-axis labels on the left panel
     */
    private void drawYAxisLabels(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int height = getHeight() - getTopMargin() - getBottomMargin();
        int width = Y_AXIS_WIDTH;
        
        // Set font and color
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2d.setColor(textColor);
        
        // Draw Y-axis label (rotated)
        Font labelFont = new Font("SansSerif", Font.BOLD, 12);
        g2d.setFont(labelFont);
        
        // Rotate and draw Y-axis label
        AffineTransform originalTransform = g2d.getTransform();
        g2d.rotate(-Math.PI / 2);
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(yAxisLabel);
        g2d.drawString(yAxisLabel, -(getTopMargin() + height/2 + labelWidth/2), 15);
        g2d.setTransform(originalTransform);
        
        // Draw Y-axis value labels
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
        
        int numDivisions = 5;
        for (int i = 0; i <= numDivisions; i++) {
            double value = maxValue * (numDivisions - i) / numDivisions;
            int y = getTopMargin() + (height * i) / numDivisions;
            
            String valueStr = String.format("%.1f", value);
            fm = g2d.getFontMetrics();
            int strWidth = fm.stringWidth(valueStr);
            
            // Draw tick mark
            g2d.drawLine(width - 5, y, width, y);
            
            // Draw value label
            g2d.drawString(valueStr, width - 10 - strWidth, y + 4);
        }
    }
    
    /**
     * Draw the bars and x-axis labels
     */
    private void drawBars(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int availableHeight = getHeight() - getTopMargin() - getBottomMargin();
        int xAxisY = getTopMargin() + availableHeight;  // Y position for x-axis
        int height = getHeight() - getTopMargin() - getBottomMargin();
        
        // Sort the data by months
        List<Month> sortedMonths = new ArrayList<>(data.keySet());
        Collections.sort(sortedMonths);
        
        if (data.isEmpty()) {
            // Draw "No Data" message
            g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2d.drawString("No Data Available Yet", 20, xAxisY/4);
            return;
        }
        
        // Draw X-axis
        g2d.drawLine(BAR_SPACING, getTopMargin() + height, calculateChartWidth() - BAR_SPACING, xAxisY);
        
        // Draw X-axis label
        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int xLabelWidth = fm.stringWidth(xAxisLabel);
        g2d.drawString(xAxisLabel, (calculateChartWidth() - xLabelWidth) / 2, xAxisY + 30);
        
        // Draw the bars and x-axis labels
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
        int barIndex = 0;
        
        for (Month month : sortedMonths) {
            double value = data.get(month);
            int x = BAR_SPACING + barIndex * (BAR_WIDTH + BAR_SPACING);
            int barHeight = (int)(value * height / maxValue);
            
            // Draw bar
            g2d.setColor(barColor);
            g2d.fillRect(x, xAxisY - barHeight, BAR_WIDTH, barHeight);
            
            // Draw bar outline
            g2d.setColor(barColor.darker());
            g2d.drawRect(x, xAxisY - barHeight, BAR_WIDTH, barHeight);
              
            // Draw month label
            g2d.setColor(textColor);
            String monthStr = month.toString().substring(0, 3);
            fm = g2d.getFontMetrics();
            int monthWidth = fm.stringWidth(monthStr);
            g2d.drawString(monthStr, x + (BAR_WIDTH - monthWidth) / 2, xAxisY + 15);
            
            // Draw value above bar
            String valueStr = String.format("%.1f", value);
            int valueWidth = fm.stringWidth(valueStr);
            g2d.drawString(valueStr, x + (BAR_WIDTH - valueWidth) / 2, xAxisY - barHeight - 5);
            
            barIndex++;
        }
    }
    private int getTopMargin() {
    	int height = hasHeader ? 10 : 10;
    	return height;
    }
    private int getBottomMargin() {
    	int height = hasHeader ? 90 : 60;
    	return height;
    }
}