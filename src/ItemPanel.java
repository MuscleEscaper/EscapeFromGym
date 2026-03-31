import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JPanel {
    public ItemPanel() {
        setPreferredSize(new Dimension(150, 600)); // 幅を固定
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("アイテム"));

        // 仮のアイテムボタンを追加
        add(createItemButton("鍵"));
        add(createItemButton("メモ"));
    }

    private JButton createItemButton(String itemName) {
        JButton btn = new JButton(itemName);
        btn.setPreferredSize(new Dimension(130, 40));

        // ボタンを押したときの動作
        btn.addActionListener(e -> {
            System.out.println(itemName + "を選択しました");
        });

        return btn;
    }

    // アイテム追加用メソッドの例
    public void addItem(String itemName) {
        add(createItemButton(itemName));
        revalidate(); // 画面更新
    }
}