import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * ダンベルの南京錠
 * パスワード：2741
 */
public class LockDumbbell extends JDialog {
    private Padlock padlock = new Padlock();
    private JButton[] dials = new JButton[4];
    private JLabel statusLabel = new JLabel("LOCKED", SwingConstants.CENTER);
    private Runnable onSuccess;

    private static final String BACKGROUND_IMAGE_PATH = "assets/images/Padlock/Padlock_dumbbell.png";

    public LockDumbbell(Window owner, Runnable onSuccess) {
        super(owner, "Padlock", ModalityType.APPLICATION_MODAL);
        this.onSuccess = onSuccess;

        setSize(400, 250);
        setLocationRelativeTo(owner);

        BackgroundPanel bgPanel = new BackgroundPanel(BACKGROUND_IMAGE_PATH);
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(Color.RED);
        bgPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel dialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        dialPanel.setOpaque(false);

        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton("0");
            btn.setFont(new Font("Dialog", Font.BOLD, 40));
            btn.setPreferredSize(new Dimension(70, 70));
            btn.addActionListener(this::incrementDial);
            dials[i] = btn;
            dialPanel.add(btn);
        }
        bgPanel.add(dialPanel, BorderLayout.CENTER);

        // ボタンパネル
        JPanel buttonPanel = new JPanel(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);
        JButton checkBtn = new JButton("照合"); // 照合ボタン
        checkBtn.setFont(new Font("Dialog", Font.PLAIN, 16));
        checkBtn.addActionListener(e -> verify());
        buttonPanel.add(checkBtn, BorderLayout.WEST);
        JButton cancelButton = new JButton("閉じる"); // 閉じるボタン
        cancelButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton, BorderLayout.EAST);
    }

    private void incrementDial(ActionEvent e) {
        if (!padlock.isLocked())
            return;
        JButton btn = (JButton) e.getSource();
        int val = Integer.parseInt(btn.getText());
        btn.setText(String.valueOf((val + 1) % 10));
    }

    private void verify() {
        StringBuilder sb = new StringBuilder();
        for (JButton btn : dials)
            sb.append(btn.getText());

        if (padlock.checkPassword(Integer.parseInt(sb.toString()))) {
            statusLabel.setText("UNLOCKED");
            statusLabel.setForeground(Color.GREEN);
            for (JButton btn : dials)
                btn.setEnabled(false);

            Timer timer = new Timer(500, e -> {
                dispose();
                if (onSuccess != null)
                    onSuccess.run();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String filePath) {
            this.backgroundImage = new ImageIcon(filePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null)
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    static class Padlock {
        private int password = 2741;
        private boolean isLocked = true;

        public boolean checkPassword(int password) {
            if (this.password == password) {
                this.isLocked = false;
                return true;
            }
            return false;
        }

        public boolean isLocked() {
            return this.isLocked;
        }
    }
}