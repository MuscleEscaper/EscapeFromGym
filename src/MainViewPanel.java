import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

/**
 * ゲーム画面管理
 */
public class MainViewPanel extends JPanel {
    private CardLayout sceneLayout;
    private JPanel sceneContainer;
    private Scenes scenes;
    private TextPanel textPanel;
    private JPanel grayScale;
    private JLayeredPane layeredPane;
    private GameProgress gameProgress;

    public MainViewPanel() {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        sceneLayout = new CardLayout();
        sceneContainer = new JPanel(sceneLayout);

        // GrayScaleはクリックできないようにする暗幕とする
        grayScale = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        grayScale.setBackground(new Color(0, 0, 0, 150));
        grayScale.setOpaque(false);
        grayScale.setVisible(false);
        MouseAdapter blocker = new MouseAdapter() {
        };
        grayScale.addMouseListener(blocker);
        grayScale.addMouseMotionListener(blocker);

        textPanel = new TextPanel(this);

        layeredPane.add(sceneContainer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(grayScale, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(textPanel, JLayeredPane.MODAL_LAYER);

        scenes = new Scenes(this, textPanel);

        gameProgress = GameProgress.getInstance();
        gameProgress.setTextPanel(textPanel);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = layeredPane.getWidth();
                int height = layeredPane.getHeight();

                sceneContainer.setBounds(0, 0, width, height);
                grayScale.setBounds(0, 0, width, height);

                int textHeight = 120;
                textPanel.setBounds(0, height - textHeight, width, textHeight);
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });
    }

    /**
     * テキストパネルと暗幕の表示状態をまとめて切り替える
     * 
     * @param visible 表示=1, 非表示=0
     */
    public void setTextPanelVisible(boolean visible) {
        textPanel.setVisible(visible);
        grayScale.setVisible(visible);
    }

    /**
     * シーンを追加
     */
    public void addScene(ScenePanel panel, String name) {
        sceneContainer.add(panel, name);
    }

    /**
     * シーンを次に進める
     */
    public void goNext() {
        sceneLayout.next(sceneContainer);
    }

    /**
     * シーンを前に戻す
     */
    public void goPrevious() {
        sceneLayout.previous(sceneContainer);
    }

    /**
     * 最初のシーンに移動
     */
    public void goFirst() {
        sceneLayout.first(sceneContainer);
    }

    /**
     * 最後のシーンに移動
     */
    public void goLast() {
        sceneLayout.last(sceneContainer);
    }

    /**
     * 名前で指定したシーンに移動
     */
    public void go(String name) {
        sceneLayout.show(sceneContainer, name);
    }
}