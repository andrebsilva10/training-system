package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.RegisterManager;

public class PanelLogin extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private RegisterManager registerManager;

    public PanelLogin(FrameBase frame) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 250));
        setPreferredSize(new Dimension(800, 600));

        JLabel usernameLabel = new JLabel("Usuário:");
        JLabel passwordLabel = new JLabel("Senha:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerManager = new RegisterManager();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (registerManager.isValidCredentials(username, password)) {
                    frame.showPanel(new PanelBase(frame));
                } else {
                    JOptionPane.showMessageDialog(frame, "Usuário e senha inválidos! Tente novamente.",
                            "Falha no login", JOptionPane.ERROR_MESSAGE);
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
    }
}
