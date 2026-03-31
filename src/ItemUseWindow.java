import javax.swing.*;
import java.awt.*;

public class ItemUseWindow {
    private JDialog dialog;
    private JTextArea textArea;
    private JButton useButton;
    private JButton cancelButton;
    private ItemData holdItem; // 使用したいアイテム

    // ★親ウィンドウ(owner)を受け取るように変更
    // Window型にすることで、JFrameからもJDialogからも呼び出せます
    public ItemUseWindow(Window owner, ItemData item) {
        // ModalityType.APPLICATION_MODALを指定して、後ろの画面を操作不可にする
        dialog = new JDialog(owner, "アイテム使用", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(owner); // 親の中央に表示

        textArea = new JTextArea();
        textArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        textArea.setSize(200, 250);
        textArea.setText("アイテム名 " + item.getName() + "\n\n" + item.getDescription());
        textArea.setEditable(false);
        dialog.add(textArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        useButton = new JButton("Use");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(useButton);
        buttonPanel.add(cancelButton);
        useButton.addActionListener(e -> {
            dialog.dispose();
            holdItem = item;
            holdItem.setIsHolding(true);
        });
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // 閉じる処理
        cancelButton.addActionListener(e -> dialog.dispose());

        // ★表示（モーダルなのでここで止まる）
        dialog.setVisible(true);
    }

    public ItemData getHoldItem() {
        return holdItem;
    }
}