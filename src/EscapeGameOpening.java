import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter; // マウス操作用に追加
import java.awt.event.MouseEvent; // マウス操作用に追加

public class EscapeGameOpening extends JFrame {

    // ▼▼▼ 画像パスの設定 ▼▼▼
    private final String BACKGROUND_PATH = "assets/images/openingimages/background.png";
    private final String TITLE_LOGO_PATH = "assets/images/openingimages/title.png";

    // ▼▼▼ タイトルの位置とサイズ ▼▼▼
    private final int TITLE_X = 100;
    private final int TITLE_Y = 80;
    private final int TITLE_WIDTH = 600;
    private final int TITLE_HEIGHT = 200;

    // ウィンドウのサイズ設定
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;

    public EscapeGameOpening() {
        // 1. ウィンドウの基本設定
        setTitle("脱出ゲーム Title");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 2. カスタムパネルの作成
        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(null);

        // 3. ゲームスタートボタンの作成（見た目をテキストのみにする）
        JButton startButton = new JButton("GAME START");
        startButton.setBounds(300, 450, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 18)); // 文字サイズを少し大きくしました

        // --- ▼ デザイン変更箇所 ▼ ---
        startButton.setForeground(Color.YELLOW); // 文字色を黄色に
        startButton.setContentAreaFilled(false); // ボタンの背景（塗りつぶし）を透明に
        startButton.setBorderPainted(false); // ボタンの枠線を消す
        startButton.setFocusPainted(false); // クリック時の枠線を消す
        startButton.setOpaque(false); // 不透明設定をオフ
        // ---------------------------

        // 4. ボタンを押した時の動作（画面遷移）
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStart();
            }
        });

        // 【おまけ】マウスを乗せた時に少し色が変わる演出（不要なら削除可）
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setForeground(Color.WHITE); // マウスが乗ったら白
                startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // カーソルを手の形に
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setForeground(Color.YELLOW); // 離れたら黄色に戻す
            }
        });

        mainPanel.add(startButton);
        add(mainPanel);
    }

    // ゲーム開始処理
    private void gameStart() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new EscapeGameApp().setVisible(true);
        });
    }

    // 背景とタイトル画像を描画する内部クラス
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
        private Image titleImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon(BACKGROUND_PATH).getImage();
                titleImage = new ImageIcon(TITLE_LOGO_PATH).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            if (titleImage != null) {
                g.drawImage(titleImage, TITLE_X, TITLE_Y, TITLE_WIDTH, TITLE_HEIGHT, this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EscapeGameOpening().setVisible(true);
        });
    }
}