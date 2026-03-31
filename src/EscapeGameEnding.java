import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EscapeGameEnding extends JPanel implements ActionListener {

    // ==========================================
    // ▼ 設定エリア：ここを変更してください ▼
    // ==========================================

    // 1. 画像ファイル名
    private static final String BG_IMAGE_PATH = "assets/images/endingimages/background.png";
    private static final String TEXT_IMAGE_PATH = "assets/images/endingimages/text_image.png";

    // 2. 文字画像の表示位置設定
    // ※ AUTO_CENTER を false にすると、下の数値を自由に変更して位置を決められます。
    private static final boolean AUTO_CENTER = false; // true=画面中央、false=手動設定

    // 画面左上からの距離（ピクセル単位）
    // AUTO_CENTER が false の時だけ有効になります。
    private static final int TEXT_X = 60; // 横の位置（0が左端）
    private static final int TEXT_Y = 50; // 縦の位置（0が上端）

    // 3. フェードインの速さ
    private static final int TIMER_DELAY = 50;
    private static final float ALPHA_INCREMENT = 0.01f;

    // ==========================================
    // ▲ 設定エリア終了 ▲
    // ==========================================

    private Image backgroundImage;
    private Image textImage;
    private float currentAlpha = 0.0f;
    private Timer timer;

    public EscapeGameEnding() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        try {
            File bgFile = new File(BG_IMAGE_PATH);
            if (bgFile.exists())
                backgroundImage = ImageIO.read(bgFile);
            else
                System.out.println("背景画像なし: " + BG_IMAGE_PATH);

            File textFile = new File(TEXT_IMAGE_PATH);
            if (textFile.exists())
                textImage = ImageIO.read(textFile);
            else
                System.out.println("文字画像なし: " + TEXT_IMAGE_PATH);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (backgroundImage != null && textImage != null) {
            timer = new Timer(TIMER_DELAY, this);
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // 1. 背景描画
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this);
        }

        // 2. 文字画像描画
        if (textImage != null) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha));

            int imgWidth = textImage.getWidth(this);
            int imgHeight = textImage.getHeight(this);

            int x, y;

            if (AUTO_CENTER) {
                // 自動で中央に計算
                // 画面より画像が大きい場合の縮小処理も含める
                if (imgWidth > panelWidth || imgHeight > panelHeight) {
                    double widthRatio = (double) panelWidth / imgWidth;
                    double heightRatio = (double) panelHeight / imgHeight;
                    double ratio = Math.min(widthRatio, heightRatio);
                    imgWidth = (int) (imgWidth * ratio);
                    imgHeight = (int) (imgHeight * ratio);
                }
                x = (panelWidth - imgWidth) / 2;
                y = (panelHeight - imgHeight) / 2;
            } else {
                // 手動設定した座標を使用
                x = TEXT_X;
                y = TEXT_Y;
            }

            g2d.drawImage(textImage, x, y, imgWidth, imgHeight, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentAlpha += ALPHA_INCREMENT;
        if (currentAlpha >= 1.0f) {
            currentAlpha = 1.0f;
            timer.stop();
        }
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Ending - Custom Position");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new EscapeGameEnding());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}