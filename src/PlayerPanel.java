import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PlayerPanel extends JPanel {
    // 表示する画像（第1形態〜第6形態）
    private Image FirstImage; // 初期状態
    private Image SecondImage; // 胸
    private Image ThirdImage; // 胸 -> 肩
    private Image FourthImage; // 胸 -> 肩 -> 背中
    private Image FifthImage; // 胸 -> 肩 -> 背中 -> 腕
    private Image SixthImage; // 胸 -> 肩 -> 背中 -> 腕 -> 足 (完全体)

    // 現在表示すべき画像
    private Image currentImage;

    public PlayerPanel() {
        // パネルの設定
        setBackground(Color.WHITE);

        // --- 画像の読み込み ---
        // プロジェクトフォルダ直下に置いてください
        FirstImage = loadImage("assets/images/Player/phisy1.jpg"); // 何もなし
        SecondImage = loadImage("assets/images/Player/phisy2.jpg"); // +胸
        ThirdImage = loadImage("assets/images/Player/phisy3.png"); // +背中
        FourthImage = loadImage("assets/images/Player/phisy4.png"); // +肩
        FifthImage = loadImage("assets/images/Player/phisy5.jpg"); // +腕
        SixthImage = loadImage("assets/images/Player/phisy6.jpg"); // +足

        // 最初はFirstを表示しておく
        currentImage = FirstImage;
    }

    /**
     * ステータスを受け取って、どの画像を表示するか決める
     */
    public void updateView(PlayerStatus status) {
        // デフォルトは初期状態
        currentImage = FirstImage;

        // 順番：胸 → 背中 → 肩 → 腕 → 足
        // if文を入れ子にすることで、順番通りに条件を満たしているかチェックします

        if (status.getChestVal()) {
            // 胸があれば第2形態へ
            currentImage = SecondImage;

            if (status.getShoulderVal()) {
                // さらに肩があれば第3形態へ
                currentImage = ThirdImage;

                if (status.getBackVal()) {
                    // さらに背中があれば第4形態へ
                    currentImage = FourthImage;

                    if (status.getArmVal()) {
                        // さらに腕があれば第5形態へ
                        currentImage = FifthImage;

                        if (status.getLegVal()) {
                            // 最後に足もあれば最終形態へ
                            currentImage = SixthImage;
                        }
                    }
                }
            }
        }

        // 画面を再描画
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 背景クリア

        // 画像がまだ読み込めていない場合は何もしない
        if (currentImage == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
        // 画像をきれいに縮小・拡大する設定
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // パネルのサイズいっぱいに画像を描画する
        g2.drawImage(currentImage, 20, 0, getWidth() - 20, getHeight(), this);
    }

    // 画像読み込み用
    private Image loadImage(String path) {
        File f = new File(path);
        if (!f.exists()) {
            System.out.println("画像が見つかりません: " + path);
            return null;
        }
        return new ImageIcon(path).getImage();
    }
}