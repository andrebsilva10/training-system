package view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameBase extends JFrame {
    private JPanel currentPanel;
    private boolean adminLoggedIn;

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

        updateAdminLoggedIn(adminLoggedIn);
    }

    public void setAdminLoggedIn(boolean adminLoggedIn) {
        this.adminLoggedIn = adminLoggedIn;
        updateAdminLoggedIn(adminLoggedIn);
    }

    private void updateAdminLoggedIn(boolean adminLoggedIn) {
        if (currentPanel instanceof PanelBase) {
            ((PanelBase) currentPanel).setAdminLoggedIn(adminLoggedIn);
        } else if (currentPanel instanceof PanelUserRegistration) {
            ((PanelUserRegistration) currentPanel).setAdminLoggedIn(adminLoggedIn);
        }
    }

}
