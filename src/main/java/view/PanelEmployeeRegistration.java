package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.RegisterManager;
import exception.EmployeeRegistrationException;
import model.Employee;

public class PanelEmployeeRegistration extends JPanel implements VisualComponent {
	private JLabel lblName;
	private JTextField txtName;
	private JButton btnRegister, btnBack;
	private RegisterManager registerM;


	public PanelEmployeeRegistration(FrameBase frame) {
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
		btnRegister = new JButton("Cadastrar");
		btnBack = new JButton("Voltar");
	}

	public void setComponents() {
		add(lblName);
		add(txtName);
		add(btnRegister);
		add(btnBack);

		
	}

	private void setEvents(FrameBase frame) {
        btnBack.addActionListener(e -> frame.showPanel(new PanelBase(frame)));
        btnRegister.addActionListener(e -> {
            String name = txtName.getText().trim();
            try {
            	if (name.isEmpty()) {
            		throw new EmployeeRegistrationException("O campo 'Nome' não pode estar vazio");
            	}

                Employee employee = registerM.createNewEmployee();
                employee.setName(name);
                registerM.saveEmployee(employee);
                txtName.setText("");
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