import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemAcc extends JPanel {
    private JLabel iconLabel;
    private JLabel nameLabel;

    public ItemAcc(ItemData item) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setPreferredSize(new Dimension(300, 100));

        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setOpaque(true);

        add(iconLabel, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.SOUTH);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (item == null) {
                    return;
                }

                // ★ここが変更点です
                // 1. このパネル（ItemAcc）が乗っている親ウィンドウ（JDialogやJFrame）を探す
                Window owner = SwingUtilities.getWindowAncestor(ItemAcc.this);

                // 2. その親ウィンドウを引数として渡す
                new ItemUseWindow(owner, item);
            }
        });

        updateView(item);
    }

    public void updateView(ItemData item) {
        if (item == null) {
            setBackground(Color.LIGHT_GRAY);
            iconLabel.setIcon(null);
            nameLabel.setText("Empty");
            nameLabel.setBackground(Color.LIGHT_GRAY);
            setToolTipText(null);
        } else {
            setBackground(Color.WHITE);
            // ItemDataがパス(String)を持っている前提
            if (item.getImage3D() != null) {
                iconLabel.setIcon(new ImageIcon(item.getImage3D()));
            } else {
                iconLabel.setIcon(null);
            }

            nameLabel.setText(item.getName());
            nameLabel.setBackground(Color.WHITE);
            setToolTipText(item.getDescription());
        }
    }
}