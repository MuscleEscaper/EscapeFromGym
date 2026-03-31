import javax.swing.*;
import java.awt.*;

/**
 * イベントハンドラ
 */
public class OnClickLogics {
    private MainViewPanel mainViewPanel;
    private TextPanel textPanel;

    private IActionOnClick gameActionOnClick;

    public OnClickLogics(MainViewPanel mainViewPanel, TextPanel textPanel) {
        this.mainViewPanel = mainViewPanel;
        this.textPanel = textPanel;
    }

    public void onClick() {
        if (gameActionOnClick != null)
            gameActionOnClick.onClick();
    }

    // クリックした際の挙動を以下の関数で定義する

    /**
     * 1行メッセージ表示
     */
    public void showMessage(String message) {
        String[] msgArray = { message };
        textPanel.showMessages(msgArray);
    }

    /**
     * アイテム獲得
     */
    public void acquireItem(ItemData item) {
        showMessage(item.getName() + " を取得した。");
        InventoryManage.getInstance().addItem(item);
    }

    /**
     * ゲーム進行
     */
    public void Progress(int curProgressNum) {
        GameProgress.getInstance().IncrementProgress(curProgressNum);
    }

    /**
     * ゲーム進行を任意の番号までスキップ
     * 
     * @param nextNum 飛ばしたい番号
     */
    public void skipProgress(int nextNum) {
        GameProgress.getInstance().skipProgress(nextNum);
    }

    /**
     * 南京錠ポップアップ表示
     * 
     * @param onSuccess 解除成功時に実行する処理
     * @param id        南京錠のID
     */
    public void openLock(Runnable onSuccess, int id) {
        Window owner = SwingUtilities.getWindowAncestor(mainViewPanel);

        switch (id) {
            case 1:
                LockDumbbell lockDialog = new LockDumbbell(owner, () -> {
                    if (onSuccess != null)
                        onSuccess.run();
                });
                lockDialog.setVisible(true);
                break;
            case 2:
                LockColor lockDialog2 = new LockColor(owner, () -> {
                    if (onSuccess != null)
                        onSuccess.run();
                });
                lockDialog2.setVisible(true);
                break;
            default:
                break;
        }
    }

    /**
     * アイテム使用
     */
    public void useItem(ItemData item, IActionOnClick actionOnClick) {
        if (item.getIsHolding()) {
            actionOnClick.onClick();
            InventoryManage.getInstance().removeItem(item);
        } else if (InventoryManage.getInstance().haveHoldingItem()) {
            showMessage("ここでは使えないようだ。");
        }
    }

    /**
     * エンディング画面を表示する
     */
    public void showEnding() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ending");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new EscapeGameEnding());

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Window owner = SwingUtilities.getWindowAncestor(mainViewPanel);
            if (owner != null) {
                owner.dispose();
            }
        });
    }
}
