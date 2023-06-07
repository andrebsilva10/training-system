package view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameBase extends JFrame {
    private JPanel currentPanel;

    public FrameBase() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        showPanel(new PanelLogin(this));
    }

    public void showPanel(JPanel panel) {
        if (currentPanel != null) {
            getContentPane().remove(currentPanel);
        }

        currentPanel = panel;
        getContentPane().add(currentPanel);
        revalidate();
        repaint();
    }
}