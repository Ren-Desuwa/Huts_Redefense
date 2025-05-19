package visuals;

import javax.swing.*;
import java.awt.*;

public class Rounded_Button extends JButton {
    private int radius;

    public Rounded_Button(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setFont(new Font("Tahoma", Font.PLAIN, 16));
        setBackground(new Color(143, 163, 165)); // soft gray like image
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    public void paintBorder(Graphics g) {
        // No border
    }
}
