package visuals;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Circle_Panel extends JPanel {
	
		private static final long serialVersionUID = 1L;
	
    private static final int DEFAULT_DIAMETER = 150;
    private int diameter = DEFAULT_DIAMETER;
    private BufferedImage image = null;

    public Circle_Panel(int diameter) {
        this.diameter = diameter;
        setPreferredSize(new Dimension(diameter, diameter));
        setOpaque(false);
    }

    public Circle_Panel() {
        this(DEFAULT_DIAMETER);
    }

    public void setImage(File file) {
        try {
            image = ImageIO.read(file);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (diameter <= 0) return;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        Ellipse2D circle = new Ellipse2D.Float(x, y, diameter, diameter);
        g2d.setClip(circle);

        if (image != null) {
            // Calculate image aspect ratio
            double imgWidth = image.getWidth();
            double imgHeight = image.getHeight();
            double imgAspect = imgWidth / imgHeight;

            int drawX = x, drawY = y;
            int drawW = diameter, drawH = diameter;

            if (imgAspect > 1) {
                // Image is wider than tall
                drawH = diameter;
                drawW = (int) (imgAspect * drawH);
                drawX = x - (drawW - diameter) / 2;
            } else {
                // Image is taller than wide or square
                drawW = diameter;
                drawH = (int) (drawW / imgAspect);
                drawY = y - (drawH - diameter) / 2;
            }

            g2d.drawImage(image, drawX, drawY, drawW, drawH, this);
        } else {
            g2d.setColor(Color.BLUE);
            g2d.fill(circle);
        }

        g2d.dispose();
    }
}
