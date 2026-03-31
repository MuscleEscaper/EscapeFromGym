import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HintDialog extends JDialog {
    private static final int MARGIN_TOP = 10;
    private static final int MARGIN_BOTTOM = 10;
    private static final int MARGIN_LEFT = 40;
    private static final int MARGIN_RIGHT = 50;

    private Image backgroundImage;
    private JTextArea hintTextArea;
    private BackgroundPanel backgroundPanel;

    public HintDialog(JFrame parent) {
        super(parent, "ヒント", true);
        loadImage("assets/images/HintBackGround.png");
        setLayout(new BorderLayout());
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());

        if (backgroundImage != null) {
            int imgW = backgroundImage.getWidth(null);
            int imgH = backgroundImage.getHeight(null);
            backgroundPanel.setPreferredSize(new Dimension(imgW, imgH));
        } else {
            backgroundPanel.setPreferredSize(new Dimension(400, 300));
        }

        backgroundPanel.setBorder(new EmptyBorder(MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT));
        add(backgroundPanel, BorderLayout.CENTER);

        hintTextArea = new JTextArea();
        hintTextArea.setEditable(false);
        hintTextArea.setOpaque(false);
        hintTextArea.setForeground(Color.WHITE);
        hintTextArea.setFont(new Font("MS Gothic", Font.BOLD, 16));
        hintTextArea.setLineWrap(true);
        hintTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(hintTextArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("閉じる");
        closeButton.addActionListener((ActionEvent e) -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * 進行度に対応するヒント表示
     * 
     * @param progress 進行度
     */
    public void showHint(int progress) {
        String message = HintData.getHintMessage(progress);
        showHintAnyMessage(message);
        GameProgress.getInstance().IncrementProgress(2);
    }

    /**
     * 任意のメッセージをヒントタブにて表示
     * 
     * @param message 表示するメッセージ
     */
    public void showHintAnyMessage(String message) {
        hintTextArea.setText(message);
        backgroundPanel.repaint();
        setVisible(true);
    }

    private void loadImage(String path) {
        try {
            backgroundImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}