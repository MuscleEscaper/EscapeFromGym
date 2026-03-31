import javax.swing.*;
import java.awt.*;

/**
 * 矢印ボタンの描画
 */
public class ArrowButton extends JButton {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private int direction;

    public ArrowButton(int direction) {
        this.direction = direction;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(new Color(200, 200, 200, 150));
        } else if (getModel().isRollover()) {
            g2.setColor(new Color(255, 255, 255, 100));
        } else {
            g2.setColor(new Color(0, 0, 0, 50));
        }

        int w = getWidth();
        int h = getHeight();
        int arrowSize = 20;
        int[] xPoints;
        int[] yPoints;

        if (direction == LEFT) {
            xPoints = new int[] { w - 10, w - 10, 10 };
            yPoints = new int[] { h / 2 - arrowSize, h / 2 + arrowSize, h / 2 };
        } else {
            xPoints = new int[] { 10, 10, w - 10 };
            yPoints = new int[] { h / 2 - arrowSize, h / 2 + arrowSize, h / 2 };
        }

        g2.fillPolygon(xPoints, yPoints, 3);
    }
}
