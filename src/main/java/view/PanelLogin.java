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
import javax.swing.SwingWorker;

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

				usernameField.setText("");
				passwordField.setText("");

				setComponentsEnabled(false);

				SwingWorker<Boolean, Void> loginWorker = new SwingWorker<Boolean, Void>() {
					@Override
					protected Boolean doInBackground() throws Exception {
						return registerManager.isValidCredentials(username, password);
					}

					@Override
				    protected void done() {
				        try {
				            boolean isValid = get();
				            if (isValid) {
				                PanelBase panelBase = new PanelBase(frame);
				                if (registerManager.isAdminUser(username)) {
				                    frame.setAdminLoggedIn(true); 
				                } else {
				                    frame.setAdminLoggedIn(false); 
				                }
				                frame.showPanel(panelBase);
				            } else {
				                JOptionPane.showMessageDialog(frame, "Usuário e senha inválidos! Tente novamente.",
				                        "Falha no login", JOptionPane.ERROR_MESSAGE);
				            }

				            setComponentsEnabled(true);
				        } catch (Exception ex) {
				            ex.printStackTrace();
				        }
				    }
				};

				loginWorker.execute();
			}
		});

		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(loginButton);
	}

	private void setComponentsEnabled(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		loginButton.setEnabled(enabled);
	}

}
