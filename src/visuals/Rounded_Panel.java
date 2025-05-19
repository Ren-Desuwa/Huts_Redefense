package visuals;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;


public class Rounded_Panel extends JPanel {
	private static final long serialVersionUID = 1L;

	// Constants for default values
	private static final int DEFAULT_RADIUS = 15;
	private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	private static final int DEFAULT_BORDER_THICKNESS = 2;

	// Properties for rounded panel
	private final int radius;
    private final Color borderColor;
    private final int borderThickness;

    public Rounded_Panel(int radius, Color borderColor, int borderThickness) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        setOpaque(false); // Required to paint custom background
    }
    
    public Rounded_Panel(int radius) {
		this(radius, DEFAULT_BORDER_COLOR, 0);
	}
    
    public Rounded_Panel() {
		this(DEFAULT_RADIUS, DEFAULT_BORDER_COLOR, DEFAULT_BORDER_THICKNESS);
	}

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create rounded background
        Shape roundedRect = new RoundRectangle2D.Float(
                borderThickness / 2f,
                borderThickness / 2f,
                getWidth() - borderThickness,
                getHeight() - borderThickness,
                radius, radius
        );

        // Paint background
        g2.setColor(getBackground());
        g2.fill(roundedRect);

        // Paint border
        if (borderThickness > 0) {
            g2.setStroke(new BasicStroke(borderThickness));
            g2.setColor(borderColor);
            g2.draw(roundedRect);
        }

        g2.dispose();

        // Paint children
        super.paintComponent(g);
    }
    
    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Float(
            borderThickness / 2f,
            borderThickness / 2f,
            getWidth() - borderThickness,
            getHeight() - borderThickness,
            radius,
            radius
        );

        g2.setClip(clip); // Clip children painting to rounded area
        super.paintChildren(g2);
        g2.dispose();
    }
}