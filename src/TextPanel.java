import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextPanel extends JPanel {

    private JLabel textLabel;
    private JLabel cornerLabel;
    private Timer cursorTimer;
    private boolean isCursorDown = false;
    private String[] currentMessages;
    private int currentIndex;
    private MainViewPanel mainViewPanel;

    public TextPanel(MainViewPanel mainViewPanel) {
        this.mainViewPanel = mainViewPanel;

        setPreferredSize(new Dimension(800, 120));

        setBackground(new Color(10, 0, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        setVisible(false);

        textLabel = new JLabel();
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        textLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        textLabel.setVerticalAlignment(JLabel.TOP);
        add(textLabel, BorderLayout.CENTER);

        cornerLabel = new JLabel("▼");
        cornerLabel.setForeground(Color.WHITE);
        cornerLabel.setFont(new Font("MS Gothic", Font.PLAIN, 12));
        cornerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cornerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 15));
        add(cornerLabel, BorderLayout.SOUTH);

        cursorTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isCursorDown) {
                    cornerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 15));
                } else {
                    cornerLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 4, 15));
                }
                isCursorDown = !isCursorDown;
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextMessage();
            }
        });
    }

    /**
     * シナリオのメッセージを引数でシナリオ番号を受け取り表示する
     * 
     * @param scenarioIndex シナリオ番号
     */
    public void showScenarioMessages(int scenarioIndex) {
        this.currentMessages = EventText.getScenarioMessages(scenarioIndex);
        this.currentIndex = 0;

        if (this.currentMessages != null && this.currentMessages.length > 0) {
            textLabel.setText(textToHTML(this.currentMessages[currentIndex]));
            mainViewPanel.setTextPanelVisible(true);

            cursorTimer.start();
        } else {
            System.out.println("エラー: 指定されたシナリオ番号 " + scenarioIndex + " にはメッセージが存在しません。");
            closePanel();
        }
    }

    /**
     * メッセージを引数で受け取り表示する
     * 
     * @param messages 表示したいメッセージの配列
     */
    public void showMessages(String[] messages) {
        this.currentMessages = messages;
        this.currentIndex = 0;

        if (this.currentMessages != null && this.currentMessages.length > 0) {
            textLabel.setText(textToHTML(this.currentMessages[currentIndex]));
            mainViewPanel.setTextPanelVisible(true);

            cursorTimer.start();
        } else {
            closePanel();
        }
    }

    private void nextMessage() {
        currentIndex++;
        if (currentMessages != null && currentIndex < currentMessages.length) {
            textLabel.setText(textToHTML(currentMessages[currentIndex]));
        } else {
            closePanel();
        }
    }

    private void closePanel() {
        mainViewPanel.setTextPanelVisible(false);

        textLabel.setText("");
        cursorTimer.stop();
    }

    /**
     * 文字列を独自のタグを用いてHTMLに変換する
     */
    private String textToHTML(String text) {
        if (text == null)
            return "";

        // 改行コードをHTMLの<br>に変換
        String formatted = text.replace("\n", "<br>");

        // 文字の色を赤にする変換
        formatted = formatted.replaceAll("\\[red\\]", "<font color='red'>");
        formatted = formatted.replaceAll("\\[/red\\]", "</font>");

        // 文字を大きくする変換
        formatted = formatted.replaceAll("\\[big\\]", "<font size='6'>");
        formatted = formatted.replaceAll("\\[/big\\]", "</font>");

        // 文字を太くする変換
        formatted = formatted.replaceAll("\\[b\\]", "<b>");
        formatted = formatted.replaceAll("\\[/b\\]", "</b>");

        return "<html>" + formatted + "</html>";
    }
}