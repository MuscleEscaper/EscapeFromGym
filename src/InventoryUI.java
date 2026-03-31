import java.awt.*;
import javax.swing.*;

public class InventoryUI {

    private JDialog dialog;
    private JPanel rightPanel; // アイテム一覧用パネル
    private PlayerPanel playerPanel; // ステータス用パネル
    private InventoryManage inventory;

    // コンストラクタ
    public InventoryUI(JFrame owner) {

        this.inventory = InventoryManage.getInstance();

        // JDialogを作成（モーダル）
        dialog = new JDialog(owner, "Inventory", true);
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(owner);

        // --- メインレイアウト ---
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // --- 左側（ステータスなど） ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        playerPanel = new PlayerPanel();
        playerPanel.setPreferredSize(new Dimension(200, 300));

        // 閉じるボタン
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton closeButton = new JButton("閉じる");
        closeButton.setPreferredSize(new Dimension(100, 40));
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(closeButton);
        leftPanel.add(playerPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // --- 右側（アイテム一覧） ---
        rightPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 初期表示
        updateView();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        dialog.add(mainPanel, BorderLayout.CENTER);

        // 表示
        dialog.setVisible(true);
    }

    /**
     * ★画面の表示を更新するメソッド
     * アイテム使用後などに呼び出す
     */
    public void updateView() {

        // アイテムパネルを初期化
        rightPanel.removeAll();

        // 最新データで再構築
        if (inventory != null) {
            for (int i = 0; i < inventory.getCapacity(); i++) {
                ItemData itemData = inventory.getItem(i);
                ItemAcc slot = new ItemAcc(itemData);
                rightPanel.add(slot);
            }
        }

        // 再描画
        rightPanel.revalidate();
        rightPanel.repaint();

        // プレイヤーステータス更新
        if (playerPanel != null) {
            playerPanel.updateView(PlayerStatus.getInstance());
        }
    }
}