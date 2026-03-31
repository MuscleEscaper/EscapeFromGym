import javax.swing.*;
import java.awt.*;

/**
 * エントリーポイント
 */
public class EscapeGameApp extends JFrame {
    private HintDialog hintDialog;

    public EscapeGameApp() {
        setTitle("脱出ゲーム Project");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        hintDialog = new HintDialog(this);

        // メイン画面
        MainViewPanel mainViewPanel = new MainViewPanel();
        add(mainViewPanel, BorderLayout.CENTER);

        // ボタンパネル
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(800, 50));
        JButton inventoryButton = new JButton("Inventory"); // インベントリボタン
        inventoryButton.setPreferredSize(new Dimension(400, 30));
        inventoryButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        inventoryButton.addActionListener(e -> {
            new InventoryUI(this);
        });
        JButton hintButton = new JButton("Hint"); // ヒントボタン
        hintButton.setPreferredSize(new Dimension(400, 30));
        hintButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        hintButton.addActionListener(e -> {
            if (GameProgress.getInstance().getProgress() > 1)
                hintDialog.showHint(GameProgress.getInstance().getProgress());
        });
        headerPanel.add(inventoryButton, BorderLayout.WEST);
        headerPanel.add(hintButton, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        GameProgress.getInstance().StartGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EscapeGameApp().setVisible(true);
        });
    }
}