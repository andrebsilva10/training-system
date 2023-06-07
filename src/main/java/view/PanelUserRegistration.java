package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.RegisterManager;
import exception.EmployeeRegistrationException;
import model.User;
import model.UserAdministrator;
import model.UserDefault;

public class PanelUserRegistration extends JPanel implements VisualComponent {
	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JButton btnRegister, btnBack;
	private RegisterManager registerM;
	boolean isAdministrator;

	public PanelUserRegistration(FrameBase frame, boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
		registerM = new RegisterManager();
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 250));
		setPreferredSize(new Dimension(800, 600));

		initializeComponents();
		setComponents();
		setEvents(frame);
	}

	private void initializeComponents() {
		lblName = new JLabel("Nome:");
		txtName = new JTextField(20);
		lblPassword = new JLabel("Senha:");
		passwordField = new JPasswordField(20);
		btnRegister = new JButton("Cadastrar");
		btnBack = new JButton("Voltar");
	}

	public void setComponents() {
		add(lblName);
		add(txtName);
		add(lblPassword);
		add(passwordField);
		add(btnRegister);
		add(btnBack);
	}

	private void setEvents(FrameBase frame) {
		btnBack.addActionListener(e -> frame.showPanel(new PanelBase(frame)));
		btnRegister.addActionListener(e -> {
			String name = txtName.getText().trim();
			char[] passwordChars = passwordField.getPassword();
			String password = new String(passwordChars);
			try {
				if (name.isEmpty() || password.isEmpty()) {
					throw new EmployeeRegistrationException("O nome e a senha não podem estar vazios");
				}

				User user;
				if (isAdministrator) {
					user = new UserAdministrator(name, password);
				} else {
					user = new UserDefault(name, password);
				}

				registerM.saveUser(user);
				txtName.setText("");
				passwordField.setText("");
			} catch (EmployeeRegistrationException exception) {
				JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
	}

}
