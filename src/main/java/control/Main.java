package control;

import javax.swing.SwingUtilities;

import view.FrameBase;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrameBase::new);
    }
}
