package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Following_Tool_Tip {
    private final JWindow tooltipWindow;
    private final JLabel tooltipLabel;
    private final int offsetX = 8;
    private final int offsetY = 20;
    private Timer hoverTimer;
    private boolean isVisible = false;
    private final int delayMs;
    private Point lastMouseScreenLocation;

    public Following_Tool_Tip(Component targetComponent, String tooltipText, int delayMilliseconds) {
        this.delayMs = delayMilliseconds;

        tooltipWindow = new JWindow();
        tooltipLabel = new JLabel(tooltipText);
        tooltipLabel.setOpaque(true);
        tooltipLabel.setBackground(new Color(255, 255, 200));
        tooltipLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        tooltipLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tooltipLabel.setForeground(Color.BLACK);
        tooltipWindow.add(tooltipLabel);
        tooltipWindow.pack();

        targetComponent.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                lastMouseScreenLocation = e.getLocationOnScreen();

                if (hoverTimer != null && hoverTimer.isRunning()) {
                    hoverTimer.restart();
                } else {
                    hoverTimer = new Timer(delayMs, evt -> {
                        if (lastMouseScreenLocation != null) {
                            tooltipWindow.setLocation(
                                lastMouseScreenLocation.x + offsetX,
                                lastMouseScreenLocation.y + offsetY
                            );
                            tooltipWindow.setVisible(true);
                            isVisible = true;
                        }
                    });
                    hoverTimer.setRepeats(false);
                    hoverTimer.start();
                }

                if (isVisible) {
                    tooltipWindow.setVisible(false);
                    isVisible = false;
                }
            }
        });

        targetComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (hoverTimer != null) hoverTimer.stop();
                tooltipWindow.setVisible(false);
                isVisible = false;
            }
        });
    }

    public void setText(String text) {
        tooltipLabel.setText(text);
        tooltipWindow.pack();
    }

    public void setBackground(Color color) {
        tooltipLabel.setBackground(color);
    }

    public void setTextColor(Color color) {
        tooltipLabel.setForeground(color);
    }

    public void setFont(Font font) {
        tooltipLabel.setFont(font);
    }

    public void hide() {
        tooltipWindow.setVisible(false);
        isVisible = false;
    }
}
