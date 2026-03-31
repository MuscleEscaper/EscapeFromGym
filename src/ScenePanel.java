import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * シーン描画
 */
public class ScenePanel extends JPanel {
    private Image backgroundImage;
    private MainViewPanel mainViewPanel;
    private List<HitDetectionArea> hitDetectionAreas = new ArrayList<>();

    public static boolean DEBUG_MODE = false;

    /**
     * シーンを生成する
     * 
     * @param imagePath 画像のパス(ex."images/Scene/scene1.png")
     */
    public ScenePanel(String imagePath, MainViewPanel mainViewPanel) {
        this.mainViewPanel = mainViewPanel;
        this.setLayout(null); // 自由配置可能
        changeImage(imagePath);
        setupNavButtons();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkClick(e.getX(), e.getY());
            }
        });
    }

    /**
     * シーンに当たり判定を追加する
     */
    public void addHitDetectionArea(HitDetectionArea hitDetectionArea) {
        hitDetectionAreas.add(hitDetectionArea);
    }

    private void setupNavButtons() {
        ArrowButton leftBtn = new ArrowButton(ArrowButton.LEFT);
        ArrowButton rightBtn = new ArrowButton(ArrowButton.RIGHT);

        leftBtn.addActionListener(e -> mainViewPanel.goPrevious());
        rightBtn.addActionListener(e -> mainViewPanel.goNext());

        add(leftBtn);
        add(rightBtn);

        // リサイズ時の位置調整ロジック
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();
                leftBtn.setBounds(20, (h - 100) / 2, 60, 100);
                rightBtn.setBounds(w - 80, (h - 100) / 2, 60, 100);
            }
        });
    }

    private void checkClick(int x, int y) {
        for (HitDetectionArea hitDetectionArea : hitDetectionAreas) {
            if (hitDetectionArea.contains(x, y)) {
                hitDetectionArea.onClick();
                break;
            }
        }
    }

    /**
     * シーンの背景画像を更新する
     * 
     * @param imagePath 画像のパス
     */
    public void changeImage(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            this.backgroundImage = null;
        } else {
            this.backgroundImage = icon.getImage();
        }
        repaint();
    }

    /**
     * 名前を引数にして当たり判定を削除する
     * 
     * @param name 消したいItemDataの名前
     */
    public void removeHitDetectionAreaByName(String name) {
        hitDetectionAreas.removeIf(area -> area.getName().equals(name));
        repaint();
    }

    /**
     * 直接オブジェクトを指定して削除する
     */
    public void removeHitDetectionArea(HitDetectionArea targetArea) {
        hitDetectionAreas.remove(targetArea);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 背景描画
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("画像が見つかっていません", 50, 50);
        }

        // 当たり判定領域の描画ループ
        for (HitDetectionArea area : hitDetectionAreas) {
            // アイテムデータと3D画像パスがある場合、その範囲に画像を描画する
            if (area.getItemData() != null && area.getItemData().getImage3D() != null) {
                ImageIcon itemIcon = new ImageIcon(area.getItemData().getImage3D());
                if (itemIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    g.drawImage(itemIcon.getImage(),
                            area.getBounds().x, area.getBounds().y,
                            area.getBounds().width, area.getBounds().height, this);
                }
            }

            // デバッグ用
            if (DEBUG_MODE) {
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));
                Rectangle b = area.getBounds();
                g2.draw(b);
                g.drawString(area.getName(), b.x, b.y - 2);
            }
        }
    }
}